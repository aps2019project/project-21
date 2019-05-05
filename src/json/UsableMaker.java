package json;

import models.Item.Usable;
import models.card.ApplyType;
import models.card.Effect;
import models.card.TargetType;
import models.card.buffs.Disarm;
import models.card.buffs.IncreaseMana;
import models.card.buffs.WeaknessAP;
import models.card.buffs.WeaknessHP;
import models.card.effects.EffectName;
import models.card.effects.GiveEffect;
import models.card.target_enums.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static json.CardMaker.saveToFile;

public class UsableMaker {
    public static void main(String[] args) throws IOException {
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


        // ---------- 6 ----------


        // ---------- 7 ----------


        // ---------- 8 ----------


        // ---------- 9 ----------


        // ---------- 10 ----------


        // ---------- 11 ----------

    }
}
