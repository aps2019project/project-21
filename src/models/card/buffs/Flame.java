package models.card.buffs;

import models.card.ApplyType;
import models.card.Buff;
import models.card.BuffMode;
import models.card.effects.EffectType;
import models.match.Cell;

public class Flame extends Buff {
    public Flame(int duration) {
        super(duration, ApplyType.ON_CELL, BuffMode.EVIL, EffectType.FLAME);
    }

    public Flame(int duration, Cell cell) {
        super(duration, cell, null, ApplyType.ON_CELL, BuffMode.EVIL, EffectType.FLAME);
    }

    public void apply() {
        if (duration < 0 || cell == null)
            return;
        if (cell.getAttacker() != null)
            cell.getAttacker().decreaseHP(2);
        duration--;
    }
}
