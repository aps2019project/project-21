package models.card.buffs;

import models.card.EffectApplyInfo;
import models.card.Buff;
import models.card.BuffMode;
import models.match.Cell;

/**
 * Flaming cell; Any attacker standing on will get damaged.
 */
public class Flame extends Buff {
    public Flame(int duration) {
        super(duration, EffectApplyInfo.ON_CELL, BuffMode.EVIL);
    }

    public void apply() {
        if (duration < 0 || cell == null)
            return;
        if (cell.getAttacker() != null)
            cell.getAttacker().decreaseHP(2);
        duration--;
    }
}
