package models.card.buffs;

import models.card.EffectApplyInfo;
import models.card.Attacker;
import models.card.Buff;
import models.card.BuffMode;

/**
 * Stuns an attacker
 */
public class Stun extends Buff {
    public Stun(int duration) {
        super(duration, EffectApplyInfo.ON_ATTACKER, BuffMode.EVIL);
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
