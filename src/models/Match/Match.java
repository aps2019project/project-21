package models.Match;

import models.Player;

import java.sql.Time;

public class Match {
    private Player playerOne;
    private Player playerTwo;
    private Battlefield battlefield;
    private GameMode gameMode;
    private int turn;
    private Time gameTime;
    private boolean isPlayerOneWinner;

    Match(Player playerOne, Player playerTwo, GameMode gameMode) {
        this.gameMode = gameMode;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.battlefield = new Battlefield();
        this.turn = 1;
    }

}