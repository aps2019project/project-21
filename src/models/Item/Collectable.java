package models.Item;

import models.Player;
import models.card.Effect;
import models.card.TargetType;
import models.match.Cell;
import models.match.Match;

public class Collectable extends Item {
    public Collectable(String name, TargetType targetType, Effect effect, String desc) {
        super(name, 0, targetType, effect, desc);
    }

    public void castItem(Match match, Player player, Cell target) {

    }
}
