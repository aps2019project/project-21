package Models.Match;

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
}
