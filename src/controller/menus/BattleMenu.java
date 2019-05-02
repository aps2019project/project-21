package controller.menus;

import controller.request.BattleMenuRequest;
import models.Item.Collectable;
import models.card.Attacker;
import models.card.Card;
import models.match.Match;
import models.match.PlayerMatchInfo;
import view.View;

public class BattleMenu extends Menu {
    private static BattleMenu instance = new BattleMenu();

    static Menu getInstance() {
        return instance;
    }

    private BattleMenu() {

    }

    public void main() {
        showMenu();

        request = new BattleMenuRequest();

        request.getNewCommand();

        request.extractType();

        switch (request.getType()) {
            //  add cases
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
                endturn();
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
        }
    }

    private void showGameInfo() {
        view.showGameInfo();
    }

    private void showMyMinions() {
        Match match = Match.getCurrentMatch();
        for (Attacker attacker : match.getPlayersMatchInfo()[0].getGroundedAttackers()) {
            view.showMyMinions(attacker);
        }
    }

    private void showOpponentMinions() {
        Match match = Match.getCurrentMatch();
        for (Attacker attacker : match.getPlayersMatchInfo()[1].getGroundedAttackers()) {
            view.showMyMinions(attacker);
        }
    }

    private void showCardInfo() {
        Card card = Card.getCardByID(request.getCommandArguments().get(0));
        view.showCardInfo(card);
    }

    private void select() {
        Attacker attacker = Attacker.getAttackerById(request.getCommandArguments().get(0));
        Match.getCurrentMatch().setSelectedAttacker(attacker);
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
        System.out.println("next card:\n");
        Card nextCard = currentPlayer.getDeck().getCards().get(0);
        view.showCardInfo(nextCard);
    }

    private void insertCardIn() {
        String name = request.getCommandArguments().get(0);
        int x = Integer.parseInt(request.getCommandArguments().get(1));
        int y = Integer.parseInt(request.getCommandArguments().get(2));
        Match.getCurrentMatch().insertCard(name, x, y);
    }

    private void endturn() {
        Match.getCurrentMatch().swapTurn();
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
        Match.getCurrentMatch().useItem(x, y);
    }

    private void showNextCard() {
        PlayerMatchInfo info = Match.getCurrentMatch().getPlayersMatchInfo()[Match.getCurrentMatch().getTurn()];
        Card card = info.getDeck().getCards().get(0);
        view.showCardInfo(card);
    }

    private void enterGraveyard() {
        MenuManager.getInstance().changeMenu(MenuType.GRAVEYARD_MENU);
    }

    private void endGame() {
        Match.getCurrentMatch().endMatch();
    }

    protected void showMenu() {
        System.out.println("----Game----\n" +
                "options:\n" +
                "1 - Game info\n" +
                "2 - Show my minions\n" +
                "3 - Show opponent minions\n" +
                "4 - Show card info [card id]\n" +
                "5 - Select [card id]\n" +
                "6 - Move to ([x], [y])\n" +
                "7 - Attack [opponent card id]\n" +
                "8 - Attack combo [opponent card id] [my card id] [my card id] [...]\n" +
                "9 - Use special power (x; y)\n" +
                "10 - Show hand\n" +
                "11 - Insert [card name] in (x; y)\n" +
                "12 - Insert [card name] in (x; y)\n" +
                "13 - End turn\n" +
                "14 - Show collectables\n" +
                "15 - Select [collectable id]\n" +
                "16 - Show info\n" +
                "17 - Use [location x; y]\n" +
                "18 - Show Next Card\n" +
                "19 - Enter graveyard\n" +
                "20 - Help\n" +
                "21 - End Game\n" +
                "22 = Exit\n" +
                "23 - Show menu\n");
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}

