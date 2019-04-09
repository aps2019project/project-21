package models.Card;

public class Card {
    private static int cardCount = 0;
    private String name;
    private int id;
    private int price;
    private int manaCost; //for hero = -1
    private int XCoordinate;
    private int YCoordinate;

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
}
