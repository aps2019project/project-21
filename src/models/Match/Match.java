package models.Match;

import controller.menu.Graveyard;
import models.Card.Card;
import models.Hand;
import models.Item.Collectable;
import models.Item.Item;
import models.Player;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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
    private Card selectedCard;
    private boolean doesAnyCardSelected;
    private List<Collectable> collectablesPlayerOne = new ArrayList<>();
    private List<Collectable> collectablesPlayerTwo = new ArrayList<>();
    private Collectable selectedCollectable;
    private Graveyard playerOneGraveyard;
    private Graveyard playerTwoGraveyard;

    Match(Player playerOne, Player playerTwo, GameMode gameMode) {
        this.gameMode = gameMode;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.battlefield = new Battlefield();
        this.turn = 1;
    }

    public void showGameInfo(){}

    public void showMyMinions(){}

    public void showOpponentMinions(){}

    public void showCardInfo(String  cardID){}

    public void selectCard(String  cardID){}

    public void moveCard(int x, int y){}

    public void attack(String opponentCardID){}

    public void attackCombo(String  opponentCardID, int[] myCardIDs){}

    public void useSpecialPower(int x, int y){}

    public void showHand(){}

    public void insertCard(String cardName, int x, int y){}

    public void swapTurn(){}

    public void showCollectables(){}

    public void selectCollectable(int collectableID){}

    public void showItemInfo(){}

    public void useItem(int x, int y) {}

    public void showNextCard(){}

    public void enterGraveyard(){}

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