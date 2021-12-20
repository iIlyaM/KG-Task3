package ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ContourService {

    public static List<RealPoint> getContourPoints(SimpleTriangle firstTriangle, SimpleTriangle secondTriangle) {
        List<RealPoint> contour = new ArrayList<>();
        List<RealPoint> firstTriangleVertexes = firstTriangle.getPoints();
        List<RealPoint> secondTriangleVertexes = secondTriangle.getPoints();
        RealPoint temp1;
        RealPoint temp2;

        for (int i = 0; i < 3; i++) {
            temp1 = isVertexInsideTriangle(firstTriangle, secondTriangleVertexes.get(i));
            if(temp1 != null) {
                contour.add(temp1);
            }
            temp2 = isVertexInsideTriangle(secondTriangle, firstTriangleVertexes.get(i));
            if(temp2 != null) {
                contour.add(temp2);
            }
        }
        return contour;
    }

    public static RealPoint isVertexInsideTriangle(SimpleTriangle triangle, RealPoint vertex) {
        List<RealPoint> triangleVertexes = new ArrayList<>(triangle.getPoints());
        List<Double> resultList = new ArrayList<>();
        for (int i = 0; i < triangleVertexes.size() - 1; i++) {
            resultList.add(multiplyVectors(new Segment(triangleVertexes.get(i + 1) ,triangleVertexes.get(i)), vertex));
            if(i == 1) {
               resultList.add(multiplyVectors(new Segment(triangleVertexes.get(2) ,triangleVertexes.get(0)), vertex));
            }
        }
        if(checkValueSigns(resultList)) {
            return vertex;
        }
        return null;
    }

    public static double multiplyVectors(Segment segment, RealPoint testedVertex) {
        RealPoint triangleVector = segment.getSegmentCoordinates();
        RealPoint testedVertexVector = new Segment(segment.getPoint1(), testedVertex).getSegmentCoordinates();
        return (triangleVector.getX() * testedVertexVector.getY()) - (triangleVector.getY() * testedVertex.getX());
    }
    
    private static boolean checkValueSigns(List<Double> list) {
        int positiveCounter = 0;
        int negativeCounter = 0;
        for(Double val : list) {
            if(val > 0) {
                positiveCounter++;
            }
            if(val < 0) {
                negativeCounter++;
            }
        }
        if(positiveCounter == 3 || negativeCounter == 3) {
            return true;
        }
        return false;
    }

    private static RealPoint getIntersectionPoint(Line firstLine, Line secondLine) {
          double denominator = (firstLine.getA() * secondLine.getB()) - (secondLine.getA() - firstLine.getB());
          double numeratorX = -((firstLine.getC()) * secondLine.getB()) - (secondLine.getC() * firstLine.getB());
          double numeratorY = -((firstLine.getA()) * secondLine.getC()) - (secondLine.getA() * firstLine.getB());
//        double newPointX = -(C1 * B2 - C2 * B1)/(A1 * B2 - A2 * B1);
//        double newPointY = -(A1 * C2 - A2 * C1)/(A1 * B2 - A2 * B1);

        return new RealPoint(numeratorX / denominator, numeratorY / denominator);
    }

    private static List<RealPoint> getBorderPoints(List<RealPoint> points) {
//        int min = list.get(0);
//        int max = list.get(0);
        List<RealPoint> borderPoints = new ArrayList<>();
        double maxX = points.get(0).getX();
        double maxY = points.get(0).getX();
        double minX = points.get(0).getX();
        double minY = points.get(0).getX();

        for (RealPoint point: points) {
            if(point.getX() < minX) {
                minX = point.getX();
            }

            if(point.getX() > maxX) {
                maxX = point.getX();
            }
        }
        for (RealPoint point: points) {
            if(point.getY() < minY) {
                minY = point.getY();
            }

            if(point.getY() > maxY) {
                maxY = point.getY();
            }
        }
        borderPoints.add(new RealPoint(minX, minY));
        borderPoints.add(new RealPoint(maxX, maxY));
        return borderPoints;
    }
}
