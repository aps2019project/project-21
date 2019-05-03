package json;

import models.card.Effect;
import models.card.Spell;
import models.card.TargetType;
import models.card.buffs.Stun;
import models.card.target_enums.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static json.CardMaker.saveToFile;

public class SpellMaker {
    public static void main(String[] args) throws IOException {
        // ---------- 1 ----------
        List<Effect> effects = new ArrayList<>();
        //effects.add(new PowerHP(Integer.MAX_VALUE, 8, PowerMode.AP, EffectApplyInfo.ON_ALLY));
        //effects.add(new Disarm(Integer.MAX_VALUE, EffectApplyInfo.ON_OPP));
        //effects.add(new Effect(EffectApplyInfo.ON_BOTH, EffectType.POSITIVE_DISPEL));
        //effects.add(new DecreaseHP(8,EffectApplyInfo.ON_OPP_HERO));
        //effects.add(new Flame(2));
        //effects.add(new Poison(4, EffectApplyInfo.ON_OPP));
        //effects.add(new WeaknessHP(Integer.MAX_VALUE, Integer.MAX_VALUE, WeaknessMode.AP, EffectApplyInfo.ON_OPP));
        effects.add(new Stun(2));
        TargetType targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.HIMSELF);
        Spell spell = new Spell("Total Disarm", 1000, 0, targetType, effects,
                "Disarms an opp til the end.");
        saveToFile(spell);


        // ---------- 2 ----------
        effects = new ArrayList<>();
        effects.add(new Stun(2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.HIMSELF);
        spell = new Spell("Area Dispel", 1500, 2, targetType, effects,
                "stun an opp");
        saveToFile(spell);


        // ---------- 3 ----------
        effects = new ArrayList<>();
        effects.add(new Stun(2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.HIMSELF);
        spell = new Spell("Empower", 250, 1, targetType, effects,
                "stun an opp");
        saveToFile(spell);


        // ---------- 4 ----------
        effects = new ArrayList<>();
        effects.add(new Stun(2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.HIMSELF);
        spell = new Spell("Fireball", 400, 1, targetType, effects,
                "stun an opp");
        saveToFile(spell);


        // ---------- 5 ----------
        effects = new ArrayList<>();
        effects.add(new Stun(2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.HIMSELF);
        spell = new Spell("God Strength", 450, 2, targetType, effects,
                "stun an opp");
        saveToFile(spell);


        // ---------- 6 ----------
        effects = new ArrayList<>();
        effects.add(new Stun(2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.HIMSELF);
        spell = new Spell("Hell Fire", 600, 3, targetType, effects,
                "stun an opp");
        saveToFile(spell);


        // ---------- 7 ----------
        effects = new ArrayList<>();
        effects.add(new Stun(2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.HIMSELF);
        spell = new Spell("Lightning Bolt", 1250, 2, targetType, effects,
                "stun an opp");
        saveToFile(spell);


        // ---------- 8 ----------
        effects = new ArrayList<>();
        effects.add(new Stun(2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.HIMSELF);
        spell = new Spell("Poison Lake", 900, 5, targetType, effects,
                "stun an opp");
        saveToFile(spell);


        // ---------- 9 ----------
        effects = new ArrayList<>();
        effects.add(new Stun(2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.HIMSELF);
        spell = new Spell("Madness", 650, 0, targetType, effects,
                "stun an opp");
        saveToFile(spell);


        // ---------- 10 ----------
        effects = new ArrayList<>();
        effects.add(new Stun(2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.HIMSELF);
        spell = new Spell("All Disarm", 2000, 9, targetType, effects,
                "stun an opp");
        saveToFile(spell);


        // ---------- 11 ----------
        effects = new ArrayList<>();
        effects.add(new Stun(2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.HIMSELF);
        spell = new Spell("All Poison", 1500, 8, targetType, effects,
                "stun an opp");
        saveToFile(spell);


        // ---------- 12 ----------
        effects = new ArrayList<>();
        effects.add(new Stun(2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.HIMSELF);
        spell = new Spell("Dispel", 2100, 0, targetType, effects,
                "stun an opp");
        saveToFile(spell);


        // ---------- 13 ----------
        effects = new ArrayList<>();
        effects.add(new Stun(2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.HIMSELF);
        spell = new Spell("Health With Profit", 2250, 0, targetType, effects,
                "stun an opp");
        saveToFile(spell);


        // ---------- 14 ----------
        effects = new ArrayList<>();
        effects.add(new Stun(2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.HIMSELF);
        spell = new Spell("PowerHP Up", 2500, 2, targetType, effects,
                "stun an opp");
        saveToFile(spell);


        // ---------- 15 ----------
        effects = new ArrayList<>();
        effects.add(new Stun(2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.HIMSELF);
        spell = new Spell("All PowerHP", 2000, 4, targetType, effects,
                "stun an opp");
        saveToFile(spell);


        // ---------- 16 ----------
        effects = new ArrayList<>();
        effects.add(new Stun(2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.HIMSELF);
        spell = new Spell("All Attack", 1500, 4, targetType, effects,
                "stun an opp");
        saveToFile(spell);


        // ---------- 17 ----------
        effects = new ArrayList<>();
        effects.add(new Stun(2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.HIMSELF);
        spell = new Spell("Weakening", 1000, 1, targetType, effects,
                "stun an opp");
        saveToFile(spell);


        // ---------- 18 ----------
        effects = new ArrayList<>();
        effects.add(new Stun(2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.HIMSELF);
        spell = new Spell("Sacrifice", 1600, 2, targetType, effects,
                "stun an opp");
        saveToFile(spell);


        // ---------- 19 ----------
        effects = new ArrayList<>();
        effects.add(new Stun(2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.HIMSELF);
        spell = new Spell("Kings Guard", 1750, 9, targetType, effects,
                "stun an opp");
        saveToFile(spell);


        // ---------- 20 ----------
        effects = new ArrayList<>();
        effects.add(new Stun(2));
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.HIMSELF);
        spell = new Spell("Shock", 1200, 1, targetType, effects,
                "stun an opp");
        saveToFile(spell);

    }
}
