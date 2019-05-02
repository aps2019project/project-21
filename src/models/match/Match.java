package models.match;

import models.Item.Collectable;
import models.Player;
import models.card.Attacker;
import models.card.Card;
import view.ErrorMode;
import view.View;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Match {
    private static final int MOVE_RANGE = 2;
    private static Match currentMatch;
// functions should be handled with currentMatch

    private Player[] players = new Player[2];
    private Battlefield battlefield;
    private PlayerMatchInfo[] info = new PlayerMatchInfo[2];
    private GameMode gameMode;
    private GoalMode goalMode;
    private GameType gameType;
    private Time gameTime;
    private int turn;  // 0 for player1 and 1 for player2
    private Attacker selectedAttacker;
    private Collectable selectedCollectable;
    private View view = View.getInstance();

    public static Match getCurrentMatch(){
        return currentMatch;
    }

    public PlayerMatchInfo[] getPlayersMatchInfo(){
        return this.info;
    }

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

    public void setSelectedAttacker(Attacker attacker){
        selectedAttacker = attacker;
    }

    public int getTurn(){
        return turn;
    }

    public Collectable getSelectedCollectable(){
        return selectedCollectable;
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
        if (target == null)
            return;
        if (!isAttackTargetValid(target))
            return;
        if (!selectedAttacker.hasSpecialPower())
            return;
        selectedAttacker.castSpecialPower(this, target);
    }

    private boolean isAttackTargetValid(Cell target) {
        //  TODO
        return true;
    }

    public void insertCard(String cardName, int x, int y) {
        Card card = info[turn].getHand().getCard(cardName);
        if (card == null) {
            System.out.println("invalid card name");
            return;
        }
        Cell target = getCell(x, y);
        if (target == null || !target.isEmpty())
            return;

        boolean isNearOtherAttackers = false;
        for (Attacker attacker : info[turn].getGroundedAttackers())
            if (Cell.getManhattanDistance(attacker.getCurrentCell(), target) < 2) {
                isNearOtherAttackers = true;
                break;
            }
        if (!isNearOtherAttackers)
            return;
        //if special power then hasTarget = true
        if (info[turn].getMp() < card.getManaCost()) {
            System.out.println("You don't have enough mana");
            return;
        }
        //full???
        //spell va jame'e hadaf
        int id = 1;
        for (int i = 0; i < info[turn].getGroundedAttackers().size(); i++) {
            if (info[turn].getGroundedAttackers().get(i).getName().equals(cardName))
                id++;
        }
//        card.setXCoordinate(x);
//        card.setYCoordinate(y);
        card.setCardIDInGame(players[turn].getUsername() + "_" + card.getName() + "_" + id);
        info[turn].getGroundedAttackers().add((Attacker) card);
        info[turn].getHand().remove(card);
        //  handle cells in both cell and attacker
        //  handle spell case
    }

    public void swapTurn() {
        turn = 1 - turn;
        //blah blah blah
        //  heh heh heh :| :(
    }

    public void selectCollectable(int collectableID) {

    }

    public void useItem(int x, int y) {
    }

    public void play() {

    }

    private boolean isMatchEnded() {
        //
        return false;
    }

    public void endMatch() {
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