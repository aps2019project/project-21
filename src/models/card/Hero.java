package models.card;

public class Hero extends Attacker {
    private int mp;
    private int cooldown;

    public Hero(String name, int price, int manaCost, int maxHp, int maxAp,
                int attackRange, AttackMode attackMode, Spell specialPower,
                int cooldown, int mp) {
        super(name, price, manaCost, maxHp, maxAp, attackRange, attackMode, specialPower);
        this.mp = mp;
        this.cooldown = cooldown;
        super.canAttack = true;
        super.canMove = true;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}
