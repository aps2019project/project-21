package json;

import models.Item.Collectable;
import models.card.ApplyType;
import models.card.Effect;
import models.card.TargetType;
import models.card.effects.IncreaseAP;
import models.card.effects.IncreaseHP;
import models.card.target_enums.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static json.CardMaker.saveToFile;

public class CollectableMaker {
    public static void main(String[] args) throws IOException {
        // ---------- 1 ----------
        List<Effect> effects = new ArrayList<>();
        effects.add(new IncreaseHP(6));
        TargetType targetType = new TargetType(RandomOrNot.RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.NONE, HeroOrMinion.BOTH, OppOrAlly.ALLY, ChooseType.NONE);
        Collectable collectable = new Collectable("Nooshdaroo", targetType, effects,
                ApplyType.ON_ATTACKER, "Increase HP of a random ally 3 units.");
        saveToFile(collectable);


        // ---------- 2 ----------
        effects = new ArrayList<>();
        effects.add(new IncreaseAP(2));
        targetType = new TargetType(RandomOrNot.RANDOM, TargetAttackerRange.RANGED_OR_HYBRID,
                CellType.NONE, HeroOrMinion.BOTH, OppOrAlly.ALLY, ChooseType.NONE);
        collectable = new Collectable("Tire Doshakh", targetType, effects,
                ApplyType.ON_ATTACKER, "Increase AP of a random ranged_or_hybrid ally 2 units.");
        saveToFile(collectable);


        // ---------- 3 ----------


        // ---------- 4 ----------


        // ---------- 5 ----------


        // ---------- 6 ----------


        // ---------- 7 ----------


        // ---------- 8 ----------


        // ---------- 9 ----------

    }
}
