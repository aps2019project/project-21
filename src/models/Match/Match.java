package models.Match;

import models.Card.Card;
import models.Hand;
import models.Player;

import java.sql.Time;

public class Match {
    private Player playerOne;
    private Player playerTwo;
    private Hand playerOneHand;
    private Hand playerTwoHand;
    private PlayerMatchInfo playerOneInfo = new PlayerMatchInfo();
    private PlayerMatchInfo playerTwoInfo = new PlayerMatchInfo();
    private Battlefield battlefield;
    private GameMode gameMode;
    private GoalMode goalMode;
    private GameType gameType;
    private int turn; // 0 or 1
    private Time gameTime;
    private boolean isPlayerOneWinner;

    Match(Player playerOne, Player playerTwo, GameMode gameMode) {
        this.gameMode = gameMode;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.battlefield = new Battlefield();
        this.turn = 1;
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
}