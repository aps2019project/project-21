package models.card;

import java.util.ArrayList;
import java.util.List;

public class Hero extends Attacker {
    private static List<Hero> heroes = new ArrayList<>();

    private int primaryCooldown;
    private int cooldown;


//    {
//        heroInGameView.relocate(currentCell.getX(), currentCell.getY());
//    }


    public Hero(String name, int price, int maxHp, int maxAp,
                int attackRange, AttackMode attackMode, Spell specialPower,
                int cooldown) {
        super(name, price, -1, maxHp, maxAp, attackRange, attackMode, specialPower);
        this.primaryCooldown = cooldown;
        this.cooldown = 0;
        super.canAttack = true;
        super.canMove = true;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public static List<Hero> getHeroes() {
        return heroes;
    }

    private static void addHero(Hero hero) {
        if (hero == null)
            return;
        heroes.add(hero);
    }

    public static void addHero(List<Hero> heroes) {
        if (heroes == null)
            return;
        for (Hero hero : heroes)
            addHero(hero);
    }

    public void reset() {
        super.reset();
        this.canAttack = true;
        this.canMove = true;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    void resetCooldown() {
        this.cooldown = primaryCooldown;
    }

    public void decreaseCooldown() {
        this.cooldown--;
    }


}
