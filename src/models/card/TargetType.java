package models.card;

import models.card.target_enums.*;

import java.io.Serializable;

public class TargetType  implements Serializable {
    private RandomOrNot randomOrNot = RandomOrNot.NOT_RANDOM;
    private TargetAttackerRange targetAttackerRange = TargetAttackerRange.ALL_THREE;
    private CellType cellType = CellType.NONE;
    private HeroOrMinion heroOrMinion = HeroOrMinion.BOTH;
    private OppOrAlly oppOrAlly = OppOrAlly.BOTH;
    private ChooseType chooseType = ChooseType.NONE;

    public TargetType() {

    }

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

    public ChooseType getChooseType() {
        return chooseType;
    }
}
