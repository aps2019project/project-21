package Models.Match;

import Models.Player;

public class Match {
    private Player playerOne;
    private Player playerTwo;
    private Battlefield battlefield;
    private GameMode gameMode;
    private int turn;

    Match(Player playerOne, Player playerTwo, GameMode gameMode) {
        this.gameMode = gameMode;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.battlefield = new Battlefield();
        this.turn = 1;
    }

}