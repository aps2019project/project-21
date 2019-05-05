package json;

import models.card.*;
import models.card.buffs.*;
import models.card.target_enums.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static json.CardMaker.saveToFile;

public class MinionMaker {
    public static void main(String[] args) throws IOException {
        // ---------- 1 ----------
        Minion minion = new Minion("Kamandare Fars", 300, 2, 6,
                4, 7, AttackMode.RANGED, null);
        saveToFile(minion);


        // ---------- 2 ----------
        List<Effect> effects = new ArrayList<>();
        effects.add(new Stun(1));
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
        targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.ALLY, ChooseType.HIMSELF);
        specialPower = new Spell("Oghab's Spell", 0, 0,
                targetType, effects, ApplyType.ON_ATTACKER, "Increase HP 10 units.");
        minion = new Minion("Oghab", 200, 2, 0,
                2, 3, AttackMode.RANGED, specialPower); //passive
        saveToFile(minion);

        // ---------- 16 ----------600
        minion = new Minion("Dive Gorazsavar", 300, 6, 16,
                8, -1, AttackMode.MELEE, null);
        saveToFile(minion);

        // ---------- 17 ----------


        // ---------- 18 ----------


        // ---------- 19 ----------
        minion = new Minion("Ezhdehaye Atash Andaz", 250, 5, 9,
                5, 4, AttackMode.RANGED, null);
        saveToFile(minion);

        // ---------- 20 ----------


        // ---------- 21 ----------


        // ---------- 22 ----------


        // ---------- 23 ----------


        // ---------- 24 ----------


        // ---------- 25 ----------


        // ---------- 26 ----------


        // ---------- 27 ----------


        // ---------- 28 ----------


        // ---------- 29 ----------


        // ---------- 30 ----------


        // ---------- 31 ----------


        // ---------- 32 ----------


        // ---------- 33 ----------
        minion = new Minion("Eiraj", 500, 4, 6,
                20, 3, AttackMode.RANGED, null);
        saveToFile(minion);

        // ---------- 34 ----------
        minion = new Minion("Ghole Bozorg", 600, 9, 30,
                8, 2, AttackMode.HYBRID, null);
        saveToFile(minion);

        // ---------- 35 ----------


        // ---------- 36 ----------


        // ---------- 37 ----------


        // ---------- 38 ----------


        // ---------- 39 ----------
        minion = new Minion("Shah Ghol", 600, 5, 10,
                4, -1, AttackMode.MELEE, null, true);
        saveToFile(minion);

        // ---------- 40 ----------
        minion = new Minion("Arzhang Div", 600, 3, 6,
                6, -1, AttackMode.MELEE, null, true);
        saveToFile(minion);
    }
}
