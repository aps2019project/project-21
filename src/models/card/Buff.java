package models.card;

import models.match.Cell;

import java.io.Serializable;

public class Buff extends Effect  implements Serializable {
    private static final long serialVersionUID = 6529685098267757027L;

    protected int duration;
    protected BuffMode buffMode;

    public Buff() {
        super();
    }

    public Buff(int duration, BuffMode buffMode) {
        this.duration = duration;
        this.buffMode = buffMode;
    }

    protected Buff(int duration, Cell cell, Attacker attacker, ApplyType applyType, BuffMode buffMode) {
        super(cell, attacker, applyType);
        this.duration = duration;
        this.buffMode = buffMode;
    }

    @Override
    public void apply() {

    }

    public int getDuration() {
        return duration;
    }

    public BuffMode getBuffMode() {
        return buffMode;
    }

    public void setBuffMode(BuffMode buffMode) {
        this.buffMode = buffMode;
    }

	public void setDuration(int duration) {
        this.duration = duration;
    }
}
