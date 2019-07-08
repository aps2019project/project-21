package controller.menus;

import models.match.Match;
import view.View;

public class SinglePlayerBattleMenu extends BattleMenu {
    public SinglePlayerBattleMenu() {
    }

    public void showGameInfo() {
        View.showGameInfo();
    }

    public void useSpell(String spell, int x, int y) {
        Match.getCurrentMatch().selectSpell(spell);
        Match.getCurrentMatch().useSpell(x, y);
    }

    public void selectAttacker(String cardIDInGame, int x, int y) {
        if (cardIDInGame == null)
            return;
        Match.getCurrentMatch().selectAttacker(cardIDInGame, x, y);
    }

    public void moveOrAttack(int x, int y) {
        if (Match.getCurrentMatch().getCell(x, y).isEmpty())
            moveTo(x, y);
        else
            attack(x, y);
    }

    void moveTo(int x, int y) {
        Match.getCurrentMatch().moveCard(x, y);
    }

    void attack(int x, int y) {
        Match.getCurrentMatch().attack(x, y);
    }

    public void useSpecialPower(int x, int y) {
        Match.getCurrentMatch().selectAttacker(Match.getCurrentMatch().getPlayersMatchInfo()[0].getHero().getCardIDInGame(), x, y);
        Match.getCurrentMatch().useSpecialPower(x, y);
    }

    public void insertCardIn(String name, int x, int y) {
        Match.getCurrentMatch().insertCard(name, x, y);
    }

    public void deselect() {
        Match.getCurrentMatch().deselect();
    }

    public void endTurn() {
        Match.getCurrentMatch().endTurn();
    }
}
