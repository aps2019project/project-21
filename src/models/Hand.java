package models;

import models.card.Attacker;
import models.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private static final int HAND_CAPACITY = 5;

    private List<Card> cards = new ArrayList<>();

    Hand() {

    }

    public static Hand extractHand(Deck deck) {
        Hand hand = new Hand();
        for (int i = 0; i < HAND_CAPACITY; i++)
            hand.pushToHandFromDeck(deck);
        return hand;
    }

    public void pushToHandFromDeck(Deck deck) {
        if (this.cards.size() >= 5)
            return;
        this.cards.add(deck.pop());
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

    public boolean hasCard(String cardName) {
        for (Card card : cards)
            if (card.getName().equals(cardName))
                return true;
        return false;
    }

    public Card getCard(String cardName) {
        for (Card card : cards)
            if (card.getName().equals(cardName))
                return card;
        return null;
    }

    public Attacker pop(String name) {
        for (Card card : cards)
            if (card instanceof Attacker)
                if (card.getName().equals(name)) {
                    cards.remove(card);
                    return (Attacker) card;
                }
        return null;
    }

    private List<Attacker> getAttackers() {
        List<Attacker> attackers = new ArrayList<>();
        for (Card card : cards)
            if (card instanceof Attacker)
                attackers.add((Attacker) card);
        return attackers;
    }

    public Attacker getAttacker(String name) {
        for (Attacker attacker : getAttackers())
            if (attacker.getName().equals(name))
                return attacker;
        return null;
    }

}
