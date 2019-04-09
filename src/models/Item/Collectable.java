package models.Item;

import models.Match.Cell;

public class Collectable extends Item {
    private Cell currentCell;
    private int id;

    public Collectable(String name){
        super(name);
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
