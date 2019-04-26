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

    Attacker() {

    }

    Attacker(String name, int hp, int ap, Spell specialPower) {
//        super(name);
        this.hp = hp;
        this.ap = ap;
        currentCell = new Cell();
        this.specialPower = specialPower;
    }

    public void move(int x, int y) {

    }

    public void attack() {

    }

    public void counterAttack() {

    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAp() {
        return ap;
    }

    public void setAp(int ap) {
        this.ap = ap;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public Spell getSpecialPower() {
        return specialPower;
    }

    public void setSpecialPower(Spell specialPower) {
        this.specialPower = specialPower;
    }

    public List<Effect> getAppliedEffects() {
        return appliedEffects;
    }

    public void setAppliedEffects(List<Effect> appliedEffects) {
        this.appliedEffects = appliedEffects;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public AttackMode getAttackMode() {
        return attackMode;
    }

    public void setAttackMode(AttackMode attackMode) {
        this.attackMode = attackMode;
    }
}
