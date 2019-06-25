package json;

import models.card.*;
import models.card.buffs.*;
import models.card.target_enums.*;

import java.util.ArrayList;
import java.util.List;

import static json.CardMaker.saveToFile;

public class MinionMaker {
    public static void main() {
        // ---------- 1 ----------
        Minion minion = new Minion("Kamandare Fars", 300, 2, 6,
                4, 7, AttackMode.RANGED, null);
        saveToFile(minion);


        // ---------- 2 ----------
        List<Effect> effects = new ArrayList<>();
        effects.add(new Stun(1));
        effects.get(0).setActivationType(ActivationType.ON_ATTACK);
        TargetType targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.SELECTED_OPP);
        Spell specialPower = new Spell("Shamshirzane Fars's Spell", 0, 0,
                targetType, effects, ApplyType.ON_ATTACKER, "Stuns the opp on attack.");
        minion = new Minion("Shamshirzane Fars", 400, 2, 6,
                4, -1, AttackMode.MELEE, specialPower);
        saveToFile(minion);


        // ---------- 3 ----------
        minion = new Minion("Neyzedare Fars", 500, 1, 5,
                3, 3, AttackMode.HYBRID, null);
        saveToFile(minion);


        // ---------- 4 ----------
        minion = new Minion("Asb Savare Fars", 200, 4, 10,
                6, -1, AttackMode.MELEE, null);
        saveToFile(minion);

        // ---------- 5 ----------
        effects = new ArrayList<>();
        effects.add(new WeaknessHP(Integer.MAX_VALUE, 5)); //save number of attacked.
        effects.get(0).setActivationType(ActivationType.ON_ATTACK);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.SELECTED_OPP);
        specialPower = new Spell("Pahlavane Fars's Spell", 0, 0,
                targetType, effects, ApplyType.ON_ATTACKER, "Attack 5 more for " +
                "each time it attacked that card.");
        minion = new Minion("Pahlavane Fars", 600, 9, 24,
                6, -1, AttackMode.MELEE, specialPower);
        saveToFile(minion);

        // ---------- 6 ----------

        minion = new Minion("Sepahsalare Fars", 800, 7, 12,
                4, -1, AttackMode.MELEE, null, true);
        saveToFile(minion);

        // ---------- 7 ----------
        minion = new Minion("Kamandare Torani", 500, 1, 3,
                4, 5, AttackMode.RANGED, null);
        saveToFile(minion);

        // ---------- 8 ----------
        minion = new Minion("Ghollabsangdare Torani", 600, 1, 4,
                2, 7, AttackMode.RANGED, null);
        saveToFile(minion);

        // ---------- 9 ----------
        minion = new Minion("Neyzedare Torani", 600, 1, 4,
                4, 3, AttackMode.HYBRID, null);
        saveToFile(minion);

        // ---------- 10 ----------
        effects = new ArrayList<>();
        effects.add(new Disarm(1));
        effects.add(new Poison(4));
        effects.get(0).setActivationType(ActivationType.ON_ATTACK);
        effects.get(0).setActivationType(ActivationType.ON_ATTACK);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.SELECTED_OPP);
        specialPower = new Spell("Jasose Torani's Spell", 0, 0,
                targetType, effects, ApplyType.ON_ATTACKER, "Disarm 1 turn and poison 4 turns.");
        minion = new Minion("Jasose Torani", 700, 4, 6,
                6, -1, AttackMode.MELEE, specialPower);
        saveToFile(minion);

        // ---------- 11 ----------
        minion = new Minion("Gorzdare Torani", 450, 2, 3,
                10, -1, AttackMode.MELEE, null);
        saveToFile(minion);

        // ---------- 12 ----------
        minion = new Minion("Shahzade Torani", 800, 6, 6,
                10, -1, AttackMode.MELEE, null, true);
        saveToFile(minion);

        // ---------- 13 ----------
        minion = new Minion("Dive Siah", 300, 9, 14,
                10, 7, AttackMode.HYBRID, null);
        saveToFile(minion);

        // ---------- 14 ----------
        minion = new Minion("Ghole Sangandaz", 300, 9, 12,
                12, 7, AttackMode.RANGED, null);
        saveToFile(minion);

        // ---------- 15 ----------
        effects = new ArrayList<>();
        effects.add(new PowerHP(Integer.MAX_VALUE,10));
        effects.get(0).setActivationType(ActivationType.PASSIVE);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.ALLY, ChooseType.HIMSELF);
        specialPower = new Spell("Oghab's Spell", 0, 0,
                targetType, effects, ApplyType.ON_ATTACKER, "Increase HP 10 units.");
        minion = new Minion("Oghab", 200, 2, 0,
                2, 3, AttackMode.RANGED, specialPower);
        saveToFile(minion);

        // ---------- 16 ----------600
        minion = new Minion("Dive Gorazsavar", 300, 6, 16,
                8, -1, AttackMode.MELEE, null);
        saveToFile(minion);

        // ---------- 17 ----------
        effects = new ArrayList<>();
        effects.add(new WeaknessHP(Integer.MAX_VALUE, 2));
        effects.get(0).setActivationType(ActivationType.ON_DEATH);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SQUARE_3_3, HeroOrMinion.MINION, OppOrAlly.OPP, ChooseType.HIMSELF);
        specialPower = new Spell("Ghole Tak Cheshm's spell", 0, 0,
                targetType, effects, ApplyType.ON_ATTACKER, "Attack all neighbour minions 2 units.");
        minion = new Minion("Ghole Tak cheshm", 500, 7, 12,
                11, 3, AttackMode.HYBRID, specialPower);
        saveToFile(minion);

        // ---------- 18 ----------
        effects = new ArrayList<>();
        effects.add(new Poison(3));
        effects.get(0).setActivationType(ActivationType.ON_ATTACK);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP, ChooseType.SELECTED_OPP);
        specialPower = new Spell("Mare Sammi's Spell", 0, 0,
                targetType, effects, ApplyType.ON_ATTACKER, "Poison opp 3 turns.");
        minion = new Minion("Mare Sammi", 300, 4, 5,
                6, 4, AttackMode.RANGED, specialPower);
        saveToFile(minion);

        // ---------- 19 ----------
        minion = new Minion("Ezhdehaye Atash Andaz", 250, 5, 9,
                5, 4, AttackMode.RANGED, null);
        saveToFile(minion);

        // ---------- 20 ----------
        //later

        // ---------- 21 ----------
        //later

        // ---------- 22 ----------
        effects = new ArrayList<>();
        effects.add(new WeaknessHP(Integer.MAX_VALUE, 6)); //delay 1 round!
        effects.add(new WeaknessHP(Integer.MAX_VALUE, 4)); //delay 2 round!
        effects.get(0).setActivationType(ActivationType.ON_ATTACK);
        effects.get(1).setActivationType(ActivationType.ON_ATTACK);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.MINION, OppOrAlly.OPP, ChooseType.SELECTED_OPP);
        specialPower = new Spell("Gorge Sefid's Spell", 0, 0,
                targetType, effects, ApplyType.ON_ATTACKER,
                "Decrease HP of attacked minion 6 and 4 at next 2 rounds.");
        minion = new Minion("Gorge Sefid", 400, 5, 8,
                2, -1, AttackMode.MELEE, specialPower);
        saveToFile(minion);

        // ---------- 23 ----------
        effects = new ArrayList<>();
        effects.add(new WeaknessHP(Integer.MAX_VALUE, 8)); //delay 1 round!
        effects.get(0).setActivationType(ActivationType.ON_ATTACK);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.MINION, OppOrAlly.OPP, ChooseType.SELECTED_OPP);
        specialPower = new Spell("Palang's Spell", 0, 0,
                targetType, effects, ApplyType.ON_ATTACKER,
                "Decrease HP of attacked minion 8 at next round.");
        minion = new Minion("Palang", 400, 4, 6,
                2, -1, AttackMode.MELEE, specialPower);
        saveToFile(minion);

        // ---------- 24 ----------
        effects = new ArrayList<>();
        effects.add(new WeaknessHP(Integer.MAX_VALUE, 6)); //delay 1 round!
        effects.get(0).setActivationType(ActivationType.ON_ATTACK);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.MINION, OppOrAlly.OPP, ChooseType.SELECTED_OPP);
        specialPower = new Spell("Gorg's Spell", 0, 0,
                targetType, effects, ApplyType.ON_ATTACKER,
                "Decrease HP of attacked minion 6 at next round.");
        minion = new Minion("Gorg", 400, 3, 6,
                1, -1, AttackMode.MELEE, specialPower);
        saveToFile(minion);

        // ---------- 25 ----------
        effects = new ArrayList<>();
        effects.add(new PowerAP(1,2));
        effects.add(new WeaknessHP(1,1));
        effects.get(0).setActivationType(ActivationType.PASSIVE);
        effects.get(1).setActivationType(ActivationType.PASSIVE);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SQUARE_3_3, HeroOrMinion.BOTH, OppOrAlly.ALLY, ChooseType.HIMSELF);
        specialPower = new Spell("Jadogar's Spell", 0, 0,
                targetType, effects, ApplyType.ON_ATTACKER,
                "Power AP 2 units but weakness HP 1 unit for 1 round, on itself and neighbors.  ");
        minion = new Minion("Jadogar", 550, 4, 5,
                4, 3, AttackMode.RANGED, specialPower);
        saveToFile(minion);

        // ---------- 26 ----------
        effects = new ArrayList<>();
        effects.add(new PowerAP(Integer.MAX_VALUE,2));
        effects.add(new Holy(Integer.MAX_VALUE));
        effects.get(0).setActivationType(ActivationType.PASSIVE);
        effects.get(1).setActivationType(ActivationType.PASSIVE);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SQUARE_3_3, HeroOrMinion.BOTH, OppOrAlly.ALLY, ChooseType.HIMSELF);
        specialPower = new Spell("Jadogare Azam's Spell", 0, 0,
                targetType, effects, ApplyType.ON_ATTACKER,
                "Power AP 2 units and continuous holy buff, on itself and neighbors.");
        minion = new Minion("Jadogare Azam", 550, 6, 6,
                6, 5, AttackMode.RANGED, specialPower);
        saveToFile(minion);

        // ---------- 27 ----------
        //later

        // ---------- 28 ----------
        //later

        // ---------- 29 ----------
        //later

        // ---------- 30 ----------
        //later

        // ---------- 31 ----------
        effects = new ArrayList<>();
        effects.add(new WeaknessHP(Integer.MAX_VALUE, 16));
        effects.get(0).setActivationType(ActivationType.ON_SPAWN);
        targetType = new TargetType(RandomOrNot.RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.MINION, OppOrAlly.OPP, ChooseType.NONE);
        specialPower = new Spell("Bahman's Spell", 0, 0,
                targetType, effects, ApplyType.ON_ATTACKER,
                "Attack a minion 16 units, randomly chosen.");
        minion = new Minion("Bahman", 200, 2, 0,
                2, 3, AttackMode.RANGED, specialPower);
        saveToFile(minion);

        // ---------- 32 ----------
        //later

        // ---------- 33 ----------
        minion = new Minion("Eiraj", 500, 4, 6,
                20, 3, AttackMode.RANGED, null);
        saveToFile(minion);

        // ---------- 34 ----------
        minion = new Minion("Ghole Bozorg", 600, 9, 30,
                8, 2, AttackMode.HYBRID, null);
        saveToFile(minion);

        // ---------- 35 ----------
        //later

        // ---------- 36 ----------
        effects = new ArrayList<>();
        effects.add(new Stun(1));
        effects.get(0).setActivationType(ActivationType.ON_SPAWN);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SQUARE_3_3, HeroOrMinion.MINION, OppOrAlly.OPP, ChooseType.HIMSELF);
        specialPower = new Spell("Nane Sarma's Spell", 0, 0,
                targetType, effects, ApplyType.ON_ATTACKER, "Stun 8 neighbourhood minions.");
        minion = new Minion("Nane Sarma", 500, 3, 3,
                4, 5, AttackMode.RANGED, specialPower);
        saveToFile(minion);

        // ---------- 37 ----------
        effects = new ArrayList<>();
        for(int i = 0; i < 12; i++) {
            effects.add(new Holy(Integer.MAX_VALUE));
            effects.get(i).setActivationType(ActivationType.PASSIVE);
        }
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.ALLY, ChooseType.HIMSELF);
        specialPower = new Spell("Folad Zereh's Spell", 0, 0,
                targetType, effects, ApplyType.ON_ATTACKER, "Having 12 continuous holy buffs.");
        minion = new Minion("Folad Zereh", 650, 3, 1,
                1, -1, AttackMode.MELEE, specialPower);
        saveToFile(minion);

        // ---------- 38 ----------
        effects = new ArrayList<>();
        effects.add(new WeaknessHP(Integer.MAX_VALUE, 6));
        effects.get(0).setActivationType(ActivationType.ON_DEATH);
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.HERO, OppOrAlly.OPP, ChooseType.NONE);
        specialPower = new Spell("Siavash's Spell", 0, 0,
                targetType, effects, ApplyType.ON_ATTACKER, "Attack opp's hero 6 units on death.");
        minion = new Minion("", 350, 4, 8,
                5, -1, AttackMode.MELEE, specialPower);
        saveToFile(minion);

        // ---------- 39 ----------
        minion = new Minion("Shah Ghol", 600, 5, 10,
                4, -1, AttackMode.MELEE, null, true);
        saveToFile(minion);

        // ---------- 40 ----------
        minion = new Minion("Arzhang Div", 600, 3, 6,
                6, -1, AttackMode.MELEE, null, true);
        saveToFile(minion);
    }

    public static void custumMinion(String name, int cost, int manaCost, int hp,
                                    int ap, int range, AttackMode attackMode, Spell spell){
        Minion minion = new Minion(name, cost, manaCost, hp,
                ap, range, attackMode, spell);
        saveToFile(minion);
    }
}
