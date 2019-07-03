package controller.menus;

import models.match.Match;

public class BattleMenu extends Menu {
    private static BattleMenu instance = new BattleMenu();

    public static BattleMenu getInstance() {
        return instance;
    }

    private BattleMenu() {
    }

    public void showGameInfo() {
        view.showGameInfo();
    }

    public void useSpell(String spell, int x, int y) {
        Match.getCurrentMatch().selectSpell(spell);
        Match.getCurrentMatch().useSpell(x, y);
    }

    public boolean selectAttacker(String cardIDInGame) {
        if (cardIDInGame == null)
            return false;
        return Match.getCurrentMatch().selectAttacker(cardIDInGame);
    }

    public int moveOrAttack(int x, int y) {
        if (Match.getCurrentMatch().getCell(x, y).isEmpty())
            return moveTo(x, y);
        else
            return 2 + attack(x, y);
    }

    private int moveTo(int x, int y) {
        return Match.getCurrentMatch().moveCard(x, y);
    }

    private int attack(int x, int y) {
        return Match.getCurrentMatch().attack(x, y);
    }

    public void useSpecialPower(int x, int y) {
        Match.getCurrentMatch().selectAttacker(Match.getCurrentMatch().getPlayersMatchInfo()[0].getHero().getCardIDInGame());
        Match.getCurrentMatch().useSpecialPower(x, y);
    }

    public void insertCardIn(String name, int x, int y) {
        Match.getCurrentMatch().insertCard(name, x, y);
    }
}
