package models.Card;

import models.Match.Cell;

import java.util.ArrayList;
import java.util.List;

public class Attacker extends Card {
    private int hp;
    private int ap;
    private Cell currentCell;
    private Spell specialPower;
    private List<Effect> appliedEffects = new ArrayList<>();
    private int range;
    private AttackMode attackMode;

    Attacker() {

    }

    Attacker(String name, int hp, int ap, Spell specialPower) {
        super(name);
        this.hp = hp;
        this.ap = ap;
        currentCell = new Cell();
        this.specialPower = specialPower;
    }

    public void move() {

    }

    public void attack() {

    }

    public void counterAttack() {

    }
}
