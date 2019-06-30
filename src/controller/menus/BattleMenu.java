package controller.menus;

import models.Item.Collectable;
import models.card.Attacker;
import models.card.Card;
import models.match.Match;
import models.match.PlayerMatchInfo;

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

    private void showMyMinions() {
        Match match = Match.getCurrentMatch();
        for (Attacker attacker : match.getPlayersMatchInfo()[match.getTurn()].getGroundedAttackers())
            view.showAttacker(attacker);
    }

    private void showOpponentMinions() {
        Match match = Match.getCurrentMatch();
        for (Attacker attacker : match.getPlayersMatchInfo()[1 - match.getTurn()].getGroundedAttackers())
            view.showAttacker(attacker);
    }

    private void showCardInfo() {
        Card card = Card.getCardByIDInGame(request.getCommandArguments().get(0));
        if (card == null)
            return;
        view.showCardInfo(card);
    }

    private void select() {
        Match.getCurrentMatch().select(request.getCommandArguments().get(0));
    }

    public void useSpell(String spell, int x, int y) {
        Match.getCurrentMatch().selectSpell(spell);
        Match.getCurrentMatch().useSpell(x, y);
    }

    public boolean selectAttacker(Attacker attacker) {
        if (attacker == null)
            return false;
        return Match.getCurrentMatch().selectAttacker(attacker.getCardIDInGame());
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

    private void attackCombo() {
        String opponentMinion = request.getCommandArguments().get(0);
        String[] myMinions = new String[request.getCommandArguments().size() - 1];
        for (int i = 1; i < request.getCommandArguments().size(); i++) {
            myMinions[i - 1] = request.getCommandArguments().get(i);
        }
        Match.getCurrentMatch().attackCombo(opponentMinion, myMinions);
    }

    public void useSpecialPower(int x, int y) {
        Match.getCurrentMatch().selectAttacker(Match.getCurrentMatch().getPlayersMatchInfo()[0].getHero().getCardIDInGame());
        Match.getCurrentMatch().useSpecialPower(x, y);
    }

    private void showHand() {
        Match match = Match.getCurrentMatch();
        PlayerMatchInfo currentPlayer = match.getPlayersMatchInfo()[match.getTurn()];
        for (Card card : currentPlayer.getHand().getCards()) {
            view.showCardInfo(card);
        }
        System.out.println("next card:");
        Card nextCard = currentPlayer.getDeck().getCards().get(0);
        view.showCardInfo(nextCard);
    }

    public void insertCardIn(String name, int x, int y) {
        Match.getCurrentMatch().insertCard(name, x, y);
    }

    private void endTurn() {
        Match.getCurrentMatch().endTurn();
    }

    private void showCollectables() {
        PlayerMatchInfo info = Match.getCurrentMatch().getPlayersMatchInfo()[Match.getCurrentMatch().getTurn()];
        for (Collectable collectable : info.getAchievedCollectables()) {
            view.showCollectable(collectable);
        }
    }

    private void showInfo() {
        Collectable selected = Match.getCurrentMatch().getSelectedCollectable();
        view.showCollectable(selected);
    }

    private void use() {
        int x = Integer.parseInt(request.getCommandArguments().get(0));
        int y = Integer.parseInt(request.getCommandArguments().get(1));
        Match.getCurrentMatch().useCollectable(x, y);
    }

    private void showNextCard() {
        PlayerMatchInfo info = Match.getCurrentMatch().getPlayersMatchInfo()[Match.getCurrentMatch().getTurn()];
        Card card = info.getDeck().getCards().get(0);
        view.showCardInfo(card);
    }

    private void enterGraveyard() {
        MenuManager.getInstance().gotoGraveyard();
    }

    protected void showMenu() {
        view.showMenu("Battle");
    }

    private void back() {
        MenuManager.getInstance().gotoMainMenu();
    }

    private void kill() {
        Match.getCurrentMatch().kill();
    }

    private void showMP() {
        Match.getCurrentMatch().showMP();
    }

    private void showSelected() {
        Match.getCurrentMatch().showSelectedCard();
    }

    private void showTurn() {
        Match.getCurrentMatch().showTurn();
    }

    private void unSelect() {
        Match.getCurrentMatch().unSelect();
    }

    private void withdraw() {
        Match.getCurrentMatch().withdraw();
    }

    private void useSpell() {
        int x = Integer.parseInt(request.getCommandArguments().get(0));
        int y = Integer.parseInt(request.getCommandArguments().get(1));
        Match.getCurrentMatch().useSpell(x, y);
    }


}

