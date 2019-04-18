package models;

import models.Card.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private static final int HAND_CAPACITY = 5;
    private List<Card> cards = new ArrayList<>();
    private Card next;

    public void remove(Card card){
        cards.remove(card);
    }

    public static int getHandCapacity() {
        return HAND_CAPACITY;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Card getNext() {
        return next;
    }

    public void setNext(Card next) {
        this.next = next;
    }
}
