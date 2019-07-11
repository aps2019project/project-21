package controller.menus;

import models.Item.Usable;
import models.Player;
import models.card.Card;
import network.Client;
import network.request.Request;

import java.util.List;

public class ShopMenu extends Menu {
    private static final long serialVersionUID = 6529685098267757009L;

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
        Client.write(Request.makeBuyRequest(cardName));
        return null;
    }

    public static String sell(int collectionID) {
        Card card = Player.getCurrentPlayer().getCollection()
                .getCardByCollectionID(collectionID);
        if (card == null) {
            return "You don't have this card";
        }
        Client.write(Request.makeSellRequest(Integer.toString(collectionID)));
        return null;
    }
}
