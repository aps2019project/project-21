package models.Item;

import models.Match.Cell;

public class Collectable extends Item {
    private Cell currentCell;
    private int id;

    public Collectable(String name){
        super(name);
    }
}
