package models;

import models.Card.Card;
import models.Item.Item;

import java.util.ArrayList;
import java.util.List;

public class Collection {
    private List<Deck> decks = new ArrayList<>();
    private Deck mainDeck;
    private List<Card> cards = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    public void addCard(Card card){
        this.cards.add(card);
    }

    public void addItem(Item item){
        this.items.add(item);
    }

    public void removeCard(Card card){
        this.cards.remove(card);
        for(int i = 0; i < decks.size(); i++) {
            decks.get(i).getCards().remove(i);
            if(((Card)decks.get(i).getHero()).getId() == card.getId())
                decks.get(i).setHero(null);
        }
    }

    public void removeItem(Item item){
        this.items.remove(item);
        for(int i = 0; i < decks.size(); i++) {
            if(decks.get(i).getItem().getId() == item.getId())
                decks.get(i).setItem(null);
        }
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public void setMainDeck(Deck mainDeck) {
        this.mainDeck = mainDeck;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}