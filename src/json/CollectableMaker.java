package json;

import models.Item.Collectable;
import models.card.ActivationType;
import models.card.ApplyType;
import models.card.Effect;
import models.card.TargetType;
import models.card.buffs.*;
import models.card.effects.IncreaseAP;
import models.card.effects.IncreaseHP;
import models.card.target_enums.*;

import java.util.ArrayList;
import java.util.List;

import static json.CardMaker.saveToFile;

public class CollectableMaker {
    public static void main() {
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
        effects = new ArrayList<>();
        effects.add(new PowerAP(Integer.MAX_VALUE, 3));
        effects.add(new PowerHP(Integer.MAX_VALUE, 3));
        targetType = new TargetType(RandomOrNot.RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.NONE, HeroOrMinion.BOTH, OppOrAlly.ALLY, ChooseType.NONE);
        collectable = new Collectable("Eksir", targetType, effects,
                ApplyType.ON_ATTACKER, "3 powerHP and 3 powerAP for random minion.");
        saveToFile(collectable);

        // ---------- 4 ----------
        //later Majone Mana

        // ---------- 5 ----------
        effects = new ArrayList<>();
        for(int i = 0; i < 10; i++)
            effects.add(new Holy(2));
        targetType = new TargetType(RandomOrNot.RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.NONE, HeroOrMinion.MINION, OppOrAlly.ALLY, ChooseType.NONE);
        collectable = new Collectable("Majone Roein Tani", targetType, effects,
                ApplyType.ON_ATTACKER, "10 holy buff for a random minion.");
        saveToFile(collectable);

        // ---------- 6 ----------
        effects = new ArrayList<>();
        effects.add(new WeaknessHP(Integer.MAX_VALUE, 8));
        effects.get(0).setActivationType(ActivationType.ON_DEATH);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SQUARE_3_3, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.NONE);
        collectable = new Collectable("Nefrine Marg", targetType, effects,
                ApplyType.ON_ATTACKER, "Attack a neighbor opp 8 unit, on death.");
        saveToFile(collectable);

        // ---------- 7 ----------
        effects = new ArrayList<>();
        effects.add(new WeaknessHP(Integer.MAX_VALUE,2));
        targetType = new TargetType(RandomOrNot.RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.NONE, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.NONE);
        collectable = new Collectable("Random Damage", targetType, effects,
                ApplyType.ON_ATTACKER, "Damage an attacker 2 units.");
        saveToFile(collectable);

        // ---------- 8 ----------
        effects = new ArrayList<>();
        effects.add(new PowerAP(Integer.MAX_VALUE, 6));
        targetType = new TargetType(RandomOrNot.RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.NONE, HeroOrMinion.BOTH, OppOrAlly.ALLY, ChooseType.NONE);
        collectable = new Collectable("Blades of agility", targetType, effects,
                ApplyType.ON_ATTACKER, "PowerAp 6 units for a random ally.");
        saveToFile(collectable);

        // ---------- 9 ----------
        effects = new ArrayList<>();
        effects.add(new PowerAP(Integer.MAX_VALUE,5));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.MELEE,
                CellType.NONE, HeroOrMinion.BOTH, OppOrAlly.ALLY, ChooseType.NONE);
        collectable = new Collectable("Shamshire Chini", targetType, effects,
                ApplyType.ON_ATTACKER, "PowerAP 5 units for melee ally.");
        saveToFile(collectable);
    }
}
