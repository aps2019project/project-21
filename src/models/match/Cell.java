package models.match;

import models.card.Attacker;
import models.card.Effect;

import java.util.ArrayList;

public class Cell {
    private int width;
    private int length;
    private ArrayList<Effect> effects = new ArrayList<>();
    private boolean empty;

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public Cell() {

    }

    public Cell(int width, int length, boolean empty) {
        this.width = width;
        this.length = length;
        this.empty = empty;
    }

    public Attacker getAttacker() {
        return null;
    }

    public boolean hasEffect() {
        return !effects.isEmpty();
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
}
