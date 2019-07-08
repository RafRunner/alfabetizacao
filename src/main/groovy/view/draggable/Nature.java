package view.draggable;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import view.Position;
import view.Size;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Nature implements EventHandler<MouseEvent> {
    private double lastMouseX = 0, lastMouseY = 0; // mouse coords
    private double lastX = 0, lastY = 0; // scene cords (in relation to the center of the shape)

    private boolean dragging = false;

    private boolean enabled = true;
    private final Region eventRegion;
    private final List<Region> dragRegions = new ArrayList<>();
    private final List<Listener> dragListeners = new ArrayList<>();

    public Nature(final Region region) {
        this(region, region);
    }

    public Nature(final Region eventRegion, final Region... dragRegions) {
        this.eventRegion = eventRegion;
        this.dragRegions.addAll(Arrays.asList(dragRegions));
        this.eventRegion.addEventHandler(MouseEvent.ANY, this);
    }

    public final boolean addDraggedNode(final Region node) {
        if (!this.dragRegions.contains(node)) {
            return this.dragRegions.add(node);
        }
        return false;
    }

    public final boolean addListener(final Listener listener) {
        return this.dragListeners.add(listener);
    }

    public final List<Region> getDragRegions() {
        return new ArrayList<>(this.dragRegions);
    }

    public final Region getEventRegion() {
        return this.eventRegion;
    }

    public Size getSize() {
        return new Size(eventRegion.getWidth(), eventRegion.getHeight());
    }

    public Position getPosition() {
        return new Position(lastX, lastY);
    }

    public boolean isTouching(final Nature nature) {
        final Position relativeDistance = getPosition().relativeAbsoluteDistance(nature.getPosition());
        final Size averageSize = getSize().average(nature.getSize());

        return relativeDistance.getX() < averageSize.getWidth() && relativeDistance.getY() < averageSize.getHeight();
    }

    public void fit(final Nature nature) {
        final Position relativeDistance = getPosition().relativeDistance(nature.getPosition());
        deslocate(relativeDistance.getX(), relativeDistance.getY());
    }

    public void deslocate(final Position position) {
        final double deltaX = position.getX() - lastX;
        final double deltaY = position.getY() - lastY;
        deslocate(deltaX, deltaY);
    }

    private void deslocate(final double deltaX, final double deltaY) {
        for (final Region dragNode : this.dragRegions) {
            final double initialTranslateX = dragNode.getTranslateX();
            final double initialTranslateY = dragNode.getTranslateY();
            dragNode.setTranslateX(initialTranslateX + deltaX);
            dragNode.setTranslateY(initialTranslateY + deltaY);
        }

        this.lastMouseX += deltaX;
        this.lastMouseY += deltaY;
        this.lastX += deltaX;
        this.lastY += deltaY;
    }

    @Override
    public final void handle(final MouseEvent event) {
        if (!this.enabled) {
            event.consume();
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                alertListener(Event.Press);
            }
            return;
        }

        if (MouseEvent.MOUSE_PRESSED == event.getEventType()) {
            if (this.eventRegion.contains(event.getX(), event.getY())) {
                this.lastMouseX = event.getSceneX();
                this.lastMouseY = event.getSceneY();
                event.consume();
                alertListener(Event.Press);
            }
        } else if (MouseEvent.MOUSE_DRAGGED == event.getEventType()) {
            if (!this.dragging) {
                this.dragging = true;
                alertListener(Event.DragStart);
            }
            if (this.dragging) {
                final double deltaX = event.getSceneX() - this.lastMouseX;
                final double deltaY = event.getSceneY() - this.lastMouseY;

                deslocate(deltaX, deltaY);

                event.consume();
                alertListener(Event.Drag);
            }
        } else if (MouseEvent.MOUSE_RELEASED == event.getEventType()) {
            if (this.dragging) {
                event.consume();
                this.dragging = false;
                alertListener(Event.DragEnd);
            }
        }
    }

    private void alertListener(Event dragEvent) {
        for (final Listener listener : this.dragListeners) {
            listener.accept(this, dragEvent);
        }
    }

    public final boolean removeDraggedRegion(final Region region) {
        return this.dragRegions.remove(region);
    }

    public final boolean removeListener(final Listener listener) {
        return this.dragListeners.remove(listener);
    }

    /**
     * When the initial mousePressed is missing we can supply the first coordinates programmatically.
     * @param lastMouseX
     * @param lastMouseY
     */
    public final void setLastMouse(final double lastMouseX, final double lastMouseY) {
        this.lastMouseX = lastMouseX;
        this.lastMouseY = lastMouseY;
    }

    public void setStartingPosition(final double x, final double y) {
        this.lastX = x;
        this.lastY = y;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }
}
