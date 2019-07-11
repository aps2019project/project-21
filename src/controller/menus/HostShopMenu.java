package controller.menus;

import models.Player;
import models.card.Card;

public class HostShopMenu extends Menu {
    private static final long serialVersionUID = 6529685098267757005L;

    public static void buy(Player player, String cardName) {
        Card card = Card.getCardByName(cardName);
        player.buy(card);
    }

    public static void sell(Player player, int collectionID) {
        Card card = player.getCollection()
                .getCardByCollectionID(collectionID);
        player.sell(card);
    }
}
