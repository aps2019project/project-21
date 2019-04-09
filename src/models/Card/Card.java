package models.Card;

public class Card {
    private static int cardCount = 0;
    private String name;
    private int id;
    private int price;
    private int manaCost; //for hero = -1
    private int XCoordinate;
    private int YCoordinate;
    private String cardIDInGame;

    {
        id = cardCount;
        cardCount++;
    }

    Card() {

    }

    Card(String name) {
        this.name = name;
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
}
