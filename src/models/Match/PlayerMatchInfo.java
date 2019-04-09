package models.Match;

import models.Card.Card;

import java.util.ArrayList;
import java.util.List;

public class PlayerMatchInfo {
    private static final int MAX_MANA = 9;
    private List<Card> graveyardOne = new ArrayList<>();
    private List<Card> garveyardTwo = new ArrayList<>();
    int mana = 3;

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

    public List<Card> getGarveyardTwo() {
        return garveyardTwo;
    }

    public void setGarveyardTwo(List<Card> garveyardTwo) {
        this.garveyardTwo = garveyardTwo;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }
}
