package models.card;

import models.Item.Item;
import models.match.Cell;

import java.util.ArrayList;
import java.util.List;

public class Card {
    private static int cardCount = 100;
    protected String name;
    protected int id;
    protected int collectionID;
    protected String cardIDInGame;
    protected int price;
    protected int manaCost;
    protected Cell currentCell;

    public Card() {

    }

    public Card(String name, int price, int manaCost) {
        this.name = name.replaceAll("\\s+", "_");
        this.price = price;
        this.manaCost = manaCost;
    }

    {
        this.id = cardCount;
        cardCount++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public int getManaCost() {
        return manaCost;
    }

    public String getCardIDInGame() {
        return cardIDInGame;
    }

    public void setCardIDInGame(String cardIDInGame) {
        this.cardIDInGame = cardIDInGame;
    }

    public void setCollectionID() {
        this.collectionID = cardCount;
        cardCount++;
    }

    public boolean TwoCardAreSame(Card card) {
        return true;
    }

    public static List<Card> getCards() {
        List<Card> cards = new ArrayList<>();
        cards.addAll(Attacker.getAttackers());
        cards.addAll(Spell.getSpells());
        cards.addAll(Item.getItems());
        return cards;
    }

    public static Card getCardByID(String id) {
        if (!id.matches("\\d+"))
            return null;
        return getCardByID(Integer.parseInt(id));
    }

    public static Card getCardByID(int id) {
        return getCardByID(id, getCards());
    }

    public static Card getCardByName(String name) {
        return getCardByName(name, getCards());
    }

    public static Card getCardByName(String name, List<Card> cards) {
        for (Card card : cards)
            if (card.name.equals(name))
                return card;
        return null;
    }

    public static List<Card> getAllCardByName(String name, List<Card> cards) {
        List<Card> cardList = new ArrayList<>();
        for (Card card : cards)
            if (card.name.equals(name))
                cardList.add(card);
        return cardList;
    }

    public static Card getCardByID(int id, List<Card> cards) {
        for (Card card : cards)
            if (card.id == id)
                return card;
        return null;
    }

    public int getCollectionID() {
        return collectionID;
    }

}