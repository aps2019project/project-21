package models.card.effects;

import models.card.ApplyType;
import models.card.Attacker;
import models.card.Effect;

import java.util.ArrayList;

public class DecreaseHP extends Effect {
    private int value;

    public DecreaseHP(int value) {
        super(ApplyType.ON_ATTACKER, EffectType.DECREASE_HP);
        this.value = value;
        super.effectArguments.add(Integer.toString(value));
    }

    public DecreaseHP(Attacker attacker, int value) {
        super(null, attacker, ApplyType.ON_ATTACKER, EffectType.DECREASE_HP);
        this.value = value;
        super.effectArguments.add(Integer.toString(value));
    }

    public void apply() {
        if (attacker == null)
            return;
        attacker.decreaseHP(value);
    }
}
