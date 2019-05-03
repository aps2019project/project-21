package models.Item;

import models.Player;
import models.card.ApplyType;
import models.card.Effect;
import models.card.TargetType;
import models.match.Cell;
import models.match.Match;

import java.util.List;

public class Usable extends Item {
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
}
