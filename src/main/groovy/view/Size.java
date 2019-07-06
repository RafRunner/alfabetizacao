package view;

public class Size {

    private double width;
    private double height;

    public Size(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public Size average(final Size size) {
        return new Size((width + size.getWidth()) / 2, (height + size.getHeight()) / 2);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
