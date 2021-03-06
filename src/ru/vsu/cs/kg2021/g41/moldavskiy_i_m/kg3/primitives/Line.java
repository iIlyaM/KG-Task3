package ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3.primitives;

import ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3.point.RealPoint;

import java.util.ArrayList;
import java.util.List;

public class Line {

    private RealPoint startPoint, endPoint;
    private double A, B, C;
    private List<Double> equation = new ArrayList<>();

    public Line(RealPoint startPoint, RealPoint endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        A = endPoint.getY() - startPoint.getY();
        B = startPoint.getX() - endPoint.getX();
        C = (startPoint.getY() * endPoint.getX()) - (startPoint.getX() * endPoint.getY());
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
