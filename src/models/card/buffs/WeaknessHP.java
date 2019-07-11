package models.card.buffs;

import models.card.Attacker;
import models.card.Buff;
import models.card.BuffMode;

/**
 * Decreases HP of an attacker
 */
public class WeaknessHP extends Buff {
    private static final long serialVersionUID = 6529685098267757020L;

    private int value;
    private int initialHP;

    public WeaknessHP(int duration, int value) {
        super(duration, BuffMode.EVIL);
        this.value = value;
    }

    public void apply() {
        if (duration < 0 || attacker == null)
            return;
        if (duration == 0)
            attacker.setHP(initialHP);
        else
            attacker.decreaseHP(value);
        duration--;
    }

    public int getValue() {
        return value;
    }

    public int getInitialHP() {
        return initialHP;
    }

    public void setAttacker(Attacker attacker) {
        this.attacker = attacker;
        this.initialHP = attacker.getHP();
    }
}