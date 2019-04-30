package models.card.buffs;

import models.card.ApplyType;
import models.card.Attacker;
import models.card.Buff;
import models.card.BuffMode;
import models.card.effects.EffectType;

public class Holy extends Buff {
    public Holy(int duration){
        super(duration, ApplyType.ON_ATTACKER, BuffMode.GOOD, EffectType.HOLY);
    }

    public Holy(int duration, Attacker attacker) {
        super(duration, null, attacker, ApplyType.ON_ATTACKER, BuffMode.GOOD, EffectType.HOLY);
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
