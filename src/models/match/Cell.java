package models.match;

import models.Item.Collectable;
import models.Item.Flag;
import models.card.Attacker;
import models.card.Effect;

import java.util.ArrayList;

public class Cell {
    private int x;
    private int y;
    private ArrayList<Effect> effects = new ArrayList<>();
    private Collectable collectable;
    private Flag flag;
    private Attacker currentAttacker;

    public static int getManhattanDistance(Cell first, Cell second) {
        return Math.abs(first.x - second.x) + Math.abs(first.y - second.y);
    }

    public boolean isEmpty() {
        return currentAttacker == null;
    }

    public void setEmpty() {
        currentAttacker = null;
    }

    public Cell() {

    }

    public Cell(int width, int length) {
        this.x = width;
        this.y = length;
    }

    public Attacker getAttacker() {
        return null;
    }

    public void addEffect(Effect effect) {
        this.effects.add(effect);
    }

    public void setCollectable(Collectable collectable) {
        this.collectable = collectable;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    public void setCurrentAttacker(Attacker attacker){
        this.currentAttacker = attacker;
    }

}