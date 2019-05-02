package models.Item;

import models.Player;
import models.card.Card;
import models.card.Effect;

import java.util.ArrayList;
import java.util.List;

public class Item extends Card {
    private static List<Item> items = new ArrayList<>();
    protected Effect effect;
    private String desc;

    public Item(String name, int price, Effect effect) {
        super(name, price, 0);
        this.effect = effect;
    }

    public Item() {

    }

    public void makeCopyAndAddToCollection(Player player) {
//        Item item = new Item(name, price);
//        item.setCollectionID(player.getCardCurrentID());
//        player.setCardCurrentID(player.getCardCurrentID() + 1);
//        player.getCollection().addItem(item);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void castItem() {

    }
}
