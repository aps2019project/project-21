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
}
