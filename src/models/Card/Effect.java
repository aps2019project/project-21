package models.Card;

import models.Match.Cell;

public class Effect {
    private int changeInAP;
    private int changeINHP;
    private boolean isItfreezer;
    private boolean isItPassive;

    public void cast(Cell cell){

    }

    public int getChangeInAP() {
        return changeInAP;
    }

    public void setChangeInAP(int changeInAP) {
        this.changeInAP = changeInAP;
    }

    public int getChangeINHP() {
        return changeINHP;
    }

    public void setChangeINHP(int changeINHP) {
        this.changeINHP = changeINHP;
    }

    public boolean isItfreezer() {
        return isItfreezer;
    }

    public void setItfreezer(boolean itfreezer) {
        isItfreezer = itfreezer;
    }

    public boolean isItPassive() {
        return isItPassive;
    }

    public void setItPassive(boolean itPassive) {
        isItPassive = itPassive;
    }
}
