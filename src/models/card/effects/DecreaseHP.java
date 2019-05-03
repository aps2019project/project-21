package models.card.effects;

import models.card.Effect;

/**
 * Damages an attacker
 */
public class DecreaseHP extends Effect {
    private int value;

    public DecreaseHP(int value) {
        super();
        this.value = value;
    }

    public void apply() {
        if (attacker == null)
            return;
        attacker.decreaseHP(value);
    }
}
