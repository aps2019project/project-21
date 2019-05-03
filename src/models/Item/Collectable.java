package models.Item;

import models.Player;
import models.card.ApplyType;
import models.card.Effect;
import models.card.TargetType;
import models.match.Cell;
import models.match.Match;

import java.util.List;

public class Collectable extends Item {
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
}
