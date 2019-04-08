package models.Card;

public class Card {
    private static int cardCount = 0;
    private String name;
    private int id;
    private int price;

    {
        id = cardCount;
        cardCount++;
    }

    Card() {

    }

    Card(String name) {
        this.name = name;
    }
}
