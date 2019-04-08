package Models.Item;

public class Item {
    private static int itemCount;
    private String name;
    private int id;

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
