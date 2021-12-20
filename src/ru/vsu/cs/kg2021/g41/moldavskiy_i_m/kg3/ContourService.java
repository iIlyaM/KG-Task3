package ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ContourService {

    public static List<RealPoint> getContourPoints(SimpleTriangle firstTriangle, SimpleTriangle secondTriangle) {
        return new ArrayList<>();
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
}
