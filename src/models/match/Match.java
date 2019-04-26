package models.match;

import controller.menus.Graveyard;
import models.card.Card;
import models.Hand;
import models.Item.Collectable;
import models.Player;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Match {
    private Player[] players = new Player[2];
    private Hand[] hands = new Hand[2];
    private List<List<Card>> groundCards = new ArrayList<>();
    private PlayerMatchInfo[] info = new PlayerMatchInfo[2];
    private Battlefield battlefield;
    private GameMode gameMode;
    private GoalMode goalMode;
    private GameType gameType;
    private int turn; // 0 for player1 and 1 for player 2
    private Time gameTime;
    private boolean isPlayerOneWinner;
    private Card selectedCard;
    private boolean doesAnyCardSelected;
    private List<Collectable>[] collectables;
    private Collectable selectedCollectable;
    private Graveyard[] graveyard = new Graveyard[2];

    Match(Player playerOne, Player playerTwo, GameMode gameMode, GameType gameType, GoalMode goalMode) {
        this.gameMode = gameMode;
        this.players[0] = playerOne;
        this.players[1] = playerTwo;
        this.battlefield = new Battlefield();
        this.turn = 1;
        List<Card> groundCards1 = new ArrayList<>();
        List<Card> groundCards0 = new ArrayList<>();
        this.groundCards.add(groundCards0);
        this.groundCards.add(groundCards1);
        this.gameType = gameType;
        this.goalMode = goalMode;
    }

    public void selectCard(String  cardID){
        Card card = new Card();
        for (int i = 0; i < groundCards.get(turn).size();i++){
            if (groundCards.get(turn).get(i).getCardIDInGame().equals(cardID))
                card = groundCards.get(turn).get(i);
        }
        if (card.getName() == null) {
            System.out.println("Invalid card name");
            return;
        }
        selectedCard = card;
        doesAnyCardSelected = true;
    }

    public void moveCard(int x, int y){
        if(!doesAnyCardSelected) {
            //error
            return;
        }
        boolean validTarget = true;
        if(Math.abs(selectedCard.getXCoordinate() - x) + Math.abs(selectedCard.getYCoordinate() - y) > 2)
            validTarget = false;
        //if(special) validTarget = true
        if(!battlefield.getCells()[x][y].isEmpty())
            validTarget = false;
        //if(pathisclosed) validTarget = false
        if(validTarget) {
            battlefield.getCells()[selectedCard.getXCoordinate()][selectedCard.getYCoordinate()].setEmpty(true);
            selectedCard.setXCoordinate(x);
            selectedCard.setYCoordinate(y);
            battlefield.getCells()[x][y].setEmpty(false);
            System.out.println(selectedCard.getCardIDInGame() + " moved to " + x + " " + y);
            return;
        }
        System.out.println("Invalid target");;
    }

    public void attack(String opponentCardID){}

    public void attackCombo(String  opponentCardID, int[] myCardIDs){}

    public void useSpecialPower(int x, int y){}

    public void insertCard(String cardName, int x, int y) {
        Card card = new Card();
        for (int i = 0; i < hands[turn].getCards().size();i++){
            if (hands[turn].getCards().get(i).getName().equals(cardName))
                card = hands[turn].getCards().get(i);
        }
        if (card.getName() == null) {
            System.out.println("Invalid card name");
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
        if (info[turn].getMana() < card.getManaCost()) {
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
        card.setXCoordinate(x);
        card.setYCoordinate(y);
        card.setCardIDInGame(players[turn].getUsername() + "_" + card.getName() + "_" + id);
        groundCards.get(turn).add(card);
        hands[turn].remove(card);
        battlefield.getCells()[x][y].setEmpty(false);
    }

    public void swapTurn(){
        turn = 1 - turn;
        //blah blah blah
    }

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
}