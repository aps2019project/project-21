package models.card.buffs;

import models.card.ApplyType;
import models.card.Attacker;
import models.card.Buff;
import models.card.BuffMode;
import models.card.effects.EffectType;

public class Power extends Buff {
    private int value;
    private PowerMode powerMode;

    public Power(int duration, int value, PowerMode powerMode) {
        super(duration, ApplyType.ON_ATTACKER, BuffMode.GOOD, EffectType.POWER);
        this.value = value;
        super.effectArguments.add(Integer.toString(value));
        this.powerMode = powerMode;
        super.effectArguments.add(powerMode.toString());
    }

    public Power(int duration, int value, PowerMode powerMode, ApplyType applyType) {
        super(duration, applyType, BuffMode.GOOD, EffectType.POWER);
        this.value = value;
        super.effectArguments.add(Integer.toString(value));
        this.powerMode = powerMode;
        super.effectArguments.add(powerMode.toString());
    }

    public Power(int duration, int value, Attacker attacker, PowerMode mode) {
        super(duration, null, attacker, ApplyType.ON_ATTACKER, BuffMode.GOOD, EffectType.POWER);
        this.value = value;
        super.effectArguments.add(Integer.toString(value));
        this.powerMode = mode;
        super.effectArguments.add(powerMode.toString());
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

