package models.match;

public class Battlefield {
    private int width;
    private int length;
    private Cell[][] cells;


    Battlefield() {
        this.width = 5;
        this.length = 9;
        this.cells = new Cell[width][length];
        initializeCells();
    }

    private void initializeCells() {
        for (int i = 0; i < width; i++)
            for (int j = 0; j < length; j++)
                cells[i][j] = new Cell(i, j);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public Cell getCell(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y > length)
            return null;
        return cells[x][y];
    }
}