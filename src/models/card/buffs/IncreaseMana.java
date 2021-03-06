package models.card.buffs;

import models.card.Buff;
import models.card.BuffMode;
import models.match.PlayerMatchInfo;

/**
 * increases mana in an info (playerMatchInfo)
 */
public class IncreaseMana extends Buff {
    private static final long serialVersionUID = 6529685098267757014L;

    private int value;

    public IncreaseMana(int duration, int value) {
        super(duration, BuffMode.GOOD);
        this.value = value;
    }

    public void apply() {
        if (duration <= 0 || match == null)
            return;
        PlayerMatchInfo info = match.getInfo(player);
        if (info == null)
            return;
        info.increaseMana(value);
        duration--;
    }
}
