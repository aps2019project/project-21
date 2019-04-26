package models.card.effects;

import models.card.ApplyType;
import models.card.Attacker;
import models.card.Effect;

public class ChangeAP extends Effect {
    private int changeValue;
    public ChangeAP(Attacker attacker, int changeValue) {
        super(null, attacker, ApplyType.ON_ATTACKER);
        this.changeValue = changeValue;
    }

    public void apply() {
        if(attacker == null)
            return;
        attacker.changeAP(changeValue);
    }
}
