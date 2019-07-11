package models.card.buffs;

import models.card.Attacker;
import models.card.Buff;
import models.card.BuffMode;

/**
 * Decreases AP of an attacker
 */
public class WeaknessAP extends Buff {
    private static final long serialVersionUID = 6529685098267757019L;

    private int value;
    private int initialAP;

    public WeaknessAP(int duration, int value) {
        super(duration, BuffMode.EVIL);
        this.value = value;
    }

    public void apply() {
        if (duration < 0 || attacker == null)
            return;
        if (duration == 0)
            attacker.setAP(initialAP);
        else
            attacker.decreaseAP(value);
        duration--;
    }

    public int getValue() {
        return value;
    }

    public int getInitialAP() {
        return initialAP;
    }

    public void setAttacker(Attacker attacker) {
        this.attacker = attacker;
        this.initialAP = attacker.getAP();
    }
}