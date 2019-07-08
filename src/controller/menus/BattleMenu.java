package controller.menus;

public abstract class BattleMenu extends Menu{
    public abstract void showGameInfo();

    public abstract void useSpell(String spell, int x, int y);

    public abstract void selectAttacker(String cardIDInGame, int x, int y);

    public abstract void moveOrAttack(int x, int y);

    abstract void moveTo(int x, int y);

    abstract void attack(int x, int y);

    public abstract void useSpecialPower(int x, int y);

    public abstract void insertCardIn(String name, int x, int y);

    public abstract void deselect();

    public abstract void endTurn();
}
