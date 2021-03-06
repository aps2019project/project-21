package models.card.buffs;

import models.card.Buff;
import models.card.BuffMode;

/**
 * holy buff for an attacker
 */
public class Holy extends Buff {
    private static final long serialVersionUID = 6529685098267757013L;

    public Holy(int duration){
        super(duration, BuffMode.GOOD);
    }

    public void apply() {
        if (duration < 0 || attacker == null)
            return;
        if (duration == 0)
            attacker.giveHolyBuff();
        else
            attacker.takeHolyBuff();
        duration--;
    }
}
