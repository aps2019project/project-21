package models.card;

import models.card.target_enums.*;

public class TargetType {
    private RandomOrNot randomOrNot;
    private AttackerRange attackerRange;
    private CellType cellType;
    private HeroOrMinion heroOrMinion;
    private OppOrAlly oppOrAlly;

    public TargetType(RandomOrNot randomOrNot, AttackerRange attackerRange,
                      CellType cellType, HeroOrMinion heroOrMinion, OppOrAlly oppOrAlly) {
        this.randomOrNot = randomOrNot;
        this.attackerRange = attackerRange;
        this.cellType = cellType;
        this.heroOrMinion = heroOrMinion;
        this.oppOrAlly = oppOrAlly;
    }

    public RandomOrNot getRandomOrNot() {
        return randomOrNot;
    }

    public AttackerRange getAttackerRange() {
        return attackerRange;
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
}
