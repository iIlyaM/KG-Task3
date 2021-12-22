package ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3.model;

import ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3.point.RealPoint;

import java.util.ArrayList;
import java.util.List;

public class SimpleTriangle {

    private final RealPoint point1;
    private final RealPoint point2;
    private final RealPoint point3;


    public SimpleTriangle(RealPoint point1, RealPoint point2, RealPoint point3) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
    }

    public RealPoint getPoint1() {
        return point1;
    }

    public RealPoint getPoint2() {
        return point2;
    }

    public RealPoint getPoint3() {
        return point3;
    }

    public List<RealPoint> getPoints() {
        List<RealPoint> points = new ArrayList<>();
        points.add(point1);
        points.add(point2);
        points.add(point3);
        return points;
    }
}
