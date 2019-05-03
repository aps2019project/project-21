package models.card.buffs;

import models.card.*;

/**
 * Poisons a cell or an attacker
 */
public class Poison extends Buff {
    public Poison(int duration, ActivationType activationType) {
        super(duration, BuffMode.EVIL);
        super.activationType = activationType;
    }

    public Poison(int duration) {
        super(duration, BuffMode.EVIL);
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
