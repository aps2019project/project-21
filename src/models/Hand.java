package models;

import models.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private static final int HAND_CAPACITY = 5;

    public static int getHandCapacity() {
        return HAND_CAPACITY;
    }

    private List<Card> cards = new ArrayList<>();

    public static Hand extractHand(Deck deck){

        //  TODO
        return null;
    }


    public void remove(Card Card) {
        cards.remove(Card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
