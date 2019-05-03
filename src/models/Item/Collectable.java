package models.Item;

import models.card.Effect;
import models.card.TargetType;

public class Collectable extends Item {
    public Collectable(String name, TargetType targetType, Effect effect, String desc) {
        super(name, 0, targetType, effect, desc);
    }

    public void castItem() {

    }
}
