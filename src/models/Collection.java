package models;

import models.Item.Usable;
import models.card.Card;
import models.card.Hero;
import models.card.Minion;
import models.card.Spell;

import java.util.ArrayList;
import java.util.List;

public class Collection {
    private List<Deck> decks = new ArrayList<>();
    private Deck mainDeck;

    private List<Hero> heroes = new ArrayList<>();
    private List<Minion> minions = new ArrayList<>();
    private List<Spell> spells = new ArrayList<>();
    private List<Usable> usables = new ArrayList<>();

    public void addCard(Card card) {
        if (card.getClass().equals(Hero.class)) {
            heroes.add((Hero) card);
        } else if (card.getClass().equals(Spell.class)) {
            spells.add((Spell) card);
        } else if (card.getClass().equals(Minion.class))
            minions.add((Minion) card);
        else if (card.getClass().equals(Usable.class))
            usables.add((Usable) card);
        card.setCollectionID();
    }

    public void removeCard(Card card) {
        if (card.getClass().equals(Minion.class))
            this.minions.remove(card);
        else if (card.getClass().equals(Spell.class))
            this.spells.remove((Spell) card);
        else if (card.getClass().equals(Hero.class))
            this.heroes.remove(card);
        else if (card.getClass().equals(Usable.class))
            this.usables.remove(card);
        for (Deck deck : decks)
            deck.remove(card);
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public void addDeck(Deck deck) {
        boolean found = true;
        for (Deck deck1 : this.decks) {
            if (deck1.getName().equals(deck.getName())) {
                found = false;
                System.out.println("Deck name is not available");
            }
        }
        if (found) {
            decks.add(deck);
        }
    }

    public void deleteDeck(Deck deck) {
        boolean found = true;
        for (Deck deck1 : this.decks) {
            if (deck1.getName().equals(deck.getName())) {
                this.decks.remove(deck1);
                found = false;
            }
        }
        if (found) {
            System.out.println("Deck isn't exist");
        }
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public void setMainDeck(Deck mainDeck) {
        this.mainDeck = mainDeck;
    }

    public List<Card> getCards() {
        List<Card> cards = new ArrayList<>();
        cards.addAll(heroes);
        cards.addAll(minions);
        cards.addAll(spells);
        cards.addAll(usables);
        return cards;
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public List<Minion> getMinions() {
        return minions;
    }

    public List<Usable> getUsables() {
        return usables;
    }

    public Deck searchDeck(String name) {
        // error mode should handled
        return null;
    }

    public boolean hasLessThanThreeItems() {
        return this.usables.size() < 3;
    }

    public boolean hasThisCard(Card card) {
        for (Card card1 : getCards())
            if (card1.getId() == card.getId())
                return true;
        return false;
    }

    public Card getCardByCollectionID(int id) {
        for (Card card : getCards())
            if (card.getCollectionID() == id)
                return card;
        return null;
    }
}