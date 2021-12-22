package ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3.services;

import ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3.model.SimpleTriangle;
import ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3.point.RealPoint;
import ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3.primitives.Line;
import ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3.primitives.Segment;

import java.util.ArrayList;
import java.util.List;

public class ContourService {

    public static List<RealPoint> sortContourPoints(List<RealPoint> contour) {
        List<RealPoint> sortedList = new ArrayList<>();
        List<RealPoint> tempSublist;
        List<RealPoint> testedTempList = new ArrayList<>();
        Segment testedSegment;
        Segment tempSegment;
        RealPoint startPoint;

        for (int i = 0; i < contour.size(); i++) {
            startPoint = contour.get(i);
            testedSegment = new Segment(startPoint, contour.get(i + 1));
            tempSublist = contour.subList(i + 2, contour.size() - 1);
            for (int j = 0; j < tempSublist.size(); j++) {
                tempSegment = new Segment(startPoint ,tempSublist.get(j));

            }
        }



        return sortedList;
    }

    public static List<RealPoint> getContourPoints(SimpleTriangle firstTriangle, SimpleTriangle secondTriangle) {
        List<RealPoint> firstTriangleVertexes = firstTriangle.getPoints();
        List<RealPoint> secondTriangleVertexes = secondTriangle.getPoints();
        List<RealPoint> contour = new ArrayList<>(getIntersectionPoints(firstTriangle, secondTriangle));
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

    private static List<RealPoint> getIntersectionPoints(SimpleTriangle firstTriangle, SimpleTriangle secondTriangle) {
        /**
         * Ax + By + C = 0
         * A = -y2 + y1
         * B = x2 - x1
         * C = -y1x2 - x1y2
         *
         * x = - (C1 * B2 - C2 * B1)/(A1 * B2 - A2 * B1)
         * y = - (A1 * C2 - A2 * C1)/(A1*B2 - A2 * B1)
         */
        List<RealPoint> insertionPoints = new ArrayList<>();
        List<RealPoint> points = new ArrayList<>();
        points.addAll(firstTriangle.getPoints());
        points.addAll(secondTriangle.getPoints());
        List<RealPoint> borderPoints = getBorderPoints(points);

        Line tempLine1;
        Line tempLine2;
        RealPoint point;

        for (int i = 0; i < firstTriangle.getPoints().size(); i++) {
            if(i != 2) {
            tempLine1 = new Line(firstTriangle.getPoints().get(i), firstTriangle.getPoints().get(i + 1));
            } else {
                tempLine1 = new Line(firstTriangle.getPoints().get(2), firstTriangle.getPoints().get(0));
            }
            for (int j = 0; j < secondTriangle.getPoints().size(); j++) {
                if(j != 2) {
                tempLine2 = new Line(secondTriangle.getPoints().get(j), secondTriangle.getPoints().get(j + 1));
                } else {
                    tempLine2 = new Line(secondTriangle.getPoints().get(2), secondTriangle.getPoints().get(0));
                }
                point = getIntersectionPoint(tempLine1, tempLine2);
                if(checkPointIsNear(borderPoints.get(0), borderPoints.get(1), point)) {
                    insertionPoints.add(point);
                }
            }
        }
        return insertionPoints;
    }

    private static RealPoint isVertexInsideTriangle(SimpleTriangle triangle, RealPoint vertex) {
        List<RealPoint> triangleVertexes = new ArrayList<>(triangle.getPoints());
        List<Double> resultList = new ArrayList<>();
        for (int i = 0; i < triangleVertexes.size(); i++) {
            if(i != 2) {
            resultList.add(multiplyVectors(new Segment(triangleVertexes.get(i) ,triangleVertexes.get(i + 1)), vertex));
            } else {
                resultList.add(multiplyVectors(new Segment(triangleVertexes.get(2), triangleVertexes.get(0)), vertex));
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
        double maxY = points.get(0).getY();
        double minX = points.get(0).getX();
        double minY = points.get(0).getY();

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

    private static boolean checkPointIsNear(RealPoint borderPoint1, RealPoint borderPoint2, RealPoint testedPoint) {
        return ((testedPoint.getX() < borderPoint2.getX() && testedPoint.getX() > borderPoint1.getX()) &&
                (testedPoint.getY() < borderPoint2.getY() && testedPoint.getY() > borderPoint1.getY()));
    }
}