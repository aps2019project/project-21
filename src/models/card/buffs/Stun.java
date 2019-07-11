package models.card.buffs;

import models.card.Buff;
import models.card.BuffMode;

/**
 * Stuns an attacker
 */
public class Stun extends Buff {
    private static final long serialVersionUID = 6529685098267757018L;

    public Stun(int duration) {
        super(duration, BuffMode.EVIL);
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
