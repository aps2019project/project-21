package models.card;

import models.match.Cell;

import java.util.ArrayList;
import java.util.List;

public class Spell extends Card {
    private List<Cell> targetZone = new ArrayList<>();
    private List<Card> targetCards = new ArrayList<>();
    private Effect effect = new Effect();
    private String description;

//    public Spell(String name) {
//        super(name);
//    }

    public void castSpell() {

    }

    public List<Cell> getTargetZone() {
        return targetZone;
    }

    public void setTargetZone(List<Cell> targetZone) {
        this.targetZone = targetZone;
    }

    public List<Card> getTargetCards() {
        return targetCards;
    }

    public void setTargetCards(List<Card> targetCards) {
        this.targetCards = targetCards;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
