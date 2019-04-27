package models.match;

import models.card.Attacker;
import models.card.Card;
import models.Player;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Match {
    private Player[] players = new Player[2];
    private Battlefield battlefield;
    private PlayerMatchInfo[] info = new PlayerMatchInfo[2];
    private GameMode gameMode;
    private GoalMode goalMode;
    private GameType gameType;
    private Time gameTime;
    private int turn;  // 0 for player1 and 1 for player 2
    private Card selectedCard;

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
        Card Card = new Card();
        for (int i = 0; i < groundCards.get(turn).size(); i++) {
            if (groundCards.get(turn).get(i).getCardIDInGame().equals(cardID))
                Card = groundCards.get(turn).get(i);
        }
        if (Card.getName() == null) {
            System.out.println("Invalid Card name");
            return;
        }
        selectedCard = Card;
    }

    public void moveCard(int x, int y) {
        if (!isAnyCardSelected()) {
            //error
            return;
        }
        boolean validTarget = true;
        if (Math.abs(selectedCard.getXCoordinate() - x) + Math.abs(selectedCard.getYCoordinate() - y) > 2)
            validTarget = false;
        //if(special) validTarget = true
        if (!battlefield.getCells()[x][y].isEmpty())
            validTarget = false;
        //if(pathisclosed) validTarget = false
        if (validTarget) {
            battlefield.getCells()[selectedCard.getXCoordinate()][selectedCard.getYCoordinate()].setEmpty(true);
            selectedCard.setXCoordinate(x);
            selectedCard.setYCoordinate(y);
            battlefield.getCells()[x][y].setEmpty(false);
            System.out.println(selectedCard.getCardIDInGame() + " moved to " + x + " " + y);
            return;
        }
        System.out.println("Invalid target");
        ;
    }

    public void attack(String opponentCardID) {
    }

    public void attackCombo(String opponentCardID, int[] myCardIDs) {
    }

    public void useSpecialPower(int x, int y) {
        if (!isAnyCardSelected())
            return;
        Attacker attacker = (Attacker) selectedCard;
        Cell target = getCell(x, y);
        if (!attacker.hasSpecialPower())
            return;
        attacker.castSpecialPower(this, target);
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

    private boolean isAnyCardSelected() {
        return selectedCard != null;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public Cell getCell(int x, int y) {
        return battlefield.getCell(x, y);
    }

    public List<Cell> getOppCells(){
        List<Cell> cells = new ArrayList<>();
        //  TODO
        return null;
    }
}