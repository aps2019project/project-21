package models.card.buffs;

import models.card.ApplyType;
import models.card.Attacker;
import models.card.Buff;
import models.card.BuffMode;
import models.card.effects.EffectType;

public class Disarm extends Buff {
    public Disarm(int duration) {
        super(duration, ApplyType.ON_ATTACKER, BuffMode.EVIL, EffectType.DISARM);
    }

    public Disarm(int duration, ApplyType applyType){
        super(duration, applyType, BuffMode.EVIL, EffectType.DISARM);
    }

    public Disarm(int duration, Attacker attacker) {
        super(duration, null, attacker, ApplyType.ON_ATTACKER, BuffMode.EVIL, EffectType.DISARM);
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
