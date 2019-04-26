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
    private boolean isStunned = false;
    private boolean hasHolyBuff = false;

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

    public void increaseAP(int value) {
        if (value < 0)
            return;
        this.ap += value;
    }

    public void decreaseAP(int value) {
        if (value < 0)
            return;
        this.ap -= value;
        //  what if ap goes negative?
    }

    public void stun() {
        isStunned = true;
    }

    public void unStun() {
        isStunned = false;
    }

    public void decreaseHP(int value) {
        if (value < 0)
            return;
        this.hp -= value;
        //  what if he dies?
    }

    public void increaseHP(int value) {
        if (value < 0)
            return;
        this.hp += value;
    }

    public void takeHolyBuff() {
        hasHolyBuff = true;
    }

    public void giveHolyBuff() {
        hasHolyBuff = false;
    }

    public int getHP() {
        return hp;
    }

    public void setHP(int hp) {
        this.hp = hp;
    }

    public int getAP() {
        return ap;
    }

    public void setAP(int ap) {
        this.ap = ap;
    }

    public List<Effect> getAppliedEffects(){
        return appliedEffects;
    }

}
