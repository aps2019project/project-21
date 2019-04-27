package models.match;

import models.card.Attacker;
import models.card.Card;
import models.Player;
import view.ErrorMode;
import view.View;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Match {
    private static final int MOVE_RANGE = 2;

    private Player[] players = new Player[2];
    private Battlefield battlefield;
    private PlayerMatchInfo[] info = new PlayerMatchInfo[2];
    private GameMode gameMode;
    private GoalMode goalMode;
    private GameType gameType;
    private Time gameTime;
    private int turn;  // 0 for player1 and 1 for player2
    private Attacker selectedAttacker;
    private View view = View.getInstance();

    public Match(Player playerOne, Player playerTwo, GameMode gameMode, GameType gameType, GoalMode goalMode) {
        this.players[0] = playerOne;
        this.players[1] = playerTwo;
        this.battlefield = new Battlefield();
        this.turn = 0;
        this.info[0] = new PlayerMatchInfo(playerOne);
        this.info[1] = new PlayerMatchInfo(playerTwo);
        this.gameMode = gameMode;
        this.gameType = gameType;
        this.goalMode = goalMode;
    }

    public void selectCard(String cardID) {
        for (Attacker attacker : info[turn].getGroundedAttackers())
            if (attacker.getCardIDInGame().equals(cardID)) {
                selectedAttacker = attacker;
                return;
            }
        view.printError(ErrorMode.CARD_ID_INVALID);
    }

    public void moveCard(int x, int y) {
        if (!isAnyCardSelected()) {
            view.printError(ErrorMode.NO_CARD_IS_SELECTED);
            return;
        }
        Cell target = getCell(x, y);
        if (target == null)
            return;
        if (!isMoveTargetValid(target))
            return;
        //  actually moving the attacker
        selectedAttacker.getCurrentCell().setEmpty();
        selectedAttacker.setCurrentCell(target);
        target.setCurrentAttacker(selectedAttacker);
        System.out.println(selectedAttacker.getCardIDInGame() + " moved to " + x + " " + y);
    }

    private boolean isMoveTargetValid(Cell target) {
        if (Cell.getManhattanDistance(selectedAttacker.getCurrentCell(), target) > MOVE_RANGE)
            return false;
        //if(special) validTarget = true
        if (!target.isEmpty())
            return false;
        //if(pathisclosed) validTarget = false
        return true;
        //  error invalid target
    }

    public void attack(String opponentCardID) {
    }

    public void attackCombo(String opponentCardID, int[] myCardIDs) {
    }

    public void useSpecialPower(int x, int y) {
        if (!isAnyCardSelected())
            return;
        Cell target = getCell(x, y);
        if(target == null)
            return;
        if(!isAttackTargetValid(target))
            return;
        if (!selectedAttacker.hasSpecialPower())
            return;
        selectedAttacker.castSpecialPower(this, target);
    }

    private boolean isAttackTargetValid(Cell target){
        //  TODO
        return true;
    }

    public void insertCard(String cardName, int x, int y) {
        Card Card = new Card();
        for (int i = 0; i < hands[turn].getCards().size(); i++) {
            if (hands[turn].getCards().get(i).getName().equals(cardName))
                Card = hands[turn].getCards().get(i);
        }
        if (Card.getName() == null) {
            System.out.println("Invalid Card name");
            return;
        }
        boolean validTarget = false;
        for (int i = 0; i < groundCards.get(turn).size() && !validTarget; i++) {
            if (Math.abs(groundCards.get(turn).get(i).getXCoordinate() - x) < 2) {
                if (Math.abs(groundCards.get(turn).get(i).getYCoordinate() - y) < 2)
                    validTarget = true;
            }
        }
        //if special power then hastarget = true
        if (!validTarget) {
            System.out.println("Invalid Target");
            return;
        }
        if (info[turn].getMana() < Card.getManaCost()) {
            System.out.println("You don't have enough mana");
            return;
        }
        //full???
        //spell va jame'e hadaf
        int id = 1;
        for (int i = 0; i < groundCards.get(turn).size(); i++) {
            if (groundCards.get(turn).get(i).getName().equals(cardName))
                id++;
        }
        Card.setXCoordinate(x);
        Card.setYCoordinate(y);
        Card.setCardIDInGame(players[turn].getUsername() + "_" + Card.getName() + "_" + id);
        groundCards.get(turn).add(Card);
        hands[turn].remove(Card);
        battlefield.getCells()[x][y].setEmpty(false);
    }

    public void swapTurn() {
        turn = 1 - turn;
        //blah blah blah
        //  heh heh heh :| :(
    }

    public void selectCollectable(int collectableID) {
    }

    public void showItemInfo() {
    }

    public void useItem(int x, int y) {
    }

    public void enterGraveyard() {
    }

    public void play() {

    }

    private boolean isMatchEnded() {
        //
        return false;
    }

    private void endMatch() {
        saveMatchResults();
        showMatchResults();
    }

    private void saveMatchResults() {
        //
    }

    private void showMatchResults() {
        //
    }

    //fxchgjvhkj

    private boolean isAnyCardSelected() {
        return selectedAttacker != null;
    }

    public Card getSelectedAttacker() {
        return selectedAttacker;
    }

    public Cell getCell(int x, int y) {
        return battlefield.getCell(x, y);
    }

    public List<Cell> getOppCells() {
        List<Cell> cells = new ArrayList<>();
        //  TODO
        return null;
    }

    public void showGameInfo() {
        System.out.println("-----Game Info:");
        for (int i = 0; i < 2; i++)
            System.out.println(players[i].getUsername() + "  mp: " + info[i].getMp());
        if (goalMode == GoalMode.KILL_HERO) {
            for (int i = 0; i < 2; i++)
                System.out.println((i + 1) + "th Hero hp: " + info[i].getDeck().getHero().getHP());
        } else if (goalMode == GoalMode.HOLD_FLAG) {
            for (int i = 0; i < 2; i++)
                System.out.println();
            //  TODO
        } else {
            //  TODO
        }
    }
}