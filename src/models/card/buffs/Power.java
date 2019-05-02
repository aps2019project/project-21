package models.card.buffs;

import models.card.ApplyType;
import models.card.Attacker;
import models.card.Buff;
import models.card.BuffMode;

public class Power extends Buff {
    private int value;
    private PowerMode powerMode;

    public Power(int duration, int value, PowerMode powerMode) {
        super(duration, ApplyType.ON_ATTACKER, BuffMode.GOOD);
        this.value = value;
        this.powerMode = powerMode;
    }

    public Power(int duration, int value, PowerMode powerMode, ApplyType applyType) {
        super(duration, applyType, BuffMode.GOOD);
        this.value = value;
        this.powerMode = powerMode;
    }

    public Power(int duration, int value, Attacker attacker, PowerMode mode) {
        super(duration, null, attacker, ApplyType.ON_ATTACKER, BuffMode.GOOD);
        this.value = value;
        this.powerMode = mode;
    }

    public void apply() {
        if (duration < 0 || attacker == null)
            return;
        if (powerMode == PowerMode.AP)
            attacker.increaseAP(value);
        else
            attacker.increaseHP(value);
        duration--;
    }

    public int getValue() {
        return value;
    }

    public PowerMode getPowerMode() {
        return powerMode;
    }
}

