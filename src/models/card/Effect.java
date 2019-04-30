package models.card;

import models.card.buffs.Weakness;
import models.card.buffs.WeaknessMode;
import models.card.effects.EffectType;
import models.match.Cell;

import java.util.ArrayList;
import java.util.List;

public class Effect {
    protected ApplyType applyType;
    protected EffectType effectType;
    protected List<String> effectArguments = new ArrayList<>();
    protected Cell cell;
    protected Attacker attacker;

    public Effect() {

    }

    public Effect(ApplyType applyType, EffectType effectType) {
        this.applyType = applyType;
        this.effectType = effectType;
    }

    protected Effect(Cell cell, Attacker attacker, ApplyType applyType, EffectType effectType) {
        this.cell = cell;
        this.attacker = attacker;
        this.applyType = applyType;
        this.effectType = effectType;
    }

    public void apply() {

    }

    public ApplyType getApplyType() {
        return applyType;
    }

    public EffectType getEffectType() {
        return effectType;
    }

    public List<String> getEffectArguments() {
        return effectArguments;
    }

}