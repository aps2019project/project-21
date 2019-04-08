package models.Match;

import models.Player;

import java.sql.Time;

public class Match {
    private Player playerOne;
    private Player playerTwo;
    private PlayerMatchInfo playerOneInfo = new PlayerMatchInfo();
    private PlayerMatchInfo playerTwoInfo = new PlayerMatchInfo();
    private Battlefield battlefield;
    private GameMode gameMode;
    private GoalMode goalMode;
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