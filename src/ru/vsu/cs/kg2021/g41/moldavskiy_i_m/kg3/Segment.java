package ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3;

public class Segment {
    private RealPoint point1, point2;
    private RealPoint segmentCoordinates;

    public Segment(RealPoint point1, RealPoint point2) {
        this.point1 = point1;
        this.point2 = point2;
        this.segmentCoordinates = new RealPoint(point2.getX() - point1.getX(), point2.getY() - point1.getY());
//        this.segmentCoordinates.setX(point2.getX() - point1.getX());
//        this.segmentCoordinates.setY(point2.getY() - point1.getY());
    }

    public RealPoint getPoint1() {
        return point1;
    }

    public RealPoint getPoint2() {
        return point2;
    }

    public RealPoint getSegmentCoordinates() {
        return segmentCoordinates;
    }
}
