package models.card;

import java.util.ArrayList;
import java.util.List;

public class Minion extends Attacker {
    private static List<Minion> minions = new ArrayList<>();
    private ActivationType activationType;

    public Minion(String name, int price, int manaCost, int maxHp, int maxAp,
                  int attackRange, AttackMode attackMode, Spell specialPower, ActivationType activationType) {
        super(name, price, manaCost, maxHp, maxAp, attackRange, attackMode, specialPower);
        this.activationType = activationType;
        super.canAttack = false;
        super.canMove = false;
    }

    public static List<Minion> getMinions() {
        return minions;
    }

    public ActivationType getActivationType() {
        return activationType;
    }

    public static void addMinions(Minion minion) {
        if (minion == null)
            return;
        minions.add(minion);
    }

    public static void addMinion(List<Minion> minions) {
        for (Minion minion : minions)
            addMinions(minion);
    }
}
