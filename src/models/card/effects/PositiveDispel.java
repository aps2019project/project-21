package models.card.effects;

import models.card.ApplyType;
import models.card.Attacker;
import models.card.Effect;
import models.match.Cell;

import java.util.List;

public class PositiveDispel extends Effect {
    public PositiveDispel(Cell cell, ApplyType applyType) {
        super(cell, null, applyType);
    }

    public PositiveDispel(Attacker attacker, ApplyType applyType) {
        super(null, attacker, applyType);
    }

    public void apply() {
        if (cell == null && attacker == null)
            return;
        Attacker attacker;
        if (cell != null)
            attacker = cell.getAttacker();
        else
            attacker = this.attacker;
        if (attacker == null)
            return;

        List<Effect> effects = attacker.getAppliedEffects();
        if (applyType == ApplyType.ON_ALLY) {
            for (Effect effect : effects) {
                //  check only for buffs and evil buffs then remove
            }
        } else if (applyType == ApplyType.ON_OPP) {
            for (Effect effect : effects) {
                //  check only for buffs and good buffs then remove
            }
        }
    }
}
