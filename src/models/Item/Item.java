package models.Item;

import models.Player;
import models.card.Card;
import models.card.Effect;

public class Item extends Card {
    protected Effect effect;

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

    public static Item getItemByName(String name){
        //bayad por shavad
        return null;
    }

    public boolean twoItemAreSame(Item item){
        return true;
    }
}
