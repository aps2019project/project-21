package models.card.effects;

import models.card.ApplyType;
import models.card.Attacker;
import models.card.Effect;

public class DecreaseHP extends Effect {
    private int value;

    public DecreaseHP(Attacker attacker, int value) {
        super(null, attacker, ApplyType.ON_ATTACKER);
        this.value = value;
    }

    public void apply() {
        if (attacker == null)
            return;
        attacker.decreaseHP(value);
    }
}
