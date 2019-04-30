package models.card.buffs;

import models.card.ApplyType;
import models.card.Attacker;
import models.card.Buff;
import models.card.BuffMode;
import models.card.effects.EffectType;

public class Weakness extends Buff {
    private int value;
    private int initialHP;
    private int initialAP;
    private WeaknessMode weaknessMode;

    public Weakness(){
        super();
    }

    public Weakness(int duration, int value, WeaknessMode weaknessMode) {
        super(duration, ApplyType.ON_ATTACKER, BuffMode.EVIL, EffectType.WEAKNESS);
        this.value = value;
        super.effectArguments.add(Integer.toString(value));
        this.weaknessMode = weaknessMode;
        super.effectArguments.add(weaknessMode.toString());
    }

    public Weakness(int duration, int value, Attacker attacker, WeaknessMode mode) {
        super(duration, null, attacker, ApplyType.ON_ATTACKER, BuffMode.EVIL, EffectType.WEAKNESS);
        this.value = value;
        super.effectArguments.add(Integer.toString(value));
        this.weaknessMode = mode;
        super.effectArguments.add(weaknessMode.toString());
        initialHP = attacker.getHP();
        initialAP = attacker.getAP();
    }

    public void apply() {
        if (duration < 0 || attacker == null)
            return;
        if (weaknessMode == WeaknessMode.HP) {
            if (duration == 0)
                attacker.setHP(initialHP);
            else
                attacker.decreaseHP(value);
        } else {
            if (duration == 0)
                attacker.setAP(initialAP);
            else
                attacker.decreaseAP(value);
        }
        duration--;
    }

    public int getValue() {
        return value;
    }

    public int getInitialHP() {
        return initialHP;
    }

    public int getInitialAP() {
        return initialAP;
    }

    public WeaknessMode getWeaknessMode() {
        return weaknessMode;
    }
}