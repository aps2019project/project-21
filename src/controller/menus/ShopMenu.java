package controller.menus;

import models.Item.Usable;
import models.Player;
import models.card.Card;

import java.util.List;

public class ShopMenu extends Menu {
    private static ShopMenu instance = new ShopMenu();

    public static Menu getInstance() {
        return instance;
    }

    private ShopMenu() {
    }


    public static Card search(String cardName) {
        return Card.getCardByName(cardName);
    }

    public static Card searchCollection(String cardName) {
        List<Card> cards = Card.getAllCardByName(cardName, Player.getCurrentPlayer().getCollection().getCards());
        if (cards.isEmpty())
            return null;
        else
            return cards.get(0);
    }

    public static String buy(String cardName) {
        Card card = Card.getCardByName(cardName);
        if (Player.getCurrentPlayer().getDrake() < card.getPrice()) {
            return "Not enough money";
        }
        if (card.getClass().equals(Usable.class) && !Player.getCurrentPlayer().hasLessThanThreeItems()) {
            return "Has three items";
        }
        Player.getCurrentPlayer().buy(card);
        return "Buy successful";
    }

    public static String sell(int collectionID) {
        Card card = Player.getCurrentPlayer().getCollection()
                .getCardByCollectionID(collectionID);
        if (card == null) {
            return "You don't have this card";
        }
        Player.getCurrentPlayer().sell(card);
        return "Sell successful";
    }
}
