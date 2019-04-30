package models.card;

import models.match.Cell;
import models.match.Match;

import java.util.ArrayList;
import java.util.List;

public class Attacker extends Card {
    private int maxHp;
    private int maxAp;
    private int hp;
    private int ap;

    private Spell specialPower;
    private List<Effect> appliedEffects = new ArrayList<>();
    private int attackRange;
    private AttackMode attackMode;

    private boolean isDisarmed = false;
    private boolean isStunned = false;
    private boolean hasHolyBuff = false;

    Attacker() {

    }

    Attacker(String name, int price, int manaCost, int hp, int ap, Spell specialPower) {
        super(name, price, manaCost);
        this.maxAp = ap;
        this.maxHp = hp;
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

    public void addEffect(List<Effect> effects) {
        appliedEffects.addAll(effects);
    }

    public void move(int x, int y) {

    }

    public void attack() {

    }

    public void counterAttack() {

    }

    public void castSpecialPower(Match match, Cell target) {
        //  check if he can use spell

        specialPower.castSpell(match, target);
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

    public List<Effect> getAppliedEffects() {
        return appliedEffects;
    }

    public boolean hasSpecialPower() {
        return specialPower != null;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell cell) {
        this.currentCell = cell;
    }

}
