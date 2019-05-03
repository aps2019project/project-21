package models.card.buffs;

import models.card.Buff;
import models.card.BuffMode;

/**
 * Flaming cell; Any attacker standing on will get damaged.
 */
public class Flame extends Buff {
    public Flame(int duration) {
        super(duration, BuffMode.EVIL);
    }

    public void apply() {
        if (duration < 0 || cell == null)
            return;
        if (cell.getAttacker() != null)
            cell.getAttacker().decreaseHP(2);
        duration--;
    }
}
