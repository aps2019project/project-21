package models.card.buffs;

import models.card.EffectApplyInfo;
import models.card.Attacker;
import models.card.Buff;
import models.card.BuffMode;

public class Stun extends Buff {
    public Stun(int duration) {
        super(duration, EffectApplyInfo.ON_ATTACKER, BuffMode.EVIL);
    }

    public Stun(int duration, Attacker attacker) {
        super(duration, null, attacker, EffectApplyInfo.ON_ATTACKER, BuffMode.EVIL);
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
