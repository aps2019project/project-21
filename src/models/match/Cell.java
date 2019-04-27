package models.match;

import models.Item.Collectable;
import models.Item.Flag;
import models.card.Attacker;
import models.card.Effect;

import java.util.ArrayList;

public class Cell {
    private int width;
    private int length;
    private ArrayList<Effect> effects = new ArrayList<>();
    private Collectable collectable;
    private Flag flag;

    //  TODO:
    public boolean isEmpty() {
        return false;
    }

    //  TODO:
    public void setEmpty(boolean empty) {
    }

    public Cell() {

    }

    public Cell(int width, int length) {
        this.width = width;
        this.length = length;
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

}
