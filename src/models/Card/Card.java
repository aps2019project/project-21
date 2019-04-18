package models.Card;

import models.Player;

public class Card {
    private static int cardCount = 0;
    private String name;
    private int id;
    private int price;
    private int manaCost; //for hero = -1
    private int XCoordinate;
    private int YCoordinate;
    private String cardIDInGame;
    private int idInCollection;

    public Card() {

    }

    public Card(String name, int id, int price, int manaCost) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.manaCost = manaCost;
    }

    public void makeCopyAndAddToCollection(Player player){
        Card card = new Card(this.getName(), this.getId(), this.getPrice(), this.getManaCost());
        card.setIdInCollection(player.getCardCurrentID());
        player.setCardCurrentID(player.getCardCurrentID()+1);
        player.getCollection().addCard(card);
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static int getCardCount() {
        return cardCount;
    }

    public static void setCardCount(int cardCount) {
        Card.cardCount = cardCount;
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

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public int getXCoordinate() {
        return XCoordinate;
    }

    public void setXCoordinate(int XCoordinate) {
        this.XCoordinate = XCoordinate;
    }

    public int getYCoordinate() {
        return YCoordinate;
    }

    public void setYCoordinate(int YCoordinate) {
        this.YCoordinate = YCoordinate;
    }

    public String getCardIDInGame() {
        return cardIDInGame;
    }

    public void setCardIDInGame(String cardIDInGame) {
        this.cardIDInGame = cardIDInGame;
    }

    public int getIdInCollection() {
        return idInCollection;
    }

    public void setIdInCollection(int idInCollection) {
        this.idInCollection = idInCollection;
    }
}
