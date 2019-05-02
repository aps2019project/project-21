package models.card.effects;

import models.card.EffectApplyInfo;
import models.card.Attacker;
import models.card.Effect;
import models.match.Cell;

import java.util.List;

public class PositiveDispel extends Effect {
    public PositiveDispel(EffectApplyInfo effectApplyInfo) {
        super(effectApplyInfo);
    }

    public PositiveDispel(Cell cell, EffectApplyInfo effectApplyInfo) {
        super(cell, null, effectApplyInfo);
    }

    public PositiveDispel(Attacker attacker, EffectApplyInfo effectApplyInfo) {
        super(null, attacker, effectApplyInfo);
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
        if (effectApplyInfo == EffectApplyInfo.ON_ALLY) {
            for (Effect effect : effects) {
                //  check only for buffs and evil buffs then remove
            }
        } else if (effectApplyInfo == EffectApplyInfo.ON_OPP) {
            for (Effect effect : effects) {
                //  check only for buffs and good buffs then remove
            }
        }
    }
}
