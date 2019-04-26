package models.card;

import models.match.Cell;

public abstract class Buff extends Effect {
    protected int duration;
    private BuffMode buffMode;

    protected Buff(int duration, Cell cell, Attacker attacker, ApplyType applyType) {
        super(cell, attacker, applyType);
        this.duration = duration;
    }

    @Override
    public abstract void apply();

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
