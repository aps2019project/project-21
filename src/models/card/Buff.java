package models.card;

import models.card.effects.EffectType;
import models.match.Cell;

public class Buff extends Effect {
    protected int duration;
    protected BuffMode buffMode;

    public Buff(){
        super();
    }

    public Buff(int duration, ApplyType applyType, BuffMode buffMode, EffectType effectType) {
        super(applyType, effectType);
        this.duration = duration;
        this.buffMode = buffMode;
        super.effectArguments.add(Integer.toString(duration));
    }

    protected Buff(int duration, Cell cell, Attacker attacker, ApplyType applyType, BuffMode buffMode, EffectType effectType) {
        super(cell, attacker, applyType, effectType);
        this.duration = duration;
        this.buffMode = buffMode;
        super.effectArguments.add(Integer.toString(duration));
    }

    @Override
    public void apply(){

    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public BuffMode getBuffMode() {
        return buffMode;
    }

    public void setBuffMode(BuffMode buffMode) {
        this.buffMode = buffMode;
    }
}
