package json;

import models.card.Effect;
import models.card.Spell;
import models.card.TargetType;
import models.card.buffs.Stun;
import models.card.target_enums.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpellMaker {
    public static void main(String[] args) throws IOException {
        List<Effect> effects = new ArrayList<>();
        //effects.add(new Power(Integer.MAX_VALUE, 8, PowerMode.AP, EffectApplyInfo.ON_ALLY));
        //effects.add(new Disarm(Integer.MAX_VALUE, EffectApplyInfo.ON_OPP));
        //effects.add(new Effect(EffectApplyInfo.ON_BOTH, EffectType.POSITIVE_DISPEL));
        //effects.add(new DecreaseHP(8,EffectApplyInfo.ON_OPP_HERO));
        //effects.add(new Flame(2));
        //effects.add(new Poison(4, EffectApplyInfo.ON_OPP));
        //effects.add(new Weakness(Integer.MAX_VALUE, Integer.MAX_VALUE, WeaknessMode.AP, EffectApplyInfo.ON_OPP));
        effects.add(new Stun(2));
        TargetType targetType = new TargetType(RandomOrNot.NOT_RANDOM, TargetAttackerRange.ALL_THREE,
                CellType.SINGLE_CELL, HeroOrMinion.BOTH, OppOrAlly.OPP);
        Spell spell = new Spell("Shock", 1200, 1, targetType, effects,
                "stun an opp");
        CardMaker.saveToFile(spell);
    }
}
