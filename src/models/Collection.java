package models;

import models.Item.Item;
import models.card.Card;
import models.card.Hero;
import models.card.Minion;
import models.card.Spell;

import java.util.ArrayList;
import java.util.List;

import static models.card.Card.cards;

public class Collection {
    private List<Deck> decks = new ArrayList<>();
    private Deck mainDeck;
    private List<Hero> hero = new ArrayList<>();
    private List<Minion> minions = new ArrayList<>();
    private List<Spell> spells = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    public void addCard(Card card) {
        if (card.getClass().equals(Hero.class)) {
            hero.add((Hero) card);
        } else if (card.getClass().equals(Spell.class)) {
            spells.add((Spell) card);
        } else {
            minions.add((Minion) card);
        }
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void removeCard(Card card) {
        if (cards.getClass().equals(Minion.class)) {
            this.minions.remove(card);
            for (int i = 0; i < decks.size(); i++) {
                Deck deck = decks.get(i);
                for (int j = 0; j < deck.getCards().size(); j++) {
                    if (deck.getCards().get(j).TwoCardAreSame(card))
                        deck.getCards().remove(j);
                }
            }
        } else if (cards.getClass().equals(Spell.class)) {
            this.spells.remove(card);
            for (int i = 0; i < decks.size(); i++) {
                Deck deck = decks.get(i);
                for (int j = 0; j < deck.getCards().size(); j++) {
                    if (deck.getCards().get(j).TwoCardAreSame(card))
                        deck.getCards().remove(j);
                }
            }
        } else {
            this.hero.remove(card);
            for (int i = 0; i < decks.size(); i++) {
                Deck deck = decks.get(i);
                if (deck.getHero().getId() == card.getId())
                    deck.setHero(null);
            }
        }
    }

    public void removeItem(Item item) {
        this.items.remove(item);
        for (int i = 0; i < decks.size(); i++) {
            if (decks.get(i).getItem().getId() == item.getId())
                decks.get(i).setItem(null);
        }
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
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

    public void setHero(List<Hero> hero) {
        this.hero = hero;
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public List<Hero> getHero() {
        return hero;
    }

    public List<Minion> getMinions() {
        return minions;
    }

    public void setSpells(List<Spell> spells) {
        this.spells = spells;
    }

    public void setMinions(List<Minion> minions) {
        this.minions = minions;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Deck searchDeck(String name) {
        // error mode should handled
        return null;
    }

    public List<Card> getCards() {
        return null;
    }
}