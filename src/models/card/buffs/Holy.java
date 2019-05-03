package models.card.buffs;

import models.card.EffectApplyInfo;
import models.card.Attacker;
import models.card.Buff;
import models.card.BuffMode;

/**
 * holy buff for an attacker
 */
public class Holy extends Buff {
    public Holy(int duration){
        super(duration, EffectApplyInfo.ON_ATTACKER, BuffMode.GOOD);
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
