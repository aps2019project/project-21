package models.card.buffs;

import models.card.ApplyType;
import models.card.Attacker;
import models.card.Buff;

public class Weakness extends Buff {
    private int value;
    private int initialHP;

    public Weakness(int value, int duration, Attacker attacker){
        super(duration, null, attacker, ApplyType.ON_ATTACKER);
        this.value = value;
        initialHP = attacker.getHp();
    }

    public void apply(){
        if(duration < 0 || attacker == null)
            return;
        if(duration == 0)
            attacker.setHp(initialHP);
        else
            attacker.decreaseHP(value);
        duration--;
    }
}
