package json;

import models.card.ApplyType;
import models.card.Effect;
import models.card.Spell;
import models.card.TargetType;
import models.card.buffs.*;
import models.card.effects.IncreaseAP;
import models.card.effects.PositiveDispel;
import models.card.target_enums.*;

import java.util.ArrayList;
import java.util.List;

import static json.CardMaker.saveToFile;

public class SpellMaker {
    public static void main() {
        // ---------- 1 ----------
        List<Effect> effects = new ArrayList<>();
        //effects.add(new PowerHP(Integer.MAX_VALUE, 8, PowerMode.AP, ApplyType.ON_ALLY));
        //effects.add(new Disarm(Integer.MAX_VALUE, ApplyType.ON_OPP));
        //effects.add(new Effect(ApplyType.ON_BOTH, EffectType.POSITIVE_DISPEL));
        //effects.add(new DecreaseHP(8,ApplyType.ON_OPP_HERO));
        //effects.add(new Flame(2));
        //effects.add(new Poison(4, ApplyType.ON_OPP));
        //effects.add(new WeaknessHP(Integer.MAX_VALUE, Integer.MAX_VALUE, WeaknessMode.AP, ApplyType.ON_OPP));
        effects.add(new Disarm(Integer.MAX_VALUE));
        TargetType targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.SELECTED_OPP);
        Spell spell = new Spell("Total Disarm", 1000, 0, targetType, effects,
                ApplyType.ON_ATTACKER, "Disarms an opp til the end.");
        saveToFile(spell);


        // ---------- 2 ----------
        effects = new ArrayList<>();
        effects.add(new PositiveDispel());
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SQUARE_2_2, HeroOrMinion.BOTH, OppOrAlly.BOTH, ChooseType.SELECTED_CELL);
        spell = new Spell("Area Dispel", 1500, 2, targetType, effects,
                ApplyType.ON_ATTACKER, "Dispel A 2*2 Square.");
        saveToFile(spell);


        // ---------- 3 ----------
        effects = new ArrayList<>();
        effects.add(new IncreaseAP(2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.ALLY, ChooseType.SELECTED_ALLY);
        spell = new Spell("Empower", 250, 1, targetType, effects,
                ApplyType.ON_ATTACKER, "Increase AP 2 units.");
        saveToFile(spell);


        // ---------- 4 ----------
        effects = new ArrayList<>();
        effects.add(new WeaknessHP(Integer.MAX_VALUE, 4));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.SELECTED_OPP);
        spell = new Spell("Fireball", 400, 1, targetType, effects,
                ApplyType.ON_ATTACKER, "Attack an opp 4 units.");
        saveToFile(spell);


        // ---------- 5 ----------
        effects = new ArrayList<>();
        effects.add(new PowerAP(Integer.MAX_VALUE, 4));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.HERO, OppOrAlly.ALLY, ChooseType.HIMSELF);
        spell = new Spell("God Strength", 450, 2, targetType, effects,
                ApplyType.ON_ATTACKER, "Increase AP of Hero 4 units.");
        saveToFile(spell);


        // ---------- 6 ----------
        effects = new ArrayList<>();
        effects.add(new Flame(2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SQUARE_2_2, HeroOrMinion.BOTH, OppOrAlly.BOTH, ChooseType.SELECTED_CELL);
        spell = new Spell("Hell Fire", 600, 3, targetType, effects,
                ApplyType.ON_ATTACKER, "Put flame on a 2*2 Square.");
        saveToFile(spell);


        // ---------- 7 ----------
        effects = new ArrayList<>();
        effects.add(new WeaknessHP(Integer.MAX_VALUE, 8));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.HERO, OppOrAlly.OPP, ChooseType.SELECTED_OPP);
        spell = new Spell("Lightning Bolt", 1250, 2, targetType, effects,
                ApplyType.ON_ATTACKER, "Attack opp's hero 8 units.");
        saveToFile(spell);


        // ---------- 8 ----------
        effects = new ArrayList<>();
        effects.add(new Poison(1));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SQUARE_3_3, HeroOrMinion.BOTH, OppOrAlly.BOTH, ChooseType.SELECTED_CELL);
        spell = new Spell("Poison Lake", 900, 5, targetType, effects,
                ApplyType.ON_ATTACKER, "Put poison on a 3*3 square.");
        saveToFile(spell);


        // ---------- 9 ----------
        effects = new ArrayList<>();
        effects.add(new PowerAP(3, 4));
        effects.add(new Disarm(1));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.ALLY, ChooseType.HIMSELF);
        spell = new Spell("Madness", 650, 0, targetType, effects,
                ApplyType.ON_ATTACKER, "Increase AP 4 units for 3 turns, but disarm for 1 turn.");
        saveToFile(spell);


        // ---------- 10 ----------
        effects = new ArrayList<>();
        effects.add(new Disarm(1));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.NONE, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.NONE);
        spell = new Spell("All Disarm", 2000, 9, targetType, effects,
                ApplyType.ON_ATTACKER, "Disarm all opps for 1 turn.");
        saveToFile(spell);


        // ---------- 11 ----------
        effects = new ArrayList<>();
        effects.add(new Poison(4));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.NONE, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.NONE);
        spell = new Spell("All Poison", 1500, 8, targetType, effects,
                ApplyType.ON_ATTACKER, "Poison on all opps for 1 turn.");
        saveToFile(spell);


        // ---------- 12 ----------
        effects = new ArrayList<>();
        effects.add(new PositiveDispel());
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.BOTH, ChooseType.SELECTED_CELL);
        spell = new Spell("Dispel", 2100, 0, targetType, effects,
                ApplyType.ON_ATTACKER, "Dispel an opp or an ally.");
        saveToFile(spell);


        // ---------- 13 ----------
        effects = new ArrayList<>();
        effects.add(new WeaknessHP(Integer.MAX_VALUE, 6));
        effects.add(new Holy(3));
        effects.add(new Holy(3));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.ALLY, ChooseType.HIMSELF);
        spell = new Spell("Health With Profit", 2250, 0, targetType, effects,
                ApplyType.ON_ATTACKER, "Decrease HP 6 units but get 2 holy buffs for 2 turns.");
        saveToFile(spell);


        // ---------- 14 ----------
        effects = new ArrayList<>();
        effects.add(new PowerAP(Integer.MAX_VALUE, 6));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.ALLY, ChooseType.HIMSELF);
        spell = new Spell("PowerHP Up", 2500, 2, targetType, effects,
                ApplyType.ON_ATTACKER, "Increase AP 6 units.");
        saveToFile(spell);


        // ---------- 15 ----------
        effects = new ArrayList<>();
        effects.add(new PowerAP(Integer.MAX_VALUE, 2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.NONE, HeroOrMinion.BOTH, OppOrAlly.ALLY, ChooseType.NONE);
        spell = new Spell("All PowerHP", 2000, 4, targetType, effects,
                ApplyType.ON_ATTACKER, "Increase AP 2 units for all allys.");
        saveToFile(spell);


        // ---------- 16 ----------
        effects = new ArrayList<>();
        effects.add(new WeaknessHP(Integer.MAX_VALUE,6));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.IN_COLUMN, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.SELECTED_CELL);
        spell = new Spell("All Attack", 1500, 4, targetType, effects,
                ApplyType.ON_ATTACKER, "Attack all opps in a column 6 units.");
        saveToFile(spell);


        // ---------- 17 ----------
        effects = new ArrayList<>();
        effects.add(new WeaknessAP(Integer.MAX_VALUE, 4));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.MINION, OppOrAlly.OPP, ChooseType.SELECTED_OPP);
        spell = new Spell("Weakening", 1000, 1, targetType, effects,
                ApplyType.ON_ATTACKER, "Decrease AP 4 unit of a opp's minion.");
        saveToFile(spell);


        // ---------- 18 ----------
        effects = new ArrayList<>();
        effects.add(new WeaknessHP(Integer.MAX_VALUE, 6));
        effects.add(new PowerAP(Integer.MAX_VALUE,8));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.MINION, OppOrAlly.ALLY, ChooseType.HIMSELF);
        spell = new Spell("Sacrifice", 1600, 2, targetType, effects,
                ApplyType.ON_ATTACKER, "Decrease HP 6 units " +
                "but increase AP 8 units of an ally minion.");
        saveToFile(spell);


        // ---------- 19 ----------
        effects = new ArrayList<>();
        effects.add(new WeaknessHP(Integer.MAX_VALUE, Integer.MAX_VALUE));
        targetType = new TargetType(RandomOrNot.RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SQUARE_3_3, HeroOrMinion.MINION, OppOrAlly.OPP, ChooseType.SELECTED_CELL);
        spell = new Spell("Kings Guard", 1750, 9, targetType, effects,
                ApplyType.ON_ATTACKER, "Kill a minion around king randomly chosen.");
        saveToFile(spell);


        // ---------- 20 ----------
        effects = new ArrayList<>();
        effects.add(new Stun(2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.HIMSELF);
        spell = new Spell("Shock", 1200, 1, targetType, effects,
                ApplyType.ON_ATTACKER, "Stun an opp.");
        saveToFile(spell);

    }
}
