package models.Item;

import models.match.Cell;

public class Flag extends Item {
    private static final long serialVersionUID = 6529685098267757035L;

    private int holdingTime = 0;

    public Flag(Cell cell) {
        this.currentCell = cell;
    }

    public int getHoldingTime() {
        return holdingTime;
    }

    public void setHoldingTime(int holdingTime) {
        this.holdingTime = holdingTime;
    }

    public void increaseHoldingTime() {
        this.holdingTime++;
    }
}
