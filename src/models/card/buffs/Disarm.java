package models.card.buffs;

import models.card.Buff;
import models.card.BuffMode;

/**
 * Disarms an attacker
 */
public class Disarm extends Buff {
    private static final long serialVersionUID = 6529685098267757011L;

    public Disarm(int duration) {
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
