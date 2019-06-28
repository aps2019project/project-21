package models.card.effects;

import models.card.Effect;
import models.card.buffs.*;

/**
 * Gives an amount of num effects to an attacker
 */
public class GiveEffect extends Effect {
    private int duration;
    private int num;
    private int value;
    private EffectName effectName;

    public GiveEffect(int duration, EffectName effectName) {
        super();
        this.duration = duration;
        this.effectName = effectName;
    }

    public GiveEffect(int duration, int num, EffectName effectName) {
        super();
        this.duration = duration;
        this.num = num;
        this.effectName = effectName;
    }

    public GiveEffect(EffectName effectName) {
        super();
        this.effectName = effectName;
    }

    public GiveEffect(int duration, EffectName effectName, int value) {
        super();
        this.duration = duration;
        this.value = value;
        this.effectName = effectName;
    }

    public void apply() {
        if (match == null || player == null)
            return;
        if (attacker == null)
            return;
        for (int i = 0; i < num; i++) {
            Effect effect = extractEffect();
            attacker.addEffect(effect);
        }
    }

    private Effect extractEffect() {
        switch (effectName) {
            case DISARM:
                return new Disarm(duration);
            case HOLY:
                return new Holy(duration);
            case POWER_AP:
                return new PowerAP(duration, value);
            case POWER_HP:
                return new PowerHP(duration, value);
            case POSITIVE_DISPEL:
                return new PositiveDispel();
            case INCREASE_AP:
                return new IncreaseAP(value);
            case INCREASE_HP:
                return new IncreaseHP(value);
            case DECREASE_HP:
                return new DecreaseHP(value);
            case POISON:
                return new Poison(duration);
            case FLAME:
                return new Flame(duration);
            case WEAKNESS_AP:
                return new WeaknessAP(duration, value);
            case WEAKNESS_HP:
                return new WeaknessHP(duration, value);
            case INCREASE_MANA:
                return new IncreaseMana(duration, value);
        }
        return null;
    }
}
