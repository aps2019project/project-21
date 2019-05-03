package models.card.buffs;

import models.card.*;
import models.match.Cell;

/**
 * Poisons a cell or an attacker
 */
public class Poison extends Buff {
    public Poison(int duration, ActivationType activationType) {
        super(duration, EffectApplyInfo.ON_ATTACKER, BuffMode.EVIL);
        super.activationType = activationType;
    }

    public Poison(int duration) {
        super(duration, EffectApplyInfo.ON_ATTACKER, BuffMode.EVIL);
    }

    public Poison(int duration, EffectApplyInfo effectApplyInfo) {
        super(duration, effectApplyInfo, BuffMode.EVIL);
    }

    public void apply() {
        if (duration < 0 || attacker == null && cell == null)
            return;
        if (attacker != null)
            attacker.decreaseHP(1);
        else if (cell.getAttacker() != null)
            cell.getAttacker().decreaseHP(1);
        duration--;
    }
}
