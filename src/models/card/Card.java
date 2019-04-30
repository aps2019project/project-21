package models.card;

import models.Player;
import models.match.Cell;

public class Card {
    private static int cardCount = 0;
    protected String name;
    protected int id;
    protected int collectionID;
    protected String cardIDInGame;
    protected int price;
    protected int manaCost; //for hero = -1
    protected Cell currentCell;

    public Card() {

    }

    public Card(String name, int price, int manaCost) {
        this.name = name;
        this.price = price;
        this.manaCost = manaCost;
    }

    {
        this.id = cardCount;
        cardCount++;
    }

    public void makeCopyAndAddToCollection(Player player) {
        Card Card = new Card(name, price, manaCost);
        Card.setCollectionID(player.getCardCurrentID());
        player.setCardCurrentID(player.getCardCurrentID() + 1);
        player.getCollection().addCard(Card);
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

    public void setCollectionID(int collectionID) {
        this.collectionID = collectionID;
    }

    public static Card getCardByID(String ID){
        //bulshet zadam
        return Card.getCardByID("10");
    }
}