package models.Item;

import models.card.Effect;

public class Collectable extends Item {
    public Collectable(String name, Effect effect, String desc) {
        super(name, 0, effect, desc);
    }

    public void castItem() {

    }
}
