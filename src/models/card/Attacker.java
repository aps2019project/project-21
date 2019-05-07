package models.card;

import models.Player;
import models.match.Cell;
import models.match.Match;

import java.util.ArrayList;
import java.util.List;

public class Attacker extends Card {
    private int maxHp;
    private int maxAp;
    private int hp;
    private int ap;

    private Spell specialPower = new Spell();
    private List<Effect> appliedEffects = new ArrayList<>();

    private int attackRange;

    private AttackMode attackMode = AttackMode.HYBRID; //  default
    protected boolean canMove;
    protected boolean canAttack;
    private boolean isDisarmed = false;
    private boolean isStunned = false;
    private boolean hasHolyBuff = false;

    Attacker() {

    }

    Attacker(String name, int price, int manaCost, int maxHp, int maxAp,
             int attackRange, AttackMode attackMode, Spell specialPower) {
        super(name, price, manaCost);
        this.maxAp = maxAp;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.ap = maxAp;
        this.specialPower = specialPower;
        this.attackRange = attackRange;
        this.attackMode = attackMode;
        currentCell = new Cell();
    }

    public void manageEffects() {
        //  must be called each turn for each attacker
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void addEffect(Effect effect) {
        appliedEffects.add(effect);
    }

    public void addEffect(List<Effect> effects) {
        appliedEffects.addAll(effects);
    }

    public void removeEffect(Effect effect) {
        if (effect == null)
            return;
        appliedEffects.remove(effect);
    }

    public void move(int x, int y) {

    }

    public void attack() {

    }

    public void counterAttack() {

    }

    public void castSpecialPower(Match match, Player player, Cell target) {
        //  check if he can use spell , has enough mana

        specialPower.castSpell(match, player, target);
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

    public AttackMode getAttackMode() {
        return attackMode;
    }

    public boolean hasSpecialPower() {
        return specialPower != null;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell cell) {
        this.currentCell = cell;
        cell.setCurrentAttacker(this);
    }

    public Spell getSpecialPower() {
        return specialPower;
    }

    public static Attacker getAttackerById(String id) {
        if (!id.matches("\\d+"))
            return null;
        return getAttackerByID(Integer.parseInt(id));
    }

    public static Attacker getAttackerByID(int id) {
        for (Attacker attacker : getAttackers())
            if (attacker.id == id)
                return attacker;
        return null;
    }

    public static List<Attacker> getAttackers() {
        List<Attacker> attackers = new ArrayList<>();
        attackers.addAll(Minion.getMinions());
        attackers.addAll(Hero.getHeroes());
        return attackers;
    }

    public boolean canAttack() {
        return canAttack;
    }

    public boolean canMove() {
        return canMove;
    }

    public void setCannotAttack() {
        this.canAttack = false;
    }

    public void setCannotMove() {
        this.canMove = false;
    }

    public void setCanAttack() {
        this.canAttack = true;
    }

    public void setCanMove() {
        this.canMove = true;
    }

    public void reset() {
        super.reset();
        this.hp = maxHp;
        this.ap = maxAp;
        appliedEffects = new ArrayList<>();
        isStunned = false;
        isDisarmed = false;
        hasHolyBuff = false;
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public String getDesc() {
        if (hasSpecialPower())
            return this.specialPower.getDesc();
        return "";
    }
}
