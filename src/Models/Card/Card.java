package Models.Card;

public class Card {
    private static int cardCount;
    private String name;
    private int id;

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

