package models.match;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    List<Cell> getCellsInList() {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < width; i++)
            for (int j = 0; j < length; j++)
                cells.add(this.cells[i][j]);
        return cells;
    }

    public Cell getCell(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y > length)
            return null;
        return cells[x][y];
    }

    Cell getRandomCell() {
        Random random = new Random();
        int m = random.nextInt(5);
        int n = random.nextInt(5) + 2;
        return getCell(m, n);
    }
}
