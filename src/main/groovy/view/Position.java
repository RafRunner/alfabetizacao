package view;

public class Position {

    private double x;
    private double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position relativeAbsoluteDistance(final Position position) {
        return new Position(Math.abs(x - position.getX()), Math.abs(y - position.getY()));
    }

    public Position relativeDistance(final Position position) {
        return new Position(position.getX() - x, position.getY() - y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "X: " + x + " Y: " + y;
    }
}
