package models.card.buffs;

import models.card.ApplyType;
import models.card.Buff;
import models.card.BuffMode;

/**
 * Disarms an attacker
 */
public class Disarm extends Buff {
    public Disarm(int duration) {
        super(duration, BuffMode.EVIL);
    }

    public Disarm(int duration, ApplyType applyType) {
        super(duration, BuffMode.EVIL);
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
