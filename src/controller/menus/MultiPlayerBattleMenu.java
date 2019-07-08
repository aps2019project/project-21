package controller.menus;

import models.BattleAction;
import models.match.Match;
import network.Client;
import network.message.Request;
import view.View;

public class MultiPlayerBattleMenu extends BattleMenu {
    public MultiPlayerBattleMenu() {
    }

    public void showGameInfo() {
        View.showGameInfo();
    }

    public void useSpell(String spell, int x, int y) {
        BattleAction battleAction = new BattleAction("useSpell", spell, Integer.toString(x), Integer.toString(y));
        Client.write(Request.makeBattleActionRequest(battleAction));
    }

    public void selectAttacker(String cardIDInGame, int x, int y) {
        if (cardIDInGame == null)
            return;
        BattleAction battleAction = new BattleAction("selectAttacker", cardIDInGame, Integer.toString(x), Integer.toString(y));
        Client.write(Request.makeBattleActionRequest(battleAction));
    }

    public void moveOrAttack(int x, int y) {
        if (Match.getCurrentMatch().getCell(x, y).isEmpty())
            moveTo(x, y);
        else
            attack(x, y);
    }

    void moveTo(int x, int y) {
        BattleAction battleAction = new BattleAction("moveTo", Integer.toString(x), Integer.toString(y));
        Client.write(Request.makeBattleActionRequest(battleAction));
    }

    void attack(int x, int y) {
        BattleAction battleAction = new BattleAction("attack", Integer.toString(x), Integer.toString(y));
        Client.write(Request.makeBattleActionRequest(battleAction));
    }

    public void useSpecialPower(int x, int y) {
        BattleAction battleAction = new BattleAction("useSpecialPower", Integer.toString(x), Integer.toString(y));
        Client.write(Request.makeBattleActionRequest(battleAction));
    }

    public void insertCardIn(String name, int x, int y) {
        BattleAction battleAction = new BattleAction("insertCardIn", name, Integer.toString(x), Integer.toString(y));
        Client.write(Request.makeBattleActionRequest(battleAction));
    }

    public void deselect() {
        BattleAction battleAction = new BattleAction("deselect");
        Client.write(Request.makeBattleActionRequest(battleAction));
    }

    public void endTurn() {
        BattleAction battleAction = new BattleAction("endTurn");
        Client.write(Request.makeBattleActionRequest(battleAction));
    }
}
