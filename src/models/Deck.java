package models;

import models.Item.Collectable;
import models.Item.Usable;
import models.card.Card;
import models.card.Hero;
import models.card.Minion;
import models.card.Spell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck implements Serializable {
    private static final long serialVersionUID = 6529685098267757046L;

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

    Deck(String name) {
        this.name = name;
    }

    public void shuffle() {
        Collections.shuffle(this.cards);
        if (!Card.getCustomCards().isEmpty())
            if (hasThisCard(Card.getCustomCards().get(0).getName())) {
                for (int i = 0; i < cards.size(); i++) {
                    if (cards.get(i).getName().equals(Card.getCustomCards().get(0).getName())) {
                        Card tmp = cards.get(7);
                        cards.set(7, cards.get(i));
                        cards.set(i, tmp);
                        return;
                    }
                }
            }
    }

    Card pop() {
        if (cards.isEmpty())
            return null;
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

    public Usable getUsable() {
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

    public List<Card> getAllCards() {  //  except collectables
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

    private Card getCardByName(String name) {
        for (Card card : getAllCards())
            if (card.getName().equals(name))
                return card;
        return null;
    }

    public boolean hasThisCard(String name) {
        return getCardByName(name) != null;
    }

    public boolean hasHero() {
        return hero != null;
    }

    public boolean hasUsable() {
        return usable != null;
    }

    private boolean isCardsFull() {
        return cards.size() >= MAX_NUM_CARDS;
    }

    public boolean isFull() {
        return isCardsFull()
                && hasHero()
                && hasUsable();
    }

    public void reset() {
        for (Card card : getAllCards())
            card.reset();
    }

    public void removeRandom() {
        cards.remove(cards.size() - 1);
    }

}
