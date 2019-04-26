package models.card.buffs;

import models.card.ApplyType;
import models.card.Attacker;
import models.card.Buff;

public class Holy extends Buff {
    public Holy(int duration, Attacker attacker) {
        super(duration, null, attacker, ApplyType.ON_ATTACKER);
    }

    public void apply() {
        if (duration < 0 || attacker == null)
            return;
        if (duration == 0)
            attacker.giveHolyBuff();
        else
            attacker.takeHolyBuff();
        duration--;
    }
}
