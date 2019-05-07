package models.match;

import jdk.jshell.execution.LoaderDelegate;
import models.Item.Collectable;
import models.Item.Flag;
import models.Player;
import models.card.Attacker;
import models.card.Card;
import models.card.Spell;
import view.ErrorMode;
import view.View;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

public class Match {
    private static final int MOVE_RANGE = 2;
    private static Match currentMatch;

    private Player[] players = new Player[2];
    private Battlefield battlefield;
    private PlayerMatchInfo[] info = new PlayerMatchInfo[2];
    private GameMode gameMode;
    private GoalMode goalMode;
    private GameType gameType;
    private int flagCount; //  only for gather flag
    private List<Flag> flags = new ArrayList<>();
    private Flag flag;
    private LocalDateTime gameTime = LocalDateTime.now();
    private int turn;  //  0 for player1 and 1 for player2
    private Card selectedCard;
    private Collectable selectedCollectable;
    private View view = View.getInstance();

    public static Match getCurrentMatch() {
        return currentMatch;
    }

    public PlayerMatchInfo[] getPlayersMatchInfo() {
        return this.info;
    }

    public Match(Player playerOne, Player playerTwo, GameMode gameMode, GameType gameType, GoalMode goalMode, int flagCount) {
        this.players[0] = playerOne;
        this.players[1] = playerTwo;
        this.battlefield = new Battlefield();
        this.turn = 0;
        this.info[0] = new PlayerMatchInfo(playerOne);
        this.info[1] = new PlayerMatchInfo(playerTwo);
        this.gameMode = gameMode;
        this.gameType = gameType;
        this.goalMode = goalMode;
        this.flagCount = flagCount;
        initiateMatch();
    }

    public void selectAttacker(String attackerID) {
        for (Attacker attacker : info[turn].getGroundedAttackers())
            if (attacker.getCardIDInGame().equals(attackerID)) {
                selectedCard = attacker;
                return;
            }
        view.printError(ErrorMode.CARD_ID_INVALID);
    }

    public void selectCard(String cardID) {
        //  TODO
    }

    public void setSelectedCard(Card card) {
        if (card == null)
            return;
        selectedCard = card;
    }

    public int getTurn() {
        return turn;
    }

    public Collectable getSelectedCollectable() {
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
        if (!isSelectedCardAttacker())
            return;
        Attacker attacker = (Attacker) selectedCard;
        //  actually moving the attacker:
        attacker.getCurrentCell().setEmpty();
        attacker.setCurrentCell(target);
        target.setCurrentAttacker(attacker);
        System.out.println(selectedCard.getCardIDInGame() + " moved to " + x + " " + y);
        //  TODO : if he goes on a collectable
    }

    private boolean isMoveTargetValid(Cell target) {
        if (!isSelectedCardAttacker())
            return false;
        Attacker attacker = (Attacker) selectedCard;
        if (Cell.getManhattanDistance(attacker.getCurrentCell(), target) > MOVE_RANGE)
            return false;
        //if(special) validTarget = true
        if (!target.isEmpty())
            return false;
        //if(pathisclosed) validTarget = false
        return true;
        //  error invalid target
    }

    public void attack(String oppID) {
        if (!isAnyCardSelected()) {
            view.printError(ErrorMode.NO_CARD_IS_SELECTED);
            return;
        }
        Attacker target = getGroundedOppAttacker(oppID);
        if (target == null) {
            view.printError(ErrorMode.INVALID_CARD_ID);
            return;
        }
        if (!(selectedCard instanceof Attacker)) {
            view.printError(ErrorMode.NO_CARD_IS_SELECTED);
            return;
        }
        Attacker mine = (Attacker) selectedCard;
        if (!mine.canAttack()) {
            System.out.println("card with id : " + selectedCard.getCardIDInGame() + " can't attack.");
            return;
        }
        if (!isInRangeOfAttack(target)) {
            view.printError(ErrorMode.UNAVAILABLE_FOR_ATTACK);
            return;
        }
        target.decreaseHP(mine.getAP());  //  TODO check if he is dead
        counterAttack(target);
        mine.setCannotAttack();
        mine.setCannotMove();
        //  TODO: cast OnAttack spells (for minions and one hero)
    }

    public boolean isInRangeOfAttack(Attacker target) {
        if (Cell.getManhattanDistance(target.getCurrentCell(),
                ((Attacker) selectedCard).getCurrentCell()) > ((Attacker) selectedCard).getAttackRange())
            return false;
        return true;
    }

    public Attacker getGroundedOppAttacker(String oppID) {
        for (Attacker attacker : info[1 - turn].getGroundedAttackers())
            if (attacker.getCardIDInGame().equals(oppID))
                return attacker;
        return null;
    }

    public void counterAttack(Attacker opp) {
        if (opp == null || getSelectedAttacker() == null)
            return;
        getSelectedAttacker().decreaseHP(opp.getAP());
        //  TODO: cast OnDefend spells
    }

    public void attackCombo(String opponentCardID, int[] myCardIDs) {
        //  TODO:  check that they can do combo attack
    }

    public void useSpell(int x, int y) {
        Cell target = getCell(x, y);
        if (target == null)
            return;
        if (!isAttackTargetValid(target))
            return;
        if (!isSelectedCardSpell())
            return;
        Spell spell = (Spell) selectedCard;
        spell.castSpell(this, getThisTurnsPlayer(), target);
    }

    public void useCollectable(int x, int y) {
        Cell target = getCell(x, y);
        if (target == null)
            return;
        if (!isAttackTargetValid(target))
            return;
        if (!isAnyCardSelected())
            return;
        Collectable collectable = (Collectable) selectedCard;
        collectable.castItem(this, getThisTurnsPlayer(), target);
    }

    public void useSpecialPower(int x, int y) {
        if (!isAnyCardSelected())
            return;
        if (!isSelectedCardAttacker())
            return;
        Cell target = getCell(x, y);
        Attacker attacker = (Attacker) selectedCard;
        if (target == null)
            return;
        if (!isAttackTargetValid(target))
            return;
        if (!attacker.hasSpecialPower())
            return;
        attacker.castSpecialPower(this, getThisTurnsPlayer(), target);
    }

    public void applyEffects() {
        // TODO: heroes, minons, infos
    }

    private boolean isAttackTargetValid(Cell target) {
        //  TODO
        return true;
    }

    public void insertCard(String cardName, int x, int y) {
        // TODO: if minion then cast OnSpawn spell
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
        card.setCardIDInGame(getThisTurnsPlayer().getUsername() + "_" + card.getName() + "_" + id);
        info[turn].getGroundedAttackers().add((Attacker) card);
        info[turn].getHand().remove(card);
        //  handle cells in both cell and attacker
        //  handle spell case
    }

    public void swapTurn() {
        turn = 1 - turn;
        prepareNextRound(turn);
    }

    private void prepareNextRound(int turn) {
        //  TODO: cast passive effects (minions, usables)
    }

    public void selectCollectable(int collectableID) {

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
        return selectedCard != null;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public Cell getCell(int x, int y) {
        return battlefield.getCell(x, y);
    }

    public List<Cell> getOppCells() {
        List<Cell> cells = new ArrayList<>();
        //  TODO
        return null;
    }

    public PlayerMatchInfo getInfo(Player player) {
        return null;
    }

    private boolean isSelectedCardAttacker() {
        // TODO
        return true;
    }

    private boolean isSelectedCardSpell() {
        //  TODO
        return true;
    }

    private boolean isSelectedCardCollectable() {
        //  TODO
        return true;
    }

    public Player getThisTurnsPlayer() {
        return players[turn];
    }

    public boolean isInTeam(Attacker attacker, Player player) {
        //  TODO
        return true;
    }

    public List<Attacker> getAllGroundedAttacker() {
        //  TODO:
        return null;
    }

    public static void setCurrentMatch(Match currentMatch) {
        Match.currentMatch = currentMatch;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public GoalMode getGoalMode() {
        return goalMode;
    }

    public GameType getGameType() {
        return gameType;
    }

    public int getFlagCount() {
        return flagCount;
    }

    public List<Flag> getFlags() {
        return flags;
    }

    public Flag getFlag() {
        return flag;
    }

    public String whoHasFlag() { //  for mode 2
        return null;
    }

    public List<String> whoHasFlags() {  //  for mode 3
        List<String> attackers = new ArrayList<>();
        return attackers;
    }

    public Attacker getSelectedAttacker() {
        if (selectedCard instanceof Attacker)
            return (Attacker) selectedCard;
        return null;
    }

    private void initiateMatch() {
        for (PlayerMatchInfo playerMatchInfo : info)
            playerMatchInfo.reset();
        info[0].getHero().setCurrentCell(getCell(2, 0));
        info[1].getHero().setCurrentCell(getCell(2, 8));
        for (int i = 0; i < 2; i++)
            Card.setCardIDInGame(players[i], info[i].getHero());
    }


}
/*
login a
a
main menu
battle
2
select user b
start multiplayer game KILL_HERO

 */