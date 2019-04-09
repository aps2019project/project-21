package models.Match;

import models.Card.Attacker;
import models.Card.Effect;

public class Cell {
    private int width;
    private int length;
    private Effect effect;

    public Cell() {

    }

    public Cell(int width, int length) {
        this.width = width;
        this.length = length;
    }

    public Attacker getAttacker(){
        return null;
    }

    public boolean hasEffect(){
        return effect != null;
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

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }
}
