package models.card;

import models.match.Cell;

public abstract class Effect {
    protected ApplyType applyType;
    protected Cell cell;
    protected Attacker attacker;

    protected Effect(Cell cell, Attacker attacker, ApplyType applyType) {
        this.cell = cell;
        this.attacker = attacker;
        this.applyType = applyType;
    }

    public abstract void apply();
}