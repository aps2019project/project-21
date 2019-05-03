package models.Item;

import models.card.Effect;

public class Usable extends Item {
    public Usable(String name, int price, Effect effect, String desc) {
        super(name, price, effect, desc);
    }

    public void castItem() {

    }
}
