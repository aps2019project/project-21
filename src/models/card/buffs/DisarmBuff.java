package models.card.buffs;

import models.card.Attacker;
import models.card.Buff;

public class DisarmBuff extends Buff {
    public DisarmBuff(int duration, Attacker attacker) {
        super(duration, null, attacker, null);
    }

    public void apply() {
        if (duration < 0 || attacker == null)
            return;
        if (duration == 0)
            attacker.unDisarm();
        else
            attacker.disarm();
        duration--;
    }
}
