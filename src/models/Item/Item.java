package models.Item;

import models.Player;

public class Item {
    private static int itemCount = 0;
    private String name;
    private int id;
    private int price;
    private int collectionID;

    public Item(String name, int id, int price) {
        this.name = name;
        this.id = id;
        this.price = price;
    }

    public Item() {

    }

    public Item(String name) {
        this.name = name;
    }

    public void makeCopyAndAddToCollection(Player player){
        Item item = new Item(this.name, this.id, this.price);
        item.setCollectionID(player.getCardCurrentID());
        player.setCardCurrentID(player.getCardCurrentID() + 1);
        player.getCollection().addItem(item);
    }

    public static int getItemCount() {
        return itemCount;
    }

    public static void setItemCount(int itemCount) {
        Item.itemCount = itemCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCollectionID() {
        return collectionID;
    }

    public void setCollectionID(int collectionID) {
        this.collectionID = collectionID;
    }

}
