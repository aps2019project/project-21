package models.Card;

import models.Match.Cell;

import java.util.ArrayList;
import java.util.List;

public class Spell extends Card {
    private List<Cell> targetZone = new ArrayList<>();
    private CardEffect effect = new CardEffect();

    public Spell(String name) {
        super(name);
    }

    public void castSpell() {

    }

}
