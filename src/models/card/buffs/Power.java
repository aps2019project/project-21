package models.card.buffs;

import models.card.ApplyType;
import models.card.Attacker;
import models.card.Buff;

public class Power extends Buff {
    private int value;

    public Power(int value, int duration, Attacker attacker) {
        super(duration, null, attacker, ApplyType.ON_ATTACKER);
        this.value = value;
    }

    public void apply() {
        if (duration < 0 || attacker == null)
            return;
        attacker.increaseHP(value);
        duration--;
    }
}
