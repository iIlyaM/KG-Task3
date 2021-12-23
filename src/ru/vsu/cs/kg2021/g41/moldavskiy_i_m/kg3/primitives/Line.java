package ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3.primitives;

import ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3.point.RealPoint;

import java.util.ArrayList;
import java.util.List;

public class Line {

    private RealPoint startPoint, endPoint;
    private double A, B, C;
    private List<Double> equation = new ArrayList<>();

    /**
     *          * A = -y2 + y1
     *          * B = x2 - x1
     *          * C = -y1x2 - x1y2
     *         double A = y1 - y2;
     *         double B = x2 - x1;
     *         double C = -(y1 * x2) -(x1 * y2);
     */
    public Line(RealPoint startPoint, RealPoint endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        A = startPoint.getY() - endPoint.getY();
        B = endPoint.getX() - startPoint.getX();
        C = -(startPoint.getY() * endPoint.getX()) - (startPoint.getX() * endPoint.getY());
        this.equation.add(A);
        this.equation.add(B);
        this.equation.add(C);
    }

    public RealPoint getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(RealPoint startPoint) {
        this.startPoint = startPoint;
    }

    public RealPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(RealPoint endPoint) {
        this.endPoint = endPoint;
    }

    public List<Double> getEquation() {
        return equation;
    }

    public double getA() {
        return A;
    }

    public double getB() {
        return B;
    }

    public double getC() {
        return C;
    }
}
