package models.card.effects;

import models.card.Effect;

/**
 * Increases AP for an attacker
 */
public class IncreaseHP extends Effect {
    private static final long serialVersionUID = 6529685098267757024L;

    private int changeValue;

    public IncreaseHP(int changeValue) {
        super();
        this.changeValue = changeValue;
    }

    public void apply() {
        if (attacker == null)
            return;
        attacker.increaseHP(changeValue);
    }
}
