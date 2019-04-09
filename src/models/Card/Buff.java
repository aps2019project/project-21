package models.Card;

import models.Match.Cell;

public class Buff extends Effect {
    private int durability;
    BuffMode buffMode;

    public void castEffect(Cell cell){
        //  override method in cardEffect
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public BuffMode getBuffMode() {
        return buffMode;
    }

    public void setBuffMode(BuffMode buffMode) {
        this.buffMode = buffMode;
    }
}
