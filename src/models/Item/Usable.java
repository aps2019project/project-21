package models.Item;

import models.card.Effect;

public class Usable extends Item {
    private Effect effect;

    public Usable(String name, int price) {
        super(name, price);
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
