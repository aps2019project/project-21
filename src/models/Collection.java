package models;

import models.Item.Item;
import models.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Collection {
    private List<Deck> decks = new ArrayList<>();
    private Deck mainDeck;
    private List<Card> cards = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    public void addCard(Card Card){
        this.cards.add(Card);
    }

    public void addItem(Item item){
        this.items.add(item);
    }

    public void removeCard(Card Card){
        this.cards.remove(Card);
        for(int i = 0; i < decks.size(); i++) {
            decks.get(i).getCards().remove(i);
            if(((Card)decks.get(i).getHero()).getId() == Card.getId())
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

    public void addDeck(Deck deck){
        boolean found=true;
        for (Deck deck1 : this.decks){
            if (deck1.getName().equals(deck.getName())){
                found = false;
                System.out.println("Deck name is not available");
            }
        }
        if (found) {
            decks.add(deck);
        }
    }

    public void deleteDeck(Deck deck){
        boolean found = true;
        for (Deck deck1: this.decks){
            if (deck1.getName().equals(deck.getName())){
                this.decks.remove(deck1);
                found = false;
            }
        }
        if (found){
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

    public Deck searchDeck(String name){
        // error mode should handled
        return null;
    }
}