package models;

import models.card.Card;
import models.card.Hero;
import models.Item.Item;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private static final int MAX_SIZE = 20;
    private String name;
    private List<Card> cards = new ArrayList<>();
    private Item item;
    private Hero hero;

    public void shuffle() {

    }

    public void showLastCard(){
        //  TODO
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }
}
