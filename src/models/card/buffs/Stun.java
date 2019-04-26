package models.card.buffs;

import models.card.ApplyType;
import models.card.Attacker;
import models.card.Buff;
import models.card.BuffMode;

public class Stun extends Buff {
    Stun(int duration, Attacker attacker) {
        super(duration, null, attacker, ApplyType.ON_ATTACKER, BuffMode.EVIL);
    }

    public void apply() {
        if (attacker == null || duration < 0)
            return;
        if (duration == 0)
            attacker.unStun();
        else
            attacker.stun();
        duration--;
    }
}
