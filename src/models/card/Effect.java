package models.card;

import models.match.Cell;

public class Effect {
    private int changeInAP;
    private int changeInHP;
    private boolean isItFreezer;
    private boolean isItPassive;

    public void apply(Cell cell){

    }

    public int getChangeInAP() {
        return changeInAP;
    }

    public void setChangeInAP(int changeInAP) {
        this.changeInAP = changeInAP;
    }

    public int getChangeInHP() {
        return changeInHP;
    }

    public void setChangeInHP(int changeInHP) {
        this.changeInHP = changeInHP;
    }

    public boolean isItFreezer() {
        return isItFreezer;
    }

    public void setItFreezer(boolean itFreezer) {
        isItFreezer = itFreezer;
    }

    public boolean isItPassive() {
        return isItPassive;
    }

    public void setItPassive(boolean itPassive) {
        isItPassive = itPassive;
    }
}
