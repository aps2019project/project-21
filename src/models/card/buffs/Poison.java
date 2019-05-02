package models.card.buffs;

import models.card.ApplyType;
import models.card.Attacker;
import models.card.Buff;
import models.card.BuffMode;
import models.card.effects.EffectType;
import models.match.Cell;

public class Poison extends Buff {
    public Poison(int duration) {
        super(duration, ApplyType.ON_ATTACKER, BuffMode.EVIL, EffectType.POISON);
    }

    public Poison(int duration, Attacker attacker) {
        super(duration, null, attacker, ApplyType.ON_ATTACKER, BuffMode.EVIL, EffectType.POISON);
    }

    public Poison(int duration, ApplyType applyType) {
        super(duration, applyType, BuffMode.EVIL, EffectType.POISON);
    }

    public Poison(int duration, Cell cell) {
        super(duration, cell, null, ApplyType.ON_CELL, BuffMode.EVIL, EffectType.POISON);
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
