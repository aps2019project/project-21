package models.Item;

import models.card.ApplyType;
import models.card.Card;
import models.card.Effect;
import models.card.TargetType;

import java.util.ArrayList;
import java.util.List;

public class Item extends Card {
    private static final long serialVersionUID = 6529685098267757036L;

    private ApplyType applyType;
    protected List<Effect> effects = new ArrayList<>();
    protected TargetType targetType;
    private String desc;

    public Item(String name, int price, TargetType targetType, List<Effect> effects,
                ApplyType applyType, String desc) {
        super(name, price, 0);
        this.targetType = targetType;
        this.effects.addAll(effects);
        this.desc = desc;
    }

    public Item(String name, int price, TargetType targetType, Effect effect,
                ApplyType applyType, String desc) {
        super(name, price, 0);
        this.targetType = targetType;
        this.effects.add(effect);
        this.desc = desc;
    }

    public Item() {

    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        items.addAll(Usable.getUsables());
        items.addAll(Collectable.getCollectables());
        return items;
    }

    public ApplyType getApplyType() {
        return applyType;
    }

    public void reset(){
        super.reset();
    }
}
