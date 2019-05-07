package models.Item;

import json.CardMaker;
import models.Player;
import models.card.ApplyType;
import models.card.Effect;
import models.card.TargetType;
import models.match.Cell;
import models.match.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Collectable extends Item {
    private static List<Collectable> collectables = new ArrayList<>();

    public Collectable(String name, TargetType targetType,
                       Effect effect, ApplyType applyType, String desc) {
        super(name, 0, targetType, effect, applyType, desc);
    }

    public Collectable(String name, TargetType targetType,
                       List<Effect> effects, ApplyType applyType, String desc) {
        super(name, 0, targetType, effects, applyType, desc);
    }

    public void castItem(Match match, Player player, Cell target) {

    }

    public static List<Collectable> getCollectables() {
        return collectables;
    }

    public static void addCollectable(Collectable collectable) {
        collectables.add(collectable);
    }

    public static void addCollectable(List<Collectable> collectables) {
        if (collectables == null)
            return;
        for (Collectable collectable : collectables)
            addCollectable(collectable);
    }

    public static Collectable getCollectableByID(int id) {
        for (Collectable collectable : collectables)
            if (collectable.id == id)
                return collectable;
        return null;
    }

    public static Collectable getRandomCollectable() {
        Random random = new Random();
        return CardMaker.deepCopy(collectables.get(random.nextInt(collectables.size())),
                Collectable.class);
    }
}
