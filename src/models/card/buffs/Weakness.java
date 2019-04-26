package models.card.buffs;

import models.card.ApplyType;
import models.card.Attacker;
import models.card.Buff;
import models.card.BuffMode;

public class Weakness extends Buff {
    private int value;
    private int initialHP;
    private int initialAP;
    private WeaknessMode mode;

    public Weakness(int value, int duration, Attacker attacker, WeaknessMode mode){
        super(duration, null, attacker, ApplyType.ON_ATTACKER, BuffMode.EVIL);
        this.value = value;
        this.mode = mode;
        initialHP = attacker.getHP();
        initialAP = attacker.getAP();
    }

    public void apply(){
        if(duration < 0 || attacker == null)
            return;
        if(mode == WeaknessMode.HP){
            if(duration == 0)
                attacker.setHP(initialHP);
            else
                attacker.decreaseHP(value);
        }
        else{
            if(duration == 0)
                attacker.setAP(initialAP);
            else
                attacker.decreaseAP(value);
        }
        duration--;
    }
}

enum WeaknessMode{
    HP,
    AP
}