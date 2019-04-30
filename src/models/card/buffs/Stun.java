package models.card.buffs;

import models.card.ApplyType;
import models.card.Attacker;
import models.card.Buff;
import models.card.BuffMode;
import models.card.effects.EffectType;

public class Stun extends Buff {
    public Stun(int duration) {
        super(duration, ApplyType.ON_ATTACKER, BuffMode.EVIL, EffectType.STUN);
    }

    public Stun(int duration, Attacker attacker) {
        super(duration, null, attacker, ApplyType.ON_ATTACKER, BuffMode.EVIL, EffectType.STUN);
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
