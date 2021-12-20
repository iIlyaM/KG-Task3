package ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3;

public class ScreenConverter {
    private double realStartX, realStartY, realWidth, realHeight;
    private int screenWidth, screenHeight;

    public ScreenConverter(double realStartX, double realStartY,
                           double realWidth, double realHeight, int screenWidth, int screenHeight) {
        this.realStartX = realStartX;
        this.realStartY = realStartY;
        this.realWidth = realWidth;
        this.realHeight = realHeight;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public ScreenPoint real2Screen(RealPoint point) {
        double x = ((point.getX() - realStartX) / realWidth) * screenWidth;
        double y = ((realStartY - point.getY()) / realHeight) * screenHeight;
        return  new ScreenPoint((int) x, (int) y);
    }

    public RealPoint screen2Real(ScreenPoint point) {
        double x = realStartX + ((point.getColumn() * realWidth) / screenWidth);
        double y = realStartY - ((point.getRow() * realHeight) / screenHeight);
        return new RealPoint(x, y);
    }

    public void moveCorner(RealPoint delta) {
        realStartX += delta.getX();
        realStartY += delta.getY();
    }

    public void changeScale(double scale) {
        realWidth *= scale;
        realHeight *= scale;
    }

    public double getRealStartX() {
        return realStartX;
    }

    public void setRealStartX(double realStartX) {
        this.realStartX = realStartX;
    }

    public double getRealStartY() {
        return realStartY;
    }

    public void setRealStartY(double realStartY) {
        this.realStartY = realStartY;
    }

    public double getRealWidth() {
        return realWidth;
    }

    public void setRealWidth(double realWidth) {
        this.realWidth = realWidth;
    }

    public double getRealHeight() {
        return realHeight;
    }

    public void setRealHeight(double realHeight) {
        this.realHeight = realHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }
}
