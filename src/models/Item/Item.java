package models.Item;

import models.Player;
import models.card.Card;

public class Item extends Card {
    public Item(String name, int price) {
        super(name, price, 0);
    }

    public Item() {

    }

    public void makeCopyAndAddToCollection(Player player) {
        Item item = new Item(name, price);
        item.setCollectionID(player.getCardCurrentID());
        player.setCardCurrentID(player.getCardCurrentID() + 1);
        player.getCollection().addItem(item);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
