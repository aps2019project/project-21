package Models.Card;

import Models.Match.Cell;

public class Attacker extends Card {
    private int hp;
    private int ap;
    private Cell currentCell;
    private Spell specialPower;

    Attacker(){

    }

    Attacker(String name, int hp, int ap, Spell specialPower) {
        super(name);
        this.hp = hp;
        this.ap = ap;
        currentCell = new Cell();
        this.specialPower = specialPower;
    }
}
