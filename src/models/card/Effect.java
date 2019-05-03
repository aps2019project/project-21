package models.card;

import models.Player;
import models.match.Cell;
import models.match.Match;

public class Effect {
    protected Cell cell;
    protected Attacker attacker;
    protected Player player;
    protected Match match;
    protected ActivationType activationType = ActivationType.NONE;

    public Effect() {

    }

    protected Effect(Cell cell, Attacker attacker, ApplyOnCellOrAttacker applyOnCellOrAttacker) {
        this.cell = cell;
        this.attacker = attacker;
    }

    public void apply() {

    }

    public void setAttacker(Attacker attacker) {
        this.attacker = attacker;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setActivationType(ActivationType activationType) {
        this.activationType = activationType;
    }
}