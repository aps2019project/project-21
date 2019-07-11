package models;

import controller.menus.CollectionMenu;
import json.CardMaker;
import models.Item.Usable;
import models.card.Card;
import models.card.Hero;
import models.card.Minion;
import models.card.Spell;
import view.View;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Collection implements Serializable {
    private static final long serialVersionUID = 6529685098267757045L;

    private List<Deck> decks = new ArrayList<>();
    private Deck mainDeck;

    private List<Hero> heroes = new ArrayList<>();
    private List<Minion> minions = new ArrayList<>();
    private List<Spell> spells = new ArrayList<>();
    private List<Usable> usables = new ArrayList<>();

    void addCard(Card card) {
        if (card instanceof Hero) {
            heroes.add((Hero) card);
        } else if (card instanceof Spell) {
            spells.add((Spell) card);
        } else if (card instanceof Minion)
            minions.add((Minion) card);
        else if (card instanceof Usable)
            usables.add((Usable) card);
        card.setCollectionID();
    }

    void removeCard(Card card) {
        if (card instanceof Minion)
            this.minions.remove(card);
        else if (card instanceof Spell)
            this.spells.remove(card);
        else if (card instanceof Hero)
            this.heroes.remove(card);
        else if (card instanceof Usable)
            this.usables.remove(card);
        for (Deck deck : decks)
            deck.remove(card);
    }

    public List<Deck> getDecks() {
        return decks;
    }

    void addDeck(Deck deck) {
        if (deck == null || hasThis(deck.getName()))
            return;
        decks.add(deck);
        if (deck.hasHero())
            heroes.add(deck.getHero());
        if (deck.hasUsable())
            usables.add(deck.getUsable());
        if (deck.getCards() != null)
            for (Card card : deck.getCards()) {
                if (card == null)
                    continue;
                if (card.getClass().equals(Minion.class))
                    minions.add((Minion) card);
                else if (card.getClass().equals(Spell.class))
                    spells.add((Spell) card);
            }
    }

    public void deleteDeck(String deckName) {
        if (!hasThis(deckName))
            return;
        List<Deck> copy = new ArrayList<>(decks);
        for (Deck deck : copy)
            if (deck.getName().equals(deckName))
                decks.remove(deck);
        if (mainDeck.getName().equals(deckName))
            mainDeck = null;
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

    boolean hasLessThanThreeItems() {
        return this.usables.size() < 3;
    }

    boolean hasThisCard(Card card) {
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

    public Deck getDeck(String deckName) {
        for (Deck deck : decks)
            if (deck.getName().equals(deckName))
                return deck;
        return null;
    }

    public boolean hasThis(String deckName) {
        return getDeck(deckName) != null;
    }

    void createDeck(String deckName) {
        if (hasThis(deckName))
            return;
        Deck deck = new Deck(deckName);
        addDeck(deck);
    }

    public boolean hasMainDeck() {
        return mainDeck != null;
    }

    boolean hasValidMainDeck() {
        return hasMainDeck() && mainDeck.isValid();
    }

    public boolean isThisHeroInADeck(Hero hero) {
        if (hero == null)
            return false;
        for (Deck deck : decks) {
            if (deck == null)
                continue;
            if (deck.hasHero())
                if (deck.getHero().getCollectionID() == hero.getCollectionID())
                    return true;
        }
        return false;
    }

    void autoChooseDeck() {
        for (Deck deck : decks)
            if (deck != null && deck.isValid()) {
                mainDeck = deck;
                return;
            }
    }

    private List<Card> getAllCards() {
        List<Card> cards = new ArrayList<>();
        cards.addAll(heroes);
        cards.addAll(minions);
        cards.addAll(usables);
        cards.addAll(spells);
        return cards;
    }

    private boolean isFree(Card card) {
        if (card instanceof Hero)
            for (Deck deck : decks)
                if (deck.hasThisCard(card))
                    return false;
        return true;
    }

    private Card getFreeCardByName(String name) {
        for (Card card : getAllCards())
            if (card.getName().equals(name) && isFree(card))
                return card;
        return null;
    }

    void importDeck(String filename) {
        Deck deck = null;
        try {
            deck = CardMaker.deckReader(filename);
        } catch (IOException e) {
            View.printThrowable(e);
        }
        if (deck == null) {
            View.popup("NO DECK SAVED AS " + filename);
            return;
        }
        if (hasThis(deck.getName())) {
            View.popup("YOU ALREADY HAVE A DECK WITH THIS NAME!");
            return;
        }
        Deck newDeck = new Deck(deck.getName());
        addDeck(newDeck);
        for (Card card : deck.getAllCards()) {
            Card cardInCollection = getFreeCardByName(card.getName());
            if (cardInCollection != null)
                CollectionMenu.getInstance().addCardToDeck(cardInCollection.getCollectionID(), newDeck.getName());
        }
    }

    void exportDeck(String deckName) {
        Deck deck = getDeck(deckName);
        if (deck == null)
            return;
        String path = CardMaker.saveToFile(deck);
        View.popup("Deck " + deckName + " saved in: " + path);
    }
}