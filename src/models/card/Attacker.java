package models.card;

import models.match.Cell;

import java.util.ArrayList;
import java.util.List;

public class Attacker extends Card {
    private int hp;
    private int ap;
    private Cell currentCell;
    private Spell specialPower;
    private List<Effect> appliedEffects = new ArrayList<>();
    private int range;
    private AttackMode attackMode;

    private boolean isDisarmed = false;

    Attacker() {

    }

    Attacker(String name, int hp, int ap, Spell specialPower) {
//        super(name);
        this.hp = hp;
        this.ap = ap;
        currentCell = new Cell();
        this.specialPower = specialPower;
    }

    public void manageEffects() {
        //  must be called each turn for each attacker
    }

    public void addEffect(Effect effect) {
        appliedEffects.add(effect);
    }

    public void move(int x, int y) {

    }

    public void attack() {

    }

    public void counterAttack() {

    }

    public void disarm() {
        isDisarmed = true;
    }

    public void unDisarm() {
        isDisarmed = false;
    }

}
