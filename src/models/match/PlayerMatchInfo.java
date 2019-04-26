package models.match;

import models.card.Card;

import java.util.ArrayList;
import java.util.List;

public class PlayerMatchInfo {
    private static final int MAX_MANA = 9;
    private List<Card> graveyardOne = new ArrayList<>();
    private List<Card> graveyardTwo = new ArrayList<>();
    private int mana = 3;

    PlayerMatchInfo() {

    }

    public static int getMaxMana() {
        return MAX_MANA;
    }

    public List<Card> getGraveyardOne() {
        return graveyardOne;
    }

    public void setGraveyardOne(List<Card> graveyardOne) {
        this.graveyardOne = graveyardOne;
    }

    public List<Card> getGraveyardTwo() {
        return graveyardTwo;
    }

    public void setGraveyardTwo(List<Card> graveyardTwo) {
        this.graveyardTwo = graveyardTwo;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }
}
