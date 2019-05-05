package models;

import models.Item.Collectable;
import models.Item.Usable;
import models.card.Card;
import models.card.Hero;
import models.Item.Item;
import models.card.Minion;
import models.card.Spell;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private static final int MAX_NUM_CARDS = 20; // minions and spells
    private String name;
    private List<Card> cards = new ArrayList<>();  // minions and spells
    private Usable usable;
    private Hero hero;
    private List<Collectable> collectables = new ArrayList<>(); // achieved during the match

    public Deck(String name, List<Card> cards, Usable usable, Hero hero) {
        this.name = name;
        this.cards = cards;
        this.usable = usable;
        this.hero = hero;
    }

    public Deck(String name) {
        this.name = name;
    }

    public static Deck copyDeck(Deck deck) {
        return new Deck(deck.name, deck.cards, deck.usable, deck.hero);
    }

    public void shuffle() {

    }

    public void showLastCard() {
        Card last = cards.get(0);
        //  TODO
    }

    public Card pop() {
        return cards.remove(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //  only for minions and spells
    public List<Card> getCards() {
        return cards;
    }

    public Item getUsable() {
        return usable;
    }

    public void setUsable(Usable usable) {
        this.usable = usable;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void addCard(Card card) {
        if (card == null)
            return;
        if (card.getClass().equals(Hero.class))
            if (hero == null)
                hero = (Hero) card;
        if (card.getClass().equals(Usable.class))
            if (usable == null)
                usable = (Usable) card;
        if (card.getClass().equals(Spell.class) || card.getClass().equals(Minion.class))
            if (!isCardsFull())
                cards.add(card);
    }

    public void addCollectable(Collectable collectable) {
        if (collectable == null)
            return;
        this.collectables.add(collectable);
    }

    public void deleteObject(Object object) {

    }

    public boolean isValid() {
        return isCardsFull() && hasHero();
    }

    public void remove(Card card) {
        if (card == null)
            return;
        cards.remove(card);
        if (hasHero())
            if (hero.getId() == card.getId())
                hero = null;
        if (hasUsable())
            if (usable.getId() == card.getId())
                usable = null;
    }

    public List<Card> getAllCards() { //  except collectables
        List<Card> cards = new ArrayList<>(this.cards);
        if (hero != null)
            cards.add(hero);
        if (usable != null)
            cards.add(usable);
        return cards;
    }

    public Card getCardByCollectionID(int collectionID) {
        for (Card card : getAllCards())
            if (card.getCollectionID() == collectionID)
                return card;
        return null;
    }

    public boolean hasThisCard(Card card) {
        return getCardByCollectionID(card.getCollectionID()) != null;
    }

    public boolean hasHero() {
        return hero != null;
    }

    public boolean hasUsable() {
        return usable != null;
    }

    public boolean isCardsFull() {
        return cards.size() >= MAX_NUM_CARDS;
    }

    public boolean isFull() {
        return isCardsFull()
                && hasHero()
                && hasUsable();
    }

}
