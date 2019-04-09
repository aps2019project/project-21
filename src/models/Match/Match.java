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

    public void selectCard(String  cardID){}

    public void moveCard(int x, int y){}

    public void attack(String opponentCardID){}

    public void attackCombo(String  opponentCardID, int[] myCardIDs){}

    public void useSpecialPower(int x, int y){}

    public void insertCard(String cardName, int x, int y){}

    public void swapTurn(){}

    public void selectCollectable(int collectableID){}

    public void showItemInfo(){}

    public void useItem(int x, int y) {}

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

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public Hand getPlayerOneHand() {
        return playerOneHand;
    }

    public void setPlayerOneHand(Hand playerOneHand) {
        this.playerOneHand = playerOneHand;
    }

    public Hand getPlayerTwoHand() {
        return playerTwoHand;
    }

    public void setPlayerTwoHand(Hand playerTwoHand) {
        this.playerTwoHand = playerTwoHand;
    }

    public PlayerMatchInfo getPlayerOneInfo() {
        return playerOneInfo;
    }

    public void setPlayerOneInfo(PlayerMatchInfo playerOneInfo) {
        this.playerOneInfo = playerOneInfo;
    }

    public PlayerMatchInfo getPlayerTwoInfo() {
        return playerTwoInfo;
    }

    public void setPlayerTwoInfo(PlayerMatchInfo playerTwoInfo) {
        this.playerTwoInfo = playerTwoInfo;
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }

    public void setBattlefield(Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GoalMode getGoalMode() {
        return goalMode;
    }

    public void setGoalMode(GoalMode goalMode) {
        this.goalMode = goalMode;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Time getGameTime() {
        return gameTime;
    }

    public void setGameTime(Time gameTime) {
        this.gameTime = gameTime;
    }

    public boolean isPlayerOneWinner() {
        return isPlayerOneWinner;
    }

    public void setPlayerOneWinner(boolean playerOneWinner) {
        isPlayerOneWinner = playerOneWinner;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public boolean isDoesAnyCardSelected() {
        return doesAnyCardSelected;
    }

    public void setDoesAnyCardSelected(boolean doesAnyCardSelected) {
        this.doesAnyCardSelected = doesAnyCardSelected;
    }

    public List<Collectable> getCollectablesPlayerOne() {
        return collectablesPlayerOne;
    }

    public void setCollectablesPlayerOne(List<Collectable> collectablesPlayerOne) {
        this.collectablesPlayerOne = collectablesPlayerOne;
    }

    public List<Collectable> getCollectablesPlayerTwo() {
        return collectablesPlayerTwo;
    }

    public void setCollectablesPlayerTwo(List<Collectable> collectablesPlayerTwo) {
        this.collectablesPlayerTwo = collectablesPlayerTwo;
    }

    public Collectable getSelectedCollectable() {
        return selectedCollectable;
    }

    public void setSelectedCollectable(Collectable selectedCollectable) {
        this.selectedCollectable = selectedCollectable;
    }

    public Graveyard getPlayerOneGraveyard() {
        return playerOneGraveyard;
    }

    public void setPlayerOneGraveyard(Graveyard playerOneGraveyard) {
        this.playerOneGraveyard = playerOneGraveyard;
    }

    public Graveyard getPlayerTwoGraveyard() {
        return playerTwoGraveyard;
    }

    public void setPlayerTwoGraveyard(Graveyard playerTwoGraveyard) {
        this.playerTwoGraveyard = playerTwoGraveyard;
    }
}