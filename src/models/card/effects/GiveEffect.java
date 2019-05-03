package models.card.effects;

import models.card.Effect;
import models.card.Hero;
import models.match.PlayerMatchInfo;

public class GiveEffect extends Effect {
    private int value;
    private EffectName effectName;

    public GiveEffect(int value, EffectName effectName) {
        super();
        this.value = value;
        this.effectName = effectName;
    }

    public void apply(){
        if (match == null || player == null)
            return;
        PlayerMatchInfo info = match.getInfo(player);
        if(info == null)
            return;
        Hero hero = info.getHero();
        if(hero == null)
            return;
        hero.giveHolyBuff();
    }
}
