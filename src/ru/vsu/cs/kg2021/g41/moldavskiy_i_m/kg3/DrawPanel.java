package ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3;

import ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3.model.SimpleTriangle;
import ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3.point.RealPoint;
import ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3.point.ScreenPoint;
import ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3.primitives.Line;
import ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3.services.ContourService;
import ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3.services.ScreenConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    private ScreenConverter screenConverter;
    private List<SimpleTriangle> triangles = new ArrayList<>();
    private java.util.List<Line> allLines = new ArrayList<>();
    private RealPoint editingPoint;
    private Line currentLine = null;
    private Line editingLine = null;


    public DrawPanel() {
        screenConverter = new ScreenConverter(-5, 10, 20, 20,
                800, 600);

        SimpleTriangle firstTriangle = new SimpleTriangle(new RealPoint(1, 6), new RealPoint(4, -7), new RealPoint(8, 4));
        SimpleTriangle secondTriangle = new SimpleTriangle(new RealPoint(12, 5), new RealPoint(-1, -6), new RealPoint(3, 6));

        triangles.add(firstTriangle);
        triangles.add(secondTriangle);



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
        g.setColor(Color.BLACK);
        for(Line l : allLines) {
            drawLine(g, screenConverter, l);
        }
        if(currentLine != null) {
            g.setColor(Color.RED);
            drawLine(g, screenConverter, currentLine);
        }

        drawTriangle(triangles.get(0), g, screenConverter);
        drawTriangle(triangles.get(1), g, screenConverter);
        List<RealPoint> test = ContourService.getContourPoints(triangles.get(0), triangles.get(1));
        drawContour(ContourService.sortContourPoints(test), g ,screenConverter);

        if(editingLine != null) {
            g.setColor(Color.green);
            drawLine(g, screenConverter, editingLine);
        }

        origG.drawImage(bufferedImage, 0, 0, null);
        g.dispose();
    }

    private static void drawTriangle(SimpleTriangle triangle, Graphics2D g, ScreenConverter sConverter) {
        ScreenPoint screenPoint1 = sConverter.real2Screen(triangle.getPoint1());
        ScreenPoint screenPoint2 = sConverter.real2Screen(triangle.getPoint2());
        ScreenPoint screenPoint3 = sConverter.real2Screen(triangle.getPoint3());
        g.drawLine(screenPoint1.getColumn(), screenPoint1.getRow(), screenPoint2.getColumn(), screenPoint2.getRow());
        g.drawLine(screenPoint2.getColumn(), screenPoint2.getRow(), screenPoint3.getColumn(), screenPoint3.getRow());
        g.drawLine(screenPoint3.getColumn(), screenPoint3.getRow(), screenPoint1.getColumn(), screenPoint1.getRow());
    }

    private static void drawContour(List<RealPoint> contourList, Graphics2D g, ScreenConverter sConverter) {
        if(contourList != null) {
            ScreenPoint startPoint;
            ScreenPoint endPoint;
            for (int i = 0; i < contourList.size(); i++) {
                if (i != (contourList.size() - 1)) {
                    startPoint = sConverter.real2Screen(contourList.get(i));
                    endPoint = sConverter.real2Screen(contourList.get(i + 1));
                } else {
                    startPoint = sConverter.real2Screen(contourList.get(contourList.size() - 1));
                    endPoint = sConverter.real2Screen(contourList.get(0));
                }
                g.setColor(Color.red);
                g.drawLine(startPoint.getColumn(), startPoint.getRow(), endPoint.getColumn(), endPoint.getRow());
            }
        } else {
            System.out.println("Нет точек пересечения или касания");
        }
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
