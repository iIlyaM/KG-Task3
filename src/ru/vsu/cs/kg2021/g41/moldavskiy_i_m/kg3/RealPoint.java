package ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3;

public class RealPoint {

    private double x, y;

    public RealPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public RealPoint minus(RealPoint point) {
        return new RealPoint(getX() - point.getX(), getY() - point.getY());
    }
}
