package models.card.effects;

import models.card.Effect;
import models.card.Hero;
import models.card.buffs.Disarm;
import models.card.buffs.Holy;
import models.card.buffs.Power;
import models.card.buffs.PowerMode;
import models.match.PlayerMatchInfo;

public class GiveEffect extends Effect {
    private int duration;
    private int value;
    private EffectName effectName;

    public GiveEffect(int duration, int value, EffectName effectName) {
        super();
        this.duration = duration;
        this.value = value;
        this.effectName = effectName;
    }

    public GiveEffect(int duration, EffectName effectName) {
        super();
        this.duration = duration;
        this.effectName = effectName;
    }

    public GiveEffect(EffectName effectName) {
        super();
        this.effectName = effectName;
    }

    public void apply() {
        if (match == null || player == null)
            return;
        PlayerMatchInfo info = match.getInfo(player);
        if (info == null)
            return;
        Hero hero = info.getHero();
        if (hero == null)
            return;
        hero.giveHolyBuff();
    }

    private Effect extractEffect() {
        switch (effectName) {
            case DISARM:
                return new Disarm(duration);
            case HOLY:
                return new Holy(duration);
            case POWER:
                return new Power(duration, value, PowerMode.AP);
//            case POSITIVE_DISPEL:
//                return new PositiveDispel()
        }
        return null;
    }
}
