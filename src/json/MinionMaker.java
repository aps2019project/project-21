package json;

import models.card.*;
import models.card.buffs.Stun;
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


        // ---------- 5 ----------


        // ---------- 6 ----------


        // ---------- 7 ----------


        // ---------- 8 ----------


        // ---------- 9 ----------


        // ---------- 10 ----------


        // ---------- 11 ----------


        // ---------- 12 ----------


        // ---------- 13 ----------


        // ---------- 14 ----------


        // ---------- 15 ----------


        // ---------- 16 ----------


        // ---------- 17 ----------


        // ---------- 18 ----------


        // ---------- 19 ----------


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


        // ---------- 34 ----------


        // ---------- 35 ----------


        // ---------- 36 ----------


        // ---------- 37 ----------


        // ---------- 38 ----------


        // ---------- 39 ----------


        // ---------- 40 ----------

    }
}
