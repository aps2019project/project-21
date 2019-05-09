package controller.menus;

import controller.request.BattleMenuRequest;
import models.Item.Collectable;
import models.card.Attacker;
import models.card.Card;
import models.match.Match;
import models.match.PlayerMatchInfo;

public class BattleMenu extends Menu {
    private static BattleMenu instance = new BattleMenu();

    static Menu getInstance() {
        return instance;
    }

    private BattleMenu() {

    }

    public void main() {
        if (showMenu) {
            showMenu();
            showMenu = false;
        }

        request = new BattleMenuRequest();

        request.getNewCommand();

        request.extractType();

        switch (request.getType()) {
            case HELP:
                showMenu();
                break;
            case GAME_INFO:
                showGameInfo();
                break;
            case EXIT:
                exit();
                break;
            case SHOW_MY_MINIONS:
                showMyMinions();
                break;
            case SHOW_OPPONENT_MINIONS:
                showOpponentMinions();
                break;
            case SHOW_CARD_INFO:
                showCardInfo();
                break;
            case SELECT:
                select();
                break;
            case MOVE_TO:
                moveTo();
                break;
            case ATTACK:
                attack();
                break;
            case ATTACK_COMBO:
                attackCombo();
                break;
            case USE_SPECIAL_POWER:
                useSpecialPower();
                break;
            case SHOW_HAND:
                showHand();
                break;
            case INSERT_IN:
                insertCardIn();
                break;
            case END_TURN:
                endTurn();
                break;
            case SHOW_COLLECTABLES:
                showCollectables();
                break;
            case SHOW_INFO:
                showInfo();
                break;
            case USE:
                use();
                break;
            case SHOW_NEXT_CARD:
                showNextCard();
                break;
            case ENTER_GRAVEYARD:
                enterGraveyard();
                break;
            case END_GAME:
                endGame();
                break;
            case SHOW_MENU:
                showMenu();
                break;
            case INVALID:
                invalidCommand();
                break;
            case BACK:
                back();
                break;
            case SHOW_BATTLEFIELD:
                showBattlefield();
                break;
            case KILL:
                kill();
                break;
            case MP:
                showMP();
                break;
            case SHOW_SELECTED:
                showSelected();
                break;
            case SHOW_TURN:
                showTurn();
                break;
            case UNSELECT:
                unselect();
                break;
        }
    }

    private void showGameInfo() {
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
        Card card = Card.getCardByID(request.getCommandArguments().get(0));
        if (card == null)
            return;
        view.showCardInfo(card);
    }

    private void select() {
        Match.getCurrentMatch().selectAttacker(request.getCommandArguments().get(0));
    }

    private void moveTo() {
        int x = Integer.parseInt(request.getCommandArguments().get(0));
        int y = Integer.parseInt(request.getCommandArguments().get(1));
        Match.getCurrentMatch().moveCard(x, y);
    }

    private void attack() {
        Match.getCurrentMatch().attack(request.getCommandArguments().get(0));
    }

    private void attackCombo() {
        String opponentMinion = request.getCommandArguments().get(0);
        int[] myMinion = new int[request.getCommandArguments().size() - 1];
        for (int i = 1; i < request.getCommandArguments().size(); i++) {
            myMinion[i - 1] = Integer.parseInt(request.getCommandArguments().get(i));
        }
        Match.getCurrentMatch().attackCombo(opponentMinion, myMinion);
    }

    private void useSpecialPower() {
        int x = Integer.parseInt(request.getCommandArguments().get(0));
        int y = Integer.parseInt(request.getCommandArguments().get(1));
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

    private void insertCardIn() {
        String name = request.getCommandArguments().get(0);
        int x = Integer.parseInt(request.getCommandArguments().get(1));
        int y = Integer.parseInt(request.getCommandArguments().get(2));
        Match.getCurrentMatch().insertCard(name, x, y);
    }

    private void endTurn() {
        Match.getCurrentMatch().endTurn();
    }

    private void showCollectables() {
        PlayerMatchInfo info = Match.getCurrentMatch().getPlayersMatchInfo()[Match.getCurrentMatch().getTurn()];
        for (Collectable collectable : info.getAchievedCollectables()) {
            view.showCollectables(collectable);
        }
    }

    private void showInfo() {
        Collectable selected = Match.getCurrentMatch().getSelectedCollectable();
        view.showCollectables(selected);
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

    private void endGame() {
        Match.getCurrentMatch().endMatch();
    }

    protected void showMenu() {
        view.showMenu("Battle");
    }

    private void back() {
        MenuManager.getInstance().gotoMainMenu();
    }

    private void showBattlefield() {
        Match.getCurrentMatch().showBattleField();
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

    private void unselect() {
        Match.getCurrentMatch().unselect();
    }
}

