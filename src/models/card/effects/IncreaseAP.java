package models.card.effects;

import models.card.Effect;

/**
 * Increases AP for an attacker
 */
public class IncreaseAP extends Effect {
    private static final long serialVersionUID = 6529685098267757023L;

    private int changeValue;

    public IncreaseAP(int changeValue) {
        super();
        this.changeValue = changeValue;
    }

    public void apply() {
        if (attacker == null)
            return;
        attacker.increaseAP(changeValue);
    }
}
