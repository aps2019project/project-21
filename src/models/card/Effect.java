package models.card;

import models.match.Cell;

public class Effect {
    protected ApplyType applyType;
    protected Cell cell;
    protected Attacker attacker;

    public Effect() {

    }

    public Effect(ApplyType applyType) {
        this.applyType = applyType;
    }

    protected Effect(Cell cell, Attacker attacker, ApplyType applyType) {
        this.cell = cell;
        this.attacker = attacker;
        this.applyType = applyType;
    }

    public void apply() {

    }

    public ApplyType getApplyType() {
        return applyType;
    }
}