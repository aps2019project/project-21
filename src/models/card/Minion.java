package models.card;

import java.util.ArrayList;
import java.util.List;

public class Minion extends Attacker {
    private static List<Minion> minions = new ArrayList<>();

    public Minion(String name, int price, int manaCost, int maxHp, int maxAp,
                  int attackRange, AttackMode attackMode, Spell specialPower) {
        super(name, price, manaCost, maxHp, maxAp, attackRange, attackMode, specialPower);
        super.canAttack = false;
        super.canMove = false;
    }

    public static List<Minion> getMinions() {
        return minions;
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
