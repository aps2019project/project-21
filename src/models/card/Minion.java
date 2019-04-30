package models.card;

public class Minion extends Attacker {
    private ActivationType activationType;

    public Minion(String name, int price, int manaCost, int maxHp, int maxAp,
                  int attackRange, AttackMode attackMode, Spell specialPower, ActivationType activationType) {
        super(name, price, manaCost, maxHp, maxAp, attackRange, attackMode, specialPower);
        this.activationType = activationType;
        super.canAttack = false;
        super.canMove = false;
    }
}
