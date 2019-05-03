package models.Item;

import models.Player;
import models.card.Card;
import models.card.Effect;
import models.card.TargetType;

import java.util.ArrayList;
import java.util.List;

public class Item extends Card {
    private static List<Item> items = new ArrayList<>();
    protected List<Effect> effects = new ArrayList<>();
    protected TargetType targetType;
    private String desc;

    public Item(String name, int price, List<Effect> effects, String desc) {
        super(name, price, 0);
        this.effects.addAll(effects);
        this.desc = desc;
    }

    public Item(String name, int price, Effect effect, String desc) {
        super(name, price, 0);
        this.effects.add(effect);
        this.desc = desc;
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

    public static Item getItemByName(String name) {
        //bayad por shavad
        return null;
    }

    public boolean twoItemAreSame(Item item) {
        return true;
    }
}
