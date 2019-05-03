package models.card;

import models.card.target_enums.*;

public class TargetType {
    private RandomOrNot randomOrNot;
    private TargetAttackerRange targetAttackerRange;
    private CellType cellType;
    private HeroOrMinion heroOrMinion;
    private OppOrAlly oppOrAlly;
    private ChooseType chooseType;

    public TargetType(RandomOrNot randomOrNot, TargetAttackerRange targetAttackerRange,
                      CellType cellType, HeroOrMinion heroOrMinion, OppOrAlly oppOrAlly,
                      ChooseType chooseType) {
        this.randomOrNot = randomOrNot;
        this.targetAttackerRange = targetAttackerRange;
        this.cellType = cellType;
        this.heroOrMinion = heroOrMinion;
        this.oppOrAlly = oppOrAlly;
        this.chooseType = chooseType;
    }

    public RandomOrNot getRandomOrNot() {
        return randomOrNot;
    }

    public TargetAttackerRange getTargetAttackerRange() {
        return targetAttackerRange;
    }

    public CellType getCellType() {
        return cellType;
    }

    public HeroOrMinion getHeroOrMinion() {
        return heroOrMinion;
    }

    public OppOrAlly getOppOrAlly() {
        return oppOrAlly;
    }

    public String targetString(){
        return null;
    }
}
