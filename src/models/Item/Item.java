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
}
