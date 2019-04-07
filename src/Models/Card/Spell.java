package Models.Card;

import Models.Match.Cell;

import java.util.ArrayList;
import java.util.List;

class Spell extends Card {
    private List<Cell> targetZone = new ArrayList<>();
    private CardEffect effect = new CardEffect();

    Spell(String name) {
        super(name);
    }
}
