package models.match;

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
    private int collectableCount = 2;
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
        if (goalMode == GoalMode.HOLD_FLAG)
            this.flagCount = 1;
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
        if (!isMoveTargetValid(target)) {
            view.printError(ErrorMode.INVALID_MOVE_TARGET);
            return;
        }
        if (!isSelectedCardAttacker())
            return;
        Attacker attacker = (Attacker) selectedCard;
        if (!attacker.canMove()) {
            view.printError(ErrorMode.ATTACKER_CANT_MOVE);
            return;
        }
        //  actually moving the attacker:
        attacker.getCurrentCell().setEmpty();
        attacker.setCurrentCell(target);
        target.setCurrentAttacker(attacker);
        System.out.println(selectedCard.getCardIDInGame() + " moved to " + x + " " + y);
        if (target.hasCollectable()) {
            info[turn].addCollectable(target.getCollectable());
            target.setCollectable(null);
        }
    }

    private boolean isMoveTargetValid(Cell target) {
        if (!isSelectedCardAttacker())
            return false;
        Attacker attacker = (Attacker) selectedCard;
        if (Cell.getManhattanDistance(attacker.getCurrentCell(), target) > MOVE_RANGE)
            return false;
        if (!target.isEmpty())
            return false;
        return true;
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
        // TODO: heroes, minions, infos
    }

    private boolean isAttackTargetValid(Cell target) {
        //  TODO
        return true;
    }

    public void insertCard(String cardName, int x, int y) {
        // TODO: if minion then cast OnSpawn spell
        Attacker attacker = info[turn].getHand().getAttacker(cardName);
        if (attacker == null) {
            view.printError(ErrorMode.NO_CARD_WITH_THIS_NAME);
            return;
        }
        Cell cell = getCell(x, y);
        if (cell == null || !cell.isEmpty() || !isInsertNear(cell)) {
            view.printError(ErrorMode.INVALID_CELL);
            return;
        }
        if (!info[turn].hasManaForThis(attacker)) {
            view.printError(ErrorMode.HAVE_NOT_MANA);
            return;
        }
        attacker = info[turn].getHand().pop(cardName);
        setCardInGameID(attacker);
        goOnCell(attacker, cell);
        info[turn].decreaseMP(attacker.getManaCost());
        info[turn].pushToHand();
        Card.setCardIDInGame(players[turn], attacker);
    }

    private void goOnCell(Attacker attacker, Cell cell) {
        if (cell == null || attacker == null)
            return;
        attacker.setCurrentCell(cell);
        cell.setCurrentAttacker(attacker);
        if (cell.hasCollectable())
            info[getCardsTeam(attacker)].addCollectable(cell.getCollectable());
        if (cell.hasFlag()) {
            info[getCardsTeam(attacker)].increaseFlags();
            cell.setFlag(null);
        }
    }


    private void setCardInGameID(Card card) {
        if (card == null)
            return;
        String cardIdInGame = players[turn].getUsername() + "_" + card.getName() + "_";
        cardIdInGame += Integer.toString(cardNameRank(card.getName()));
        card.setCardIDInGame(cardIdInGame);
    }

    private int cardNameRank(String name) {
        int num = 1;
        for (Card card : info[turn].getAllUsedCards())
            if (card.getName().equals(name))
                num++;
        return num;
    }

    private boolean isInsertNear(Cell cell) {
        for (Attacker attacker : getBothGroundedAttackers())
            if (Cell.getEuclideanDistance(attacker.getCurrentCell(), cell) < 1.43d)
                return true;
        return false;
    }

    public List<Attacker> getBothGroundedAttackers() {
        List<Attacker> attackers = new ArrayList<>(info[0].getGroundedAttackers());
        attackers.addAll(info[1].getGroundedAttackers());
        return attackers;
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
        for (Card card1 : player.getCollection().getMainDeck().getAllCards())
            if (card1.getCollectionID() == attacker.getCollectionID())
                return true;
        return false;
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
        info[0].addToGroundedAttackers(info[0].getHero());
        info[1].addToGroundedAttackers(info[1].getHero());
        for (int i = 0; i < 2; i++)
            Card.setCardIDInGame(players[i], info[i].getHero());
        if (goalMode == GoalMode.HOLD_FLAG || goalMode == GoalMode.GATHER_FLAG)
            initiateFlags();
        initiateCollectables();
    }

    private void initiateFlags() {
        int cnt = 0;
        while (cnt < flagCount) {
            Cell cell = battlefield.getRandomCell();
            if (!cell.hasFlag()) {
                cell.setFlag(new Flag());
                cnt++;
            }
        }
    }

    private void initiateCollectables() {
        int cnt = 0;
        while (cnt < collectableCount) {
            Cell cell = battlefield.getRandomCell();
            if (!cell.hasCollectable()) {
                cell.setCollectable(Collectable.getRandomCollectable());
                cnt++;
            }
        }
    }

    public void showBattleField() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(" ---");
                if (j == 8)
                    System.out.print(" ");
            }
            System.out.println();
            for (int j = 0; j < 9; j++) {
                System.out.print("|");
                if (!getCell(i, j).isEmpty()) {
                    Attacker attacker = getCell(i, j).getCurrentAttacker();
                    if (isInTeam(attacker, players[0]))
                        System.out.print(" A ");
                    else if (isInTeam(attacker, players[1]))
                        System.out.print(" B ");
                } else System.out.print(" O ");
                if (j == 8)
                    System.out.print("|");
            }
            System.out.println();
        }
        for (int j = 0; j < 9; j++) {
            System.out.print(" ---");
            if (j == 8)
                System.out.print(" ");
        }
        System.out.println();
    }

    public void kill() {
        //  TODO
    }

    public int getCardsTeam(Card card) {  //  returns the turn number
        for (int i = 0; i < 2; i++) {
            Player player = players[i];
            for (Card card1 : player.getCollection().getMainDeck().getAllCards())
                if (card1.getCollectionID() == card.getCollectionID())
                    return i;
        }
        return -1;
    }

    public void showMP() {
        System.out.println(info[turn].getMp());
    }


}
