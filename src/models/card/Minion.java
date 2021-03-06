package models.card;

import java.util.ArrayList;
import java.util.List;

public class Minion extends Attacker {
    private static final long serialVersionUID = 6529685098267757031L;

    private static List<Minion> minions = new ArrayList<>();
    private boolean isCombo;

    public Minion(String name, int price, int manaCost, int maxHp, int maxAp,
                  int attackRange, AttackMode attackMode, Spell specialPower) {
        super(name, price, manaCost, maxHp, maxAp, attackRange, attackMode, specialPower);
        super.canAttack = false;
        super.canMove = false;
        this.isCombo = false;
    }

    public Minion(String name, int price, int manaCost, int maxHp, int maxAp,
                  int attackRange, AttackMode attackMode, Spell specialPower, boolean isCombo) {
        super(name, price, manaCost, maxHp, maxAp, attackRange, attackMode, specialPower);
        super.canAttack = false;
        super.canMove = false;
        this.isCombo = isCombo;
    }

    public static List<Minion> getMinions() {
        return minions;
    }

    static void addMinion(Minion minion) {
        if (minion == null)
            return;
        minions.add(minion);
    }

    public static void addMinion(List<Minion> minions) {
        if (minions == null)
            return;
        for (Minion minion : minions)
            addMinion(minion);
    }

    public boolean isCombo() {
        return this.isCombo;
    }

    public void reset() {
        super.reset();
        canAttack = false;
        canMove = false;
    }

}
