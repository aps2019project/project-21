package models.Item;

public class Item {
    private static int itemCount = 0;
    private String name;
    private int id;
    private int price;

    {
        id = itemCount;
        itemCount++;
    }

    public Item() {

    }

    public Item(String name) {
        this.name = name;
    }
}