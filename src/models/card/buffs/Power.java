package models.card.buffs;

import models.card.ApplyType;
import models.card.Attacker;
import models.card.Buff;
import models.card.BuffMode;

public class Power extends Buff {
    private int value;
    private PowerMode mode;

    public Power(int value, int duration, Attacker attacker, PowerMode mode) {
        super(duration, null, attacker, ApplyType.ON_ATTACKER, BuffMode.GOOD);
        this.value = value;
        this.mode = mode;
    }

    public void apply() {
        if (duration < 0 || attacker == null)
            return;
        if (mode == PowerMode.AP)
            attacker.increaseAP(value);
        else
            attacker.increaseHP(value);
        duration--;
    }
}

enum PowerMode {
    AP,
    HP
}