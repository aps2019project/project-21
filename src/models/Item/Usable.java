package models.Item;

import models.card.Effect;

public class Usable extends Item {
    public Usable(String name, int price, Effect effect ) {
        super(name, price, effect);
    }

    public void castUsable() {

    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }
}
