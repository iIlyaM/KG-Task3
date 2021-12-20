package ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    private ScreenConverter screenConverter;
    private Line axisX, axisY;
    private List<SimpleTriangle> triangles = new ArrayList<>();
    private java.util.List<Line> allLines = new ArrayList<>();
    private RealPoint editingPoint;
    private Line currentLine = null;
    private Line editingLine = null;


    public DrawPanel() {
        screenConverter = new ScreenConverter(-5, 10, 20, 20,
                800, 600);
        axisX = new Line(new RealPoint(-1, 0), new RealPoint(1, 0));
        axisY = new Line(new RealPoint(0, -1), new RealPoint(0, 1));
        SimpleTriangle firstTriangle = new SimpleTriangle(new RealPoint(1, 6), new RealPoint(4, -7), new RealPoint(8, 4));
        triangles.add(firstTriangle);



        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
    }

    @Override
    protected void paintComponent(Graphics origG) {
        screenConverter.setScreenWidth(getWidth());
        screenConverter.setScreenHeight(getHeight());

        BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.BLUE);
//        drawLine(g, screenConverter, axisX);
//        drawLine(g, screenConverter, axisY);
        g.setColor(Color.BLACK);
        for(Line l : allLines) {
            drawLine(g, screenConverter, l);
        }
        if(currentLine != null) {
            g.setColor(Color.RED);
            drawLine(g, screenConverter, currentLine);
        }

        drawTriangle(triangles.get(0), g, screenConverter);
        if(editingLine != null) {
            g.setColor(Color.green);
            drawLine(g, screenConverter, editingLine);
        }

        origG.drawImage(bufferedImage, 0, 0, null);
        g.dispose();
    }

    private static List<RealPoint> getIntersectionContour(
            SimpleTriangle firstTriangle,
            SimpleTriangle secondTriangle,
            ScreenConverter screenConverter
    ) {
        List<RealPoint> insertionCourt = new ArrayList<>();
        List<RealPoint> points = new ArrayList<>();
        points.addAll(firstTriangle.getPoints());
        points.addAll(secondTriangle.getPoints());
        List<RealPoint> borderPoints = getBorderPoints(points);



        return insertionCourt;
    }

    private static RealPoint getIntersectionPoint(List<Double> firstLine, List<Double> secondLine) {
        double A1 = firstLine.get(0);
        double B1 = firstLine.get(1);
        double C1 = firstLine.get(2);

        double A2 = secondLine.get(0);
        double B2 = secondLine.get(1);
        double C2 = secondLine.get(2);

        double newPointX = -(C1 * B2 - C2 * B1)/(A1 * B2 - A2 * B1);
        double newPointY = -(A1 * C2 - A2 * C1)/(A1 * B2 - A2 * B1);

        return new RealPoint(newPointX, newPointY);
    }

    private static List<Double> getStraightLineEquation(RealPoint point) {
        /**
         * Ax + By + C = 0
         * A = -y2 + y1
         * B = x2 - x1
         * C = -y1x2 - x1y2
         *
         * x = - (C1 * B2 - C2 * B1)/(A1 * B2 - A2 * B1)
         * y = - (A1 * C2 - A2 * C1)/(A1*B2 - A2 * B1)
         */
        List<Double> straightLineEquation = new ArrayList<>();
        double x1 = point.getX();
        double y1 = point.getY();
        double x2 = point.getX();
        double y2 = point.getY();
//        double x3 = secondTrianglePoint1.getX();
//        double y3 = secondTrianglePoint1.getY();
//        double x4 = secondTrianglePoint2.getX();
//        double y4 = secondTrianglePoint2.getY();

        double A = y1 - y2;
        double B = x2 - x1;
        double C = -(y1 * x2) -(x1 * y2);
        straightLineEquation.add(A);
        straightLineEquation.add(B);
        straightLineEquation.add(C);
//        double A2 = y3 - y4;
//        double B2 = x4 - x3;
//        double C2 = -(y3 * x4) -(x3 * y4);
//
//        double newPointX = -(C1 * B2 - C2 * B1)/(A1 * B2 - A2 * B1);
//        double newPointY = -(A1 * C2 - A2 * C1)/(A1 * B2 - A2 * B1);
        return straightLineEquation;
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

    private static void drawTriangle(SimpleTriangle triangle, Graphics2D g, ScreenConverter sConverter) {
        ScreenPoint screenPoint1 = sConverter.real2Screen(triangle.getPoint1());
        ScreenPoint screenPoint2 = sConverter.real2Screen(triangle.getPoint2());
        ScreenPoint screenPoint3 = sConverter.real2Screen(triangle.getPoint3());
        g.drawLine(screenPoint1.getColumn(), screenPoint1.getRow(), screenPoint2.getColumn(), screenPoint2.getRow());
        g.drawLine(screenPoint2.getColumn(), screenPoint2.getRow(), screenPoint3.getColumn(), screenPoint3.getRow());
        g.drawLine(screenPoint3.getColumn(), screenPoint3.getRow(), screenPoint1.getColumn(), screenPoint1.getRow());
    }


    private static void drawLine(Graphics2D g, ScreenConverter sConverter, Line line) {
        ScreenPoint point1 = sConverter.real2Screen(line.getStartPoint());
        ScreenPoint point2 = sConverter.real2Screen(line.getEndPoint());
        g.drawLine(point1.getColumn(), point1.getRow(), point2.getColumn(), point2.getRow());
    }

    private static double distanceToLine(ScreenPoint linePoint1, ScreenPoint linePoint2, ScreenPoint checkPoint) {
        double a = linePoint2.getRow() - linePoint1.getRow();
        double b = -(linePoint2.getColumn() - linePoint1.getColumn());
        double e = - checkPoint.getColumn() * b + checkPoint.getRow() * a;
        double f = a * linePoint1.getColumn() - b * linePoint1.getRow();
        double y = (a * e * - b * f) / (a * a + b * b);
        double x = (a * y - e) / b;
        return Math.sqrt((checkPoint.getColumn() - x) * (checkPoint.getColumn() - x) +
                (checkPoint.getRow() - y) * (checkPoint.getRow() - y));
    }

    private static boolean isNear(ScreenPoint sP1, ScreenPoint sP2, double eps) {
        int distanceX = sP1.getColumn() - sP2.getColumn();
        int distanceY = sP1.getRow() - sP2.getRow();
        int distance2 = distanceX * distanceX + distanceY * distanceY;
        return distance2 < eps;
    }

    private static boolean isPointInRect(ScreenPoint pr1, ScreenPoint pr2, ScreenPoint cp) {
        return cp.getColumn() >= Math.min(pr1.getColumn(), pr2.getColumn()) &&
                cp.getColumn() <= Math.max(pr1.getColumn(), pr2.getColumn()) &&
                cp.getRow() >= Math.min(pr1.getRow(), pr2.getRow()) &&
                cp.getRow() <= Math.max(pr1.getRow(), pr2.getRow());
    }

    private static boolean closeToLine(ScreenConverter screenConverter, Line line, ScreenPoint searchPoint, int eps) {
        ScreenPoint a = screenConverter.real2Screen(line.getStartPoint());
        ScreenPoint b = screenConverter.real2Screen(line.getEndPoint());
        return isNear(a, searchPoint, eps) || isNear(b, searchPoint, eps) ||
                (distanceToLine(a, b, searchPoint ) < eps)
                        && (isPointInRect(a, b, searchPoint));
    }
//
//    private static double findDistance(ScreenPoint p1, ScreenPoint p2) {
//        return Math.sqrt(Math.pow(p2.getColumn() - p1.getColumn(), 2) + Math.pow(p2.getRow() - p1.getRow(), 2));
//    }

    private RealPoint getEditingPoint(ScreenPoint screenPoint) {
        for (SimpleTriangle triangle: triangles) {
            List<RealPoint> points = triangle.getPoints();
            for (RealPoint p: points) {
                if(isNear(screenPoint, screenConverter.real2Screen(p), 10)) {
                    return p;
                }
            }
        }
        return null;
    }

    private static Line findFirstLineNextToClick(
            ScreenConverter sConverter,
            java.util.List<Line> lines,
            ScreenPoint searchPoint,
            int eps
            ) {
        for(Line line : lines) {
             if(closeToLine(sConverter, line, searchPoint, eps)) {
                 return line;
             }
        } return null;
    }



    @Override
    public void mouseClicked(MouseEvent e) {

    }

    private ScreenPoint prevScreenPoint = null;
    private ScreenPoint clickPoint = null;

    @Override
    public void mousePressed(MouseEvent e) {
        clickPoint = new ScreenPoint(e.getX(), e.getY());
        if(SwingUtilities.isRightMouseButton(e)) {
            prevScreenPoint = new ScreenPoint(e.getX(), e.getY());

        } else if(SwingUtilities.isLeftMouseButton(e)) {
            editingPoint = getEditingPoint(clickPoint);

            if(editingLine == null) {
                Line soughtLine = findFirstLineNextToClick(screenConverter, allLines,
                        new ScreenPoint(e.getX(), e.getY()), 3);
                if (soughtLine != null) {
                    editingLine = soughtLine;
                }
            } else {
                if(closeToLine(screenConverter, editingLine, new ScreenPoint(e.getX(), e.getY()), 3)) {

                } else {
                    editingLine = null;
                }
            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)) {
            prevScreenPoint = null;
        } else if(SwingUtilities.isLeftMouseButton(e)) {
//            currentLine.setEndPoint(screenConverter.screen2Real(new ScreenPoint(e.getX(), e.getY())));
//            allLines.add(currentLine);
//            currentLine = null;
            editingPoint = null;
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        ScreenPoint currPoint = new ScreenPoint(e.getX(), e.getY());
        if(SwingUtilities.isRightMouseButton(e)) {
            RealPoint point1 = screenConverter.screen2Real(currPoint);
            RealPoint point2 = screenConverter.screen2Real(prevScreenPoint);
            RealPoint delta = point2.minus(point1);
            screenConverter.moveCorner(delta);
            prevScreenPoint = currPoint;
        } else if(SwingUtilities.isLeftMouseButton(e)) {
            if(editingPoint != null) {
                RealPoint newPoint = screenConverter.screen2Real(currPoint);
                double newX = newPoint.getX();
                double newY = newPoint.getY();
                editingPoint.setX(newX);
                editingPoint.setY(newY);
            }
//            if(currentLine != null) {
//                currentLine.setEndPoint(screenConverter.screen2Real(new ScreenPoint(e.getX(), e.getY())));
//            } else if(editingLine != null) {
//
//            }
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    private static final double SCALE_STEP = 0.01;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double coefficient = 1 + SCALE_STEP * (clicks < 0 ? -1 : 1);
        double scale = 1;

        for (int i = Math.abs(clicks); i > 0 ; i--) {
            scale *= coefficient;
        }

        screenConverter.changeScale(scale);
        repaint();
    }
}
