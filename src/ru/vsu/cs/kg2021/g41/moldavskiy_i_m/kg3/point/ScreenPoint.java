package ru.vsu.cs.kg2021.g41.moldavskiy_i_m.kg3.point;

public class ScreenPoint {

    private final int column, row;

    public ScreenPoint(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
