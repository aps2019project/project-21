package models.card.buffs;

import models.card.Attacker;
import models.card.Buff;
import models.card.BuffMode;

/**
 * Increases AP of an attacker
 */
public class PowerAP extends Buff {
    private static final long serialVersionUID = 6529685098267757016L;

    private int value;
    private int initialAP;

    public PowerAP(int duration, int value) {
        super(duration, BuffMode.GOOD);
        this.value = value;
    }

    public void apply() {
        if (duration < 0 || attacker == null)
            return;
        if (duration == 0)
            attacker.setAP(initialAP);
        else
            attacker.increaseAP(value);
        duration--;
    }

    public int getValue() {
        return value;
    }

    public void setAttacker(Attacker attacker) {
        this.attacker = attacker;
        this.initialAP = attacker.getAP();
    }
}

