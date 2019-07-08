package view;

public class Position implements Comparable<Position> {

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

    @Override
    public int compareTo(Position o) {
        final Double distanciaRadiana = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        final Double outraDistanciaRadiana = Math.sqrt(Math.pow(o.getX(), 2) + Math.pow(o.getY(), 2));

        return distanciaRadiana.compareTo(outraDistanciaRadiana);
    }
}
