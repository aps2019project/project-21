package models.card.buffs;

import models.card.EffectApplyInfo;
import models.card.Attacker;
import models.card.Buff;
import models.card.BuffMode;

/**
 * Increases HP of an attacker
 */
public class PowerHP extends Buff {
    private int value;
    private int initialHP;

    public PowerHP(int duration, int value) {
        super(duration, EffectApplyInfo.ON_ATTACKER, BuffMode.GOOD);
        this.value = value;
    }

    public PowerHP(int duration, int value, EffectApplyInfo effectApplyInfo) {
        super(duration, effectApplyInfo, BuffMode.GOOD);
        this.value = value;
    }

    public void apply() {
        if (duration < 0 || attacker == null)
            return;
        if (duration == 0)
            attacker.setHP(initialHP);
        else
            attacker.increaseHP(value);
        duration--;
    }

    public int getValue() {
        return value;
    }

    public void setAttacker(Attacker attacker) {
        this.attacker = attacker;
        this.initialHP = attacker.getHP();
    }
}

