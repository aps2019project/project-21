package models.card.effects;

import models.card.EffectApplyInfo;
import models.card.Attacker;
import models.card.Effect;

public class DecreaseHP extends Effect {
    private int value;

    public DecreaseHP(int value) {
        super(EffectApplyInfo.ON_ATTACKER);
        this.value = value;
    }

    public DecreaseHP(int value, EffectApplyInfo effectApplyInfo) {
        super(effectApplyInfo);
        this.value = value;
    }

    public DecreaseHP(Attacker attacker, int value) {
        super(null, attacker, EffectApplyInfo.ON_ATTACKER);
        this.value = value;
    }

    public void apply() {
        if (attacker == null)
            return;
        attacker.decreaseHP(value);
    }
}
