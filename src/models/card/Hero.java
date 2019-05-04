package models.card;

import java.util.ArrayList;
import java.util.List;

public class Hero extends Attacker {
    private static List<Hero> heroes = new ArrayList<>();

    private int cooldown;

    public Hero(String name, int price, int maxHp, int maxAp,
                int attackRange, AttackMode attackMode, Spell specialPower,
                int cooldown) {
        super(name, price, -1, maxHp, maxAp, attackRange, attackMode, specialPower);
        this.cooldown = cooldown;
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

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public static List<Hero> getHeroes() {
        return heroes;
    }

    public static void addHero(Hero hero) {
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

    public static Hero getHeroByID(String ID) {

        return null;
    }
}
