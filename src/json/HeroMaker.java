package json;

import models.card.*;
import models.card.buffs.*;
import models.card.effects.DecreaseHP;
import models.card.effects.EffectName;
import models.card.effects.GiveEffect;
import models.card.target_enums.*;

import java.io.IOException;

import static json.CardMaker.saveToFile;

public class HeroMaker {
    public static void main(String[] args) throws IOException {
        // ---------- 1 ----------
        Effect effect = new GiveEffect(Integer.MAX_VALUE, 4, EffectName.POWER_AP);
        TargetType targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.HERO, OppOrAlly.ALLY, ChooseType.HIMSELF);
        Spell specialPower = new Spell("Dive Sefid's Spell", 0, 1,
                targetType, effect, ApplyOnCellOrAttacker.ON_ATTACKER, "Casts one power buff with 4 ap increase on himself forever");
        Hero hero = new Hero("Dive Sefid", 8000, 50,
                4, 1, AttackMode.MELEE, specialPower, 2);
        saveToFile(hero);


        // ---------- 2 ----------
        effect = new Stun(1);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.NONE, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.NONE);
        specialPower = new Spell("Simorgh's Spell", 0, 5,
                targetType, effect,ApplyOnCellOrAttacker.ON_ATTACKER,  "Stuns all opp attackers for one turn.");
        hero = new Hero("Simorgh", 9000, 50,
                4, 1, AttackMode.MELEE, specialPower, 8);
        saveToFile(hero);


        // ---------- 3 ----------
        effect = new Disarm(1);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.NONE, HeroOrMinion.BOTH, OppOrAlly.BOTH, ChooseType.SELECTED_OPP);
        specialPower = new Spell("Ezhdehaye Haftsar's Spell", 0, 0,
                targetType, effect, ApplyOnCellOrAttacker.ON_ATTACKER, "Disarms a selected attacker.");
        hero = new Hero("Ezhdehaye Haftsar", 8000, 50,
                4, 1, AttackMode.MELEE, specialPower, 1);
        saveToFile(hero);


        // ---------- 4 ----------
        effect = new Stun(1);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.NONE, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.SELECTED_OPP);
        specialPower = new Spell("Rakhsh's Spell", 0, 1,
                targetType, effect, ApplyOnCellOrAttacker.ON_ATTACKER, "Stuns one opponent attacker for one turn.");
        hero = new Hero("Rakhsh", 8000, 50,
                4, 1, AttackMode.MELEE, specialPower, 2);
        saveToFile(hero);


        // ---------- 5 ----------
        effect = new Poison(1, ActivationType.ON_ATTACK);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.NONE, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.SELECTED_OPP);
        specialPower = new Spell("Zahak's Spell", 0, 0,
                targetType, effect, ApplyOnCellOrAttacker.ON_ATTACKER, "Poisons an opponent on attack for 3 turns.");
        hero = new Hero("Zahak", 10000, 50,
                2, 1, AttackMode.MELEE, specialPower, 0);
        saveToFile(hero);


        // ---------- 6 ----------
        effect = new Poison(1, ActivationType.ON_ATTACK);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.NONE, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.SELECTED_OPP);
        specialPower = new Spell("Kaveh's Spell", 0, 1,
                targetType, effect, ApplyOnCellOrAttacker.ON_CELL, "Gives a selected cell holy buff for 3 turns.");
        hero = new Hero("Kaveh", 8000, 50,
                4, 1, AttackMode.MELEE, specialPower, 3);
        saveToFile(hero);


        // ---------- 7 ----------
        effect = new DecreaseHP(4);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.IN_ROW, HeroOrMinion.BOTH, OppOrAlly.BOTH, ChooseType.SELECTED_ALLY);
        specialPower = new Spell("Arash's Spell", 0, 2,
                targetType, effect, ApplyOnCellOrAttacker.ON_ATTACKER, "Gives 4 damages to all opponent attackers in the same row as selected hero.");
        hero = new Hero("Arash", 10000, 30,
                2, 6, AttackMode.RANGED, specialPower, 2);
        saveToFile(hero);


        // ---------- 8 ----------
        effect = new GiveEffect(EffectName.POSITIVE_DISPEL);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.NONE, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.SELECTED_OPP);
        specialPower = new Spell("Afsaneh's Spell", 0, 1,
                targetType, effect, ApplyOnCellOrAttacker.ON_ATTACKER, "Dispels an opponent attacker.");
        hero = new Hero("Afsaneh", 11000, 40,
                3, 3, AttackMode.RANGED, specialPower, 2);
        saveToFile(hero);


        // ---------- 9 ----------
        effect = new GiveEffect(Integer.MAX_VALUE, EffectName.HOLY);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.NONE, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.SELECTED_OPP);
        specialPower = new Spell("Esfandiar's Spell", 0, 0,
                targetType, effect, ApplyOnCellOrAttacker.ON_ATTACKER, "Has 3 holy buffs continuously.");
        hero = new Hero("Esfandiar", 12000, 35,
                3, 3, AttackMode.HYBRID, specialPower, 0);
        saveToFile(hero);


        // ---------- 10 ----------
        hero = new Hero("Rostam", 8000, 55,
                7, 4, AttackMode.HYBRID, null, 0);
        saveToFile(hero);
    }
}
