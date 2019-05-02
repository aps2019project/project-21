package models.card.buffs;

import models.card.EffectApplyInfo;
import models.card.Attacker;
import models.card.Buff;
import models.card.BuffMode;

public class Disarm extends Buff {
    public Disarm(int duration) {
        super(duration, EffectApplyInfo.ON_ATTACKER, BuffMode.EVIL);
    }

    public Disarm(int duration, EffectApplyInfo effectApplyInfo) {
        super(duration, effectApplyInfo, BuffMode.EVIL);
    }

    public Disarm(int duration, Attacker attacker) {
        super(duration, null, attacker, EffectApplyInfo.ON_ATTACKER, BuffMode.EVIL);
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
