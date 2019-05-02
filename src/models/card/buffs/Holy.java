package models.card.buffs;

import models.card.EffectApplyInfo;
import models.card.Attacker;
import models.card.Buff;
import models.card.BuffMode;

public class Holy extends Buff {
    public Holy(int duration){
        super(duration, EffectApplyInfo.ON_ATTACKER, BuffMode.GOOD);
    }

    public Holy(int duration, Attacker attacker) {
        super(duration, null, attacker, EffectApplyInfo.ON_ATTACKER, BuffMode.GOOD);
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
