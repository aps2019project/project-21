package models.Item;

import models.Match.Cell;

public class Flag extends Item {
    private Cell currentCell;

    public Flag(String name){
        super(name);
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }
}
