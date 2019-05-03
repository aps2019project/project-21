package models.Item;

import models.card.Effect;
import models.card.TargetType;

public class Usable extends Item {
    public Usable(String name, int price, TargetType targetType, Effect effect, String desc) {
        super(name, price, targetType, effect, desc);
    }

    public void castItem() {

    }
}
