package controller.menus;

import models.match.Match;

public class HostBattleMenu extends Menu {
    private Match match;

    public HostBattleMenu(Match match) {
        this.match = match;
    }

    public void showGameInfo() {
        view.showGameInfo();
    }

    public void useSpell(String spell, String x, String y) {
        match.selectSpell(spell);
        match.useSpell(Integer.parseInt(x), Integer.parseInt(y));
    }

    public void selectAttacker(String cardIDInGame, String x, String y) {
        if (cardIDInGame == null)
            return;
        match.selectAttacker(cardIDInGame, Integer.parseInt(x), Integer.parseInt(y));
    }

    private void moveTo(String x, String y) {
        match.moveCard(Integer.parseInt(x), Integer.parseInt(y));
    }

    private void attack(String x, String y) {
        match.attack(Integer.parseInt(x), Integer.parseInt(y));
    }

    public void useSpecialPower(String x, String y) {
        match.selectAttacker(match.getPlayersMatchInfo()[match.getTurn()].getHero().getCardIDInGame(), Integer.parseInt(x), Integer.parseInt(y));
        match.useSpecialPower(Integer.parseInt(x), Integer.parseInt(y));
    }

    public void insertCardIn(String name, String x, String y) {
        match.insertCard(name, Integer.parseInt(x), Integer.parseInt(y));
    }

    public void deselect() {
        match.deselect();
    }

    public void endTurn() {
        match.endTurn();
    }
}
