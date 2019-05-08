package models.match;

import models.Item.Collectable;
import models.Item.Flag;
import models.card.Attacker;
import models.card.Effect;

import java.util.ArrayList;
import java.util.List;

public class Cell {

    private int x;
    private int y;

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    private ArrayList<Effect> effects = new ArrayList<>();
    private Collectable collectable;
    private Flag flag;
    private Attacker currentAttacker;

    public Cell(Attacker attacker) {
        this.currentAttacker = attacker;
    }

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

    public void addEffect(Effect effect) {
        this.effects.add(effect);
    }

    public void addEffect(List<Effect> effects) {
        if (effects == null)
            return;
        for (Effect effect : effects)
            addEffect(effect);
    }

    public void setCollectable(Collectable collectable) {
        this.collectable = collectable;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    public void setCurrentAttacker(Attacker attacker) {
        this.currentAttacker = attacker;
    }

    public boolean hasFlag() {
        return this.flag != null;
    }

    public boolean hasCollectable() {
        return this.collectable != null;
    }

    public ArrayList<Effect> getEffects() {
        return effects;
    }

    public Collectable getCollectable() {
        return collectable;
    }

    public Flag getFlag() {
        return flag;
    }

    public Attacker getCurrentAttacker() {
        return currentAttacker;
    }

    public static double getEuclideanDistance(Cell first, Cell second) {
        if (first == null || second == null)
            return -1;
        return Math.sqrt((first.x - second.x) * (first.x - second.x)
                + (first.y - second.y) * (first.y - second.y));

    }
}