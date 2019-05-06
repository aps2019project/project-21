package json;

import models.Item.Usable;
import models.card.ActivationType;
import models.card.ApplyType;
import models.card.Effect;
import models.card.TargetType;
import models.card.buffs.*;
import models.card.effects.EffectName;
import models.card.effects.GiveEffect;
import models.card.target_enums.*;

import java.util.ArrayList;
import java.util.List;

import static json.CardMaker.saveToFile;

public class UsableMaker {
    public static void main() {
        // ---------- 1 ----------
        List<Effect> effects = new ArrayList<>();
        effects.add(new IncreaseMana(3, 1));
        Usable usable = new Usable("Taje Danaee", 300, null, effects,
                ApplyType.ON_PLAYER_INFO, "Increases Mana in first three turns");
        saveToFile(usable);


        // ---------- 2 ----------
        effects = new ArrayList<>();
        effects.add(new GiveEffect(12, EffectName.HOLY));
        TargetType targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.NONE, HeroOrMinion.HERO, OppOrAlly.ALLY, ChooseType.NONE);
        usable = new Usable("Namoose Separ", 4000, targetType, effects,
                ApplyType.ON_ATTACKER, "Gives ally hero 12 holu buffs.");
        saveToFile(usable);


        // ---------- 3 ----------
        effects = new ArrayList<>();
        effects.add(new Disarm(1));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.RANGED_OR_HYBRID,
                CellType.NONE, HeroOrMinion.HERO, OppOrAlly.ALLY, ChooseType.SELECTED_ALLY);
        usable = new Usable("Kamane Damool", 30000, targetType, effects,
                ApplyType.ON_ATTACKER, "Ranged and hybrid ally hero will disarm opp on attack");
        saveToFile(usable);


        // ---------- 4 ----------
        effects = new ArrayList<>();
        effects.add(new WeaknessAP(Integer.MAX_VALUE, 2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.RANGED_OR_HYBRID,
                CellType.NONE, HeroOrMinion.HERO, OppOrAlly.OPP, ChooseType.SELECTED_OPP);
        usable = new Usable("Pare Simorgh", 3500, targetType, effects,
                ApplyType.ON_ATTACKER, "Attack Hybrid or Ranged Opp hero 2 units.");
        saveToFile(usable);

        // ---------- 5 ----------
        effects = new ArrayList<>();
        effects.add(new WeaknessAP(1, 2));
        effects.get(0).setActivationType(ActivationType.ON_ATTACK);
        targetType = new TargetType(RandomOrNot.RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.NONE, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.NONE);
        usable = new Usable("Terror Hood", 5000, targetType, effects,
                ApplyType.ON_ATTACKER, "Decrease 2 units of opp's (randomly chosen) AP for 1 turn, on attack.");
        saveToFile(usable);

        // ---------- 6 ----------
        //later King Wisdom

        // ---------- 7 ----------
        effects = new ArrayList<>();
        effects.add(new WeaknessHP(Integer.MAX_VALUE, 1));
        effects.get(0).setActivationType(ActivationType.ON_SPAWN);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.NONE, HeroOrMinion.HERO, OppOrAlly.OPP, ChooseType.NONE);
        usable = new Usable("Assassination Dagger", 15000, targetType, effects,
                ApplyType.ON_ATTACKER, "Attack opp's hero when put each card on board.");
        saveToFile(usable);

        // ---------- 8 ----------
        effects = new ArrayList<>();
        effects.add(new Poison(1));
        effects.get(0).setActivationType(ActivationType.ON_ATTACK);
        targetType = new TargetType(RandomOrNot.RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.NONE, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.NONE);
        usable = new Usable("Poisonous Dagger", 7000, targetType, effects,
                ApplyType.ON_ATTACKER, "Poison random opp for 1 turn, while attack.");
        saveToFile(usable);

        // ---------- 9 ----------
        effects = new ArrayList<>();
        effects.add(new Disarm(1));
        effects.get(0).setActivationType(ActivationType.ON_ATTACK);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.SELECTED_OPP);
        usable = new Usable("Shock Hammer", 15000, targetType, effects,
                ApplyType.ON_ATTACKER, "Hero disarm opp for 1 turn, while attack.");
        saveToFile(usable);

        // ---------- 10 ----------
        effects = new ArrayList<>();
        effects.add(new PowerAP(Integer.MAX_VALUE, 1));
        effects.get(0).setActivationType(ActivationType.ON_DEATH);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.ALLY, ChooseType.SELECTED_ALLY);
        usable = new Usable("Soul Eater", 25000, targetType, effects,
                ApplyType.ON_ATTACKER, "Apply power buff 1 unit of ally, on death.");
        saveToFile(usable);

        // ---------- 11 ----------
        effects = new ArrayList<>();
        effects.add(new Holy(2));
        effects.get(0).setActivationType(ActivationType.ON_SPAWN);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.NONE, HeroOrMinion.MINION, OppOrAlly.ALLY, ChooseType.SELECTED_ALLY);
        usable = new Usable("Ghosle Tameid", 20000, targetType, effects,
                ApplyType.ON_ATTACKER, "Holy buff for 2 rounds when spawn.");
        saveToFile(usable);
    }
}
