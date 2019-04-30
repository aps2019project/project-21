package models.card.effects;

import models.card.ApplyType;
import models.card.Attacker;
import models.card.Effect;

public class IncreaseAP extends Effect {
    private int changeValue;

    public IncreaseAP(int changeValue) {
        super(null, EffectType.INCREASE_AP);
        this.changeValue = changeValue;
        super.effectArguments.add(Integer.toString(changeValue));
    }

    public IncreaseAP(Attacker attacker, int changeValue) {
        super(null, attacker, ApplyType.ON_ATTACKER, EffectType.INCREASE_AP);
        this.changeValue = changeValue;
        super.effectArguments.add(Integer.toString(changeValue));
    }

    public void apply() {
        if (attacker == null)
            return;
        attacker.increaseAP(changeValue);
    }
}
