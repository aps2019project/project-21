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
}
