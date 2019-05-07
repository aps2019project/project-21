package models.Item;

import models.Player;
import models.card.ApplyType;
import models.card.Effect;
import models.card.TargetType;
import models.match.Cell;
import models.match.Match;

import java.util.ArrayList;
import java.util.List;

public class Usable extends Item {
    private static List<Usable> usables = new ArrayList<>();

    public Usable(String name, int price, TargetType targetType,
                  Effect effect, ApplyType applyType, String desc) {
        super(name, price, targetType, effect, applyType, desc);
    }

    public Usable(String name, int price, TargetType targetType,
                  List<Effect> effects, ApplyType applyType, String desc) {
        super(name, price, targetType, effects, applyType, desc);
    }

    public void castItem(Match match, Player player, Cell cell) {

    }

    public static List<Usable> getUsables() {
        return usables;
    }

    public static void addUsable(Usable usable) {
        if (usable == null)
            return;
        usables.add(usable);
    }

    public static void addUsable(List<Usable> usables) {
        if (usables == null)
            return;
        for (Usable usable : usables)
            addUsable(usable);
    }

    public static Usable getUsableByID(int id) {
        for (Usable usable : usables)
            if (usable.id == id)
                return usable;
        return null;
    }

    public void reset() {
        super.reset();
    }
}
