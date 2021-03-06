package models.match;

import controller.menus.HostBattleMenu;
import controller.menus.MainMenu;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.util.Duration;
import json.CardMaker;
import models.AIPlayer;
import models.BattleAction;
import models.Item.Collectable;
import models.Item.Flag;
import models.Player;
import models.card.*;
import network.Client;
import network.request.Request;
import view.*;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Match implements Serializable {
    private static final long serialVersionUID = 6529685098267757040L;

    private static Match currentMatch;
    private static int timeLimit = 10000;
    private static final int MOVE_RANGE = 2;
    private static final int DEFAULT_WINNING_PRIZE = 1000;

    private transient BattleView battleView;
    private Player[] players = new Player[2];
    private Battlefield battlefield;
    private PlayerMatchInfo[] info = new PlayerMatchInfo[2];
    private GameMode gameMode;
    private GoalMode goalMode;
    private GameType gameType;
    private int flagCount; //  only for gather flag and hold flag
    private int collectableCount = 3;
    private List<Flag> flags = new ArrayList<>();
    private LocalDateTime gameTime = LocalDateTime.now();
    private int turn;  //  0 for player1 and 1 for player2
    private int turnCount = 1;
    private Card selectedCard;
    private String winner;
    private String loser;
    private HostBattleMenu hostBattleMenu;
    private Match initialCopy;
    private List<BattleAction> battleActions = new ArrayList<>();
    private long lastEndTurn;

    public void setPlayerOne(Player player) {
        players[0] = player;
    }

    public void setPlayerTwo(Player player) {
        players[1] = player;
    }

    public BattleView getBattleView() {
        return battleView;
    }

    public static Match getCurrentMatch() {
        return currentMatch;
    }

    public PlayerMatchInfo[] getPlayersMatchInfo() {
        return this.info;
    }

    public Match(Player playerOne, Player playerTwo, MatchRequest matchRequest) {
        this.players[0] = playerOne;
        this.players[1] = playerTwo;
        this.battlefield = new Battlefield();
        this.turn = 0;
        this.info[0] = new PlayerMatchInfo(playerOne);
        this.info[1] = new PlayerMatchInfo(playerTwo);
        this.gameMode = matchRequest.getGameMode();
        this.gameType = matchRequest.getGameType();
        this.goalMode = matchRequest.getGoalMode();
        this.flagCount = matchRequest.getFlagCount();
        if (this.goalMode == GoalMode.HOLD_FLAG)
            this.flagCount = 1;
        initiateMatch();
        hostBattleMenu = new HostBattleMenu(this);
        this.initialCopy = CardMaker.deepCopy(this, Match.class);
        this.lastEndTurn = System.currentTimeMillis();
    }

    public Match getInitialCopy() {
        return initialCopy;
    }

    public void action(BattleAction battleAction) {
        try {
            battleActions.add(battleAction);
            Method method = HostBattleMenu.class.getDeclaredMethod(battleAction.getMethod(), battleAction.getArgsClasses());
            method.setAccessible(true);
            method.invoke(hostBattleMenu, battleAction.getArguments());
        } catch (Exception ex) {
            View.printThrowable(ex);
        }
    }

    public boolean amIInThisMatch() {
        return players[0].getUsername().equals(Player.getCurrentPlayer().getUsername())
                || players[1].getUsername().equals(Player.getCurrentPlayer().getUsername());
    }

    public void saveAndExit() {  // only for single player mode
        players[0].addToHistory(this);
//        View.setScene(MainMenuView.getInstance().getScene());
        Client.write(Request.makePlayer(Player.getCurrentPlayer()));
        MainMenu.reset();
    }

    public void setBattleView(BattleView battleView) {
        this.battleView = battleView;
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }

    public void setSelectedCard(Card card) {
        selectedCard = card;
    }

    public void deselect() {
        selectedCard = null;
    }

    public void selectAttacker(String cardID, int x, int y) {
        for (Attacker a : info[turn].getGroundedAttackers())
            if (a.getCardIDInGame().equals(cardID)) {
                selectedCard = a;
                battleView.selectAttackerBoolean(x, y);
                return;
            }
    }

    private boolean selectCollectable(String cardID) {
        for (Collectable collectable : info[turn].getAchievedCollectables())
            if (collectable.getCardIDInGame().equalsIgnoreCase(cardID)) {
                selectedCard = collectable;
                return true;
            }
        return false;
    }

    public boolean selectSpell(String spellName) {
        for (Card card : info[turn].getHand().getCards())
            if (card instanceof Spell)
                if (card.getName().equals(spellName)) {
                    selectedCard = card;
                    return true;
                }
        return false;
    }

    public int getTurn() {
        return turn;
    }

    public Collectable getSelectedCollectable() {
        if (!(selectedCard instanceof Collectable))
            return null;
        return (Collectable) selectedCard;
    }

    public void moveCard(int x, int y) {
        if (!isAnyCardSelected()) {
            selectedCard = null;
            View.printMessage(Message.NO_CARD_IS_SELECTED);
            battleView.moveOrAttack1(x, y);
            return;
        }
        Cell target = getCell(x, y);
        if (target == null) {
            selectedCard = null;
            battleView.moveOrAttack1(x, y);
            return;
        }
        if (!isMoveTargetValid(target)) {
            View.printMessage(Message.INVALID_MOVE_TARGET);
            selectedCard = null;
            battleView.moveOrAttack1(x, y);
            return;
        }
        if (!isSelectedCardAttacker()) {
            selectedCard = null;
            battleView.moveOrAttack1(x, y);
            return;
        }
        Attacker attacker = (Attacker) selectedCard;
        if (!attacker.canMove()) {
            View.printMessage(Message.ATTACKER_CANT_MOVE);
            selectedCard = null;
            battleView.moveOrAttack1(x, y);
            return;
        }
        if (isPathClosed(attacker.getCurrentCell(), target)) {
            View.printMessage(Message.INVALID_MOVE_TARGET);
            selectedCard = null;
            battleView.moveOrAttack1(x, y);
            return;
        }
        if (attacker.isStunned()) {
            View.printMessage(Message.STUNNED);
            selectedCard = null;
            battleView.moveOrAttack1(x, y);
            return;
        }

        attacker.getCurrentCell().setEmpty();
        attacker.setCannotMove();
        goOnCell(attacker, target);
        if (selectedCard != null)
            System.out.println(selectedCard.getCardIDInGame() + " moved to (" + x + ", " + y + ")");
        selectedCard = null;
        System.out.println("RETURN 0");
        battleView.drawCollectables();
        battleView.drawFlags();
        battleView.moveOrAttack0(x, y);
        VoicePlay.move();
    }

    private boolean isMoveTargetValid(Cell target) {
        if (!isSelectedCardAttacker())
            return false;
        Attacker attacker = (Attacker) selectedCard;
        if (Cell.getManhattanDistance(attacker.getCurrentCell(), target) > MOVE_RANGE)
            return false;
        return target.isEmpty();
    }

    private boolean isPathClosed(Cell source, Cell dest) {
        if (Cell.getManhattanDistance(source, dest) == 0)
            return false;
        if (Cell.getManhattanDistance(source, dest) == 1)
            return false;
        for (Cell cell : battlefield.getCellsInList())
            if (Cell.getManhattanDistance(cell, source) == 1
                    && Cell.getManhattanDistance(cell, dest) == 1)
                if (cell.isEmpty())
                    return false;
        return true;
    }

    public void attack(int x, int y) {
        int r = attack(getCell(x, y).getCurrentAttacker().getCardIDInGame());
        if (r == 1)
            battleView.moveOrAttack3(x, y);
        else if (r == 0)
            battleView.moveOrAttack2(x, y);
    }

    public int attack(String oppID) {
        if (!isAnyCardSelected()) {
            View.printMessage(Message.NO_CARD_IS_SELECTED);
            return 1;
        }
        Attacker target = getGroundedOppAttacker(oppID);
        if (target == null) {
            View.printMessage(Message.INVALID_CARD_ID);
            return 1;
        }
        if (!isSelectedCardAttacker()) {
            View.printMessage(Message.NO_CARD_IS_SELECTED);
            return 1;
        }
        Attacker attacker = (Attacker) selectedCard;
        if (!attacker.canAttack() || attacker.isStunned()) {
            System.out.println("card with id : " + selectedCard.getCardIDInGame() + " can't attack.");
            return 1;
        }
        if (!isInRangeOfAttack(target, attacker)) {
            View.printMessage(Message.UNAVAILABLE_FOR_ATTACK);
            return 1;
        }
        if (attacker.isStunned()) {
            View.printMessage(Message.STUNNED);
            return 1;
        }
        target.decreaseHP(attacker.getAP());
        counterAttack(target);
        checkIfHeIsDead(target);
        attacker.setCannotAttack();
        attacker.setCannotMove();
        System.out.println("attacked.");
        VoicePlay.attack();

        if (attacker instanceof Minion) {
            Minion minion = (Minion) attacker;
            if (minion.hasSpecialPower())
                for (Effect effect : minion.getSpecialPower().getEffects())
                    if (effect.getActivationType() == ActivationType.ON_ATTACK)
                        minion.getSpecialPower().castSpell(this, players[turn], target.getCurrentCell());
        }
        return 0;
    }

    private void checkIfHeIsDead(Attacker attacker) {
        if (attacker == null)
            return;
        if (attacker.getHP() <= 0) {
            int i = info[getCardsTeam(attacker)].getGroundedAttackers().indexOf(attacker);
            getCell(info[getCardsTeam(attacker)].getGroundedAttackers().get(i).getCurrentCell().getX(),
                    info[getCardsTeam(attacker)].getGroundedAttackers().get(i).getCurrentCell().getY()).dieAttacker();
            info[getCardsTeam(attacker)].kill(attacker);
        }
        if (attacker instanceof Minion) {
            Minion minion = (Minion) attacker;
            if (minion.hasSpecialPower())
                for (Effect effect : minion.getSpecialPower().getEffects())
                    if (effect.getActivationType() == ActivationType.ON_DEATH)
                        minion.getSpecialPower().castSpell(this, players[turn], attacker.getCurrentCell());
        }
    }

    private boolean isInRangeOfAttack(Attacker target, Attacker attacker) {
        if (attacker == null || target == null)
            return false;
        int dist = Cell.getManhattanDistance(target.getCurrentCell(),
                (attacker).getCurrentCell());
        if (attacker.getAttackMode() == AttackMode.MELEE) {
            return dist <= 1;
        } else if (attacker.getAttackMode() == AttackMode.RANGED) {
            return dist > 1 && dist <= attacker.getAttackRange();
        } else return dist <= attacker.getAttackRange();
    }

    private Attacker getGroundedOppAttacker(String oppID) {
        for (Attacker attacker : info[1 - turn].getGroundedAttackers())
            if (attacker.getCardIDInGame().equals(oppID))
                return attacker;
        return null;
    }

    private void counterAttack(Attacker opp) {
        if (opp == null || getSelectedAttacker() == null || opp.isDisarmed())
            return;
        getSelectedAttacker().decreaseHP(opp.getAP());
        if (opp instanceof Minion) {
            Minion minion = (Minion) opp;
            if (minion.hasSpecialPower())
                for (Effect effect : minion.getSpecialPower().getEffects())
                    if (effect.getActivationType() == ActivationType.ON_DEFEND)
                        minion.getSpecialPower().castSpell(this, players[turn], selectedCard.getCurrentCell());
        }
    }

    public void attackCombo(String opponentCardID, String[] myCardIDs) {  // test
        if (!isAnyCardSelected()) {
            View.printMessage(Message.NO_CARD_IS_SELECTED);
            return;
        }
        if (!(selectedCard instanceof Minion)) {
            View.printMessage(Message.SELECTED_CARD_NOT_MINION);
            return;
        }
        Minion selectedMinion = (Minion) selectedCard;
        Minion[] minions = new Minion[myCardIDs.length];
        for (String myCardID : myCardIDs) {
            Minion comboMinion = info[turn].getGroundedMinionByID(myCardID);
            if (comboMinion == null) {
                View.printMessage(Message.SELECTED_CARD_NOT_MINION);
                return;
            }
            if (!comboMinion.isCombo()) {
                View.printMessage(Message.NOT_COMBO);
                return;
            }
        }
        Attacker target = getGroundedOppAttacker(opponentCardID);
        if (target == null) {
            View.printMessage(Message.INVALID_CARD_ID);
            return;
        }
        if (!isInRangeOfOneOfThese(target, minions)) {
            View.printMessage(Message.UNAVAILABLE_FOR_ATTACK);
            return;
        }
        target.decreaseHP(selectedMinion.getAP());
        selectedMinion.setCannotAttack();
        selectedMinion.setCannotMove();
        for (Minion minion : minions) {
            target.decreaseHP(minion.getAP());
            minion.setCannotAttack();
            minion.setCannotMove();
        }
        counterAttack(target);
        checkIfHeIsDead(target);
    }

    private boolean isInRangeOfOneOfThese(Attacker target, Minion[] minions) {
        for (Minion minion : minions)
            if (isInRangeOfAttack(target, minion))
                return true;
        return false;
    }

    public void useSpell(int x, int y) {
        if (!isAnyCardSelected()) {
            View.printMessage(Message.NO_CARD_IS_SELECTED);
            return;
        }
        if (!isSelectedCardSpell()) {
            View.printMessage(Message.NO_SPELL_SELECTED);
            return;
        }
        Cell target = getCell(x, y);
        if (target == null) {
            View.printMessage(Message.INVALID_CELL);
            return;
        }
        Spell spell = (Spell) selectedCard;
        if (!info[turn].hasManaForThis(spell)) {
            View.printMessage(Message.HAVE_NOT_MANA);
            return;
        }
        spell.castSpell(this, getThisTurnsPlayer(), target);
        info[turn].getHand().remove(spell);
        info[turn].decreaseMP(spell.getManaCost());
        battleView.drawMana();
        battleView.drawHand();
        battleView.drawSpellEffect(spell, x, y);
        VoicePlay.spell();
    }

    public void useCollectable(int x, int y) {
        if (!isAnyCardSelected()) {
            View.printMessage(Message.NO_CARD_IS_SELECTED);
            return;
        }
        if (!(selectedCard instanceof Collectable)) {
            View.printMessage(Message.NO_COLLECTABLE_SELECTED);
            return;
        }
        Collectable collectable = (Collectable) selectedCard;
        Cell target = getCell(x, y);
        if (target == null)
            return;
        collectable.castItem(this, getThisTurnsPlayer(), target);
    }

    public void useSpecialPower(int x, int y) {
        if (!isAnyCardSelected()) {
            View.printMessage(Message.NO_CARD_IS_SELECTED);
            return;
        }
        if (!isSelectedCardAttacker()) {
            View.printMessage(Message.NO_ATTACKER_SELECTED);
            return;
        }
        Attacker attacker = (Attacker) selectedCard;
        if (!attacker.hasSpecialPower()) {
            View.printMessage(Message.HASNT_SPECIAL);
            return;
        }
        Cell target = getCell(x, y);
        if (target == null) {
            View.printMessage(Message.INVALID_CELL);
            return;
        }
        if (!info[turn].hasManaForThis(attacker.getSpecialPower())) {
            View.printMessage(Message.HAVE_NOT_MANA);
            return;
        }
        if (attacker instanceof Hero)
            if (((Hero) attacker).getCooldown() > 0) {
                View.printMessage(Message.COOLDOWN);
                return;
            }
        attacker.castSpecialPower(this, getThisTurnsPlayer(), target);
        battleView.drawSpellEffect(attacker.getSpecialPower(), x, y);
        battleView.drawMana();
    }

    private void applyEffects() {
        for (Attacker attacker : getBothGroundedAttackers())
            attacker.applyEffects();
    }

    public void insertCard(String cardName, int x, int y) {
        Attacker attacker = info[turn].getHand().getAttacker(cardName);
        for (Card card : info[turn].getHand().getCards())
            System.out.println("hand: " + card.getName());
        System.out.println(cardName);
        if (attacker == null) {
            View.printMessage(Message.NO_CARD_WITH_THIS_NAME);
            return;
        }
        Cell cell = getCell(x, y);
        if (cell == null || !cell.isEmpty() || !isInsertNear(cell)) {
            View.printMessage(Message.INVALID_CELL);
            return;
        }

        if (!info[turn].hasManaForThis(attacker)) {
            View.printMessage(Message.HAVE_NOT_MANA);
            return;
        }

        attacker = (Attacker) info[turn].getHand().pop(cardName);
        info[turn].addToGroundedAttackers(attacker);
        setCardInGameID(attacker);
        goOnCell(attacker, cell);
        info[turn].decreaseMP(attacker.getManaCost());
        Card.setCardIDInGame(players[turn], attacker);
        System.out.println("card " + attacker.getName() + " with id: " + attacker.getCardIDInGame()
                + " inserted to (" + x + ", " + y + ").");

        if (attacker instanceof Minion) {
            Minion minion = (Minion) attacker;
            if (minion.hasSpecialPower())
                for (Effect effect : minion.getSpecialPower().getEffects())
                    if (effect.getActivationType() == ActivationType.ON_SPAWN)
                        minion.getSpecialPower().castSpell(this, players[turn], attacker.getCurrentCell());
        }

        battleView.drawHand();
        battleView.drawAttackers();
        battleView.drawMana();
        VoicePlay.insert();
    }

    private void goOnCell(Attacker attacker, Cell cell) {
        if (cell == null || attacker == null)
            return;
        attacker.setCurrentCell(cell);
        cell.setCurrentAttacker(attacker);
        if (cell.hasCollectable()) {
            info[getCardsTeam(attacker)].addCollectable(cell.getCollectable());
            setCardInGameID(cell.getCollectable());
            cell.setCollectable(null);
        }
        if (cell.hasFlag() && attacker.getFlag() == null) {
            cell.getFlag().setHoldingTime(0);
            attacker.setFlag(cell.getFlag());
            cell.setFlag(null);
        }
        battleView.drawMana();
    }

    private void setCardInGameID(Card card) {
        if (card == null)
            return;
        String cardIdInGame = players[turn].getUsername() + "_" + card.getName() + "_";
        cardIdInGame += Integer.toString(cardNameRank(card.getName()));
        card.setCardIDInGame(cardIdInGame);
    }

    private int cardNameRank(String name) {  // todo chech this, check single player replay logic cuase i didnt insert bug
        int num = 0;
        for (Card card : info[turn].getAllUsedCards())
            if (card.getName().equals(name))
                num++;
        for (Collectable collectable : info[turn].getAchievedCollectables())
            if (collectable.getName().equalsIgnoreCase(name))
                num++;
        return num;
    }

    private boolean isInsertNear(Cell cell) {
        for (Attacker attacker : getPlayersMatchInfo()[turn].getGroundedAttackers())
            if (Cell.getEuclideanDistance(attacker.getCurrentCell(), cell) < 1.43d)
                return true;
        return false;
    }

    public List<Attacker> getBothGroundedAttackers() {
        List<Attacker> attackers = new ArrayList<>(info[0].getGroundedAttackers());
        attackers.addAll(info[1].getGroundedAttackers());
        return attackers;
    }

    private void swapTurn() {
        turn = 1 - turn;
        turnCount++;
        if (getThisTurnsPlayer().getUsername().equals(Player.getCurrentPlayer().getUsername()))
            View.setIsOppPlaying(false);
        else
            View.setIsOppPlaying(true);
    }

    private void aiPlay() {
        System.out.println("AI playing...");
        try {
            Hero hero = info[1].getHero();
            if (hero != null) {
                selectAttacker(hero.getCardIDInGame(), hero.getCurrentCell().getX(), hero.getCurrentCell().getY());
                moveCard(2, info[1].getHero().getCurrentCell().getY() - 1);
            }

            for (Attacker attacker : info[1].getGroundedAttackers()) {
                if (attacker == null)
                    continue;
                if (attacker.canMove()) {
                    selectAttacker(attacker.getCardIDInGame(), attacker.getCurrentCell().getX(), attacker.getCurrentCell().getY());
                    int random = new Random().nextInt(2) * 2 - 1;
                    moveCard(attacker.getCurrentCell().getX() + random, attacker.getCurrentCell().getY());
                }
            }

            Duration duration = Duration.millis(2500);
            TranslateTransition t2 = new TranslateTransition(duration, new Group());
            t2.play();

            t2.setOnFinished(event -> {
                for (Card card : info[1].getHand().getCards())
                    if (card instanceof Minion)
                        if (info[1].hasManaForThis(card)) {
                            turn = 1;
                            insertCard(card.getName(), info[1].getHero().getCurrentCell().getX() - 1
                                    , info[1].getHero().getCurrentCell().getY());
                            turn = 0;
                            break;
                        }
            });
        } finally {
            endTurn();
        }
    }

    private void isMatchEnded() {
        if (goalMode == GoalMode.KILL_HERO) {
            for (int i = 0; i < 2; i++)
                if (info[i].getHero() == null || !info[i].getHero().isAlive()) {
                    endMatch(players[1 - i], players[i]);
                    return;
                }
        } else if (goalMode == GoalMode.HOLD_FLAG) {
            if (flags.get(0).getHoldingTime() >= 11)
                if (whichTeamHasTheFlag() != -1) {
                    endMatch(players[whichTeamHasTheFlag()], players[1 - whichTeamHasTheFlag()]);
                }
        } else if (goalMode == GoalMode.GATHER_FLAG) {
            for (int i = 0; i < 2; i++)
                if (2 * info[i].gatheredFlags() >= flagCount) {
                    endMatch(players[i], players[1 - i]);
                    return;
                }
        }
    }

    private int whichTeamHasTheFlag() {
        for (Attacker attacker : getBothGroundedAttackers())
            if (attacker.getFlag() != null)
                return getCardsTeam(attacker);
        return -1;
    }

    private void endMatch(Player winner, Player loser) {
        VoicePlay.victory();
        this.winner = winner.getUsername();
        this.loser = loser.getUsername();
        winner.addDrake(getMatchWinningPrize());
        saveMatchResults(winner, loser);
        showMatchResults();
        View.setScene(MainMenuView.getInstance().getScene());
        Client.write(Request.makePlayer(Player.getCurrentPlayer()));
        MainMenu.reset();
    }

    private int getMatchWinningPrize() {
        if (gameMode == GameMode.SINGLE_PLAYER && gameType == GameType.STORY)
            return ((AIPlayer) players[1]).getWinningPrize();
        else
            return DEFAULT_WINNING_PRIZE;
    }

    private void saveMatchResults(Player winner, Player loser) {
        winner.incrementWins();
        loser.incrementLosses();
        winner.getMatchHistory().remove(this);
        winner.addToHistory(this);
        loser.getMatchHistory().remove(this);
        loser.addToHistory(this);
    }

    private void showMatchResults() {
        View.setIsOppPlaying(false);
        View.showMatchResults(this);
    }

    public boolean isAnyCardSelected() {
        return selectedCard != null;
    }

    public Cell getCell(int x, int y) {
        return battlefield.getCell(x, y);
    }

    public PlayerMatchInfo getInfo(Player player) {
        for (int i = 0; i < 2; i++)
            if (players[i].getUsername().equals(player.getUsername()))
                return info[i];
        return null;
    }

    private boolean isSelectedCardAttacker() {
        if (!isAnyCardSelected())
            return false;
        return selectedCard instanceof Attacker;
    }

    private boolean isSelectedCardSpell() {
        return selectedCard instanceof Spell;
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

    public GoalMode getGoalMode() {
        return goalMode;
    }

    public List<Flag> getFlags() {
        return flags;
    }

    public Attacker whoHasFlag() { //  for mode 2
        for (Attacker attacker : getBothGroundedAttackers())
            if (attacker.getFlag() != null)
                return attacker;
        return null;
    }

    public List<Attacker> whoHasFlags() {  //  for mode 3
        List<Attacker> attackers = new ArrayList<>();
        for (Attacker attacker : getBothGroundedAttackers())
            if (attacker.getFlag() != null)
                attackers.add(attacker);
        return attackers;
    }

    private Attacker getSelectedAttacker() {
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
                Flag flag = new Flag(cell);
                cell.setFlag(flag);
                flags.add(flag);
                cnt++;
            }
        }
    }

    private void initiateCollectables() {
        int cnt = 0;
        while (cnt < collectableCount) {
            Cell cell = battlefield.getRandomCell();
            if (!cell.hasCollectable() && !cell.hasFlag()) {
                Collectable collectable = Collectable.getRandomCollectable();
                cell.setCollectable(collectable);
                collectable.setCurrentCell(cell);
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
                } else if (getCell(i, j).hasCollectable())
                    System.out.print(" C ");
                else if (getCell(i, j).hasFlag())
                    System.out.print(" F ");
                else System.out.print(" O ");
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
        endMatch(players[turn], players[1 - turn]);
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

    public void endTurn() {
        this.lastEndTurn = System.currentTimeMillis();
        isMatchEnded();
        prepareNextRound();
        swapTurn();
        if (gameMode == GameMode.SINGLE_PLAYER)
            if (turn == 1)
                aiPlay();
        System.out.println("turn ended.");
        battleView.drawMana();
        VoicePlay.endturn();
    }

    private void prepareNextRound() {
        battleView.drawCollectables();
        battleView.drawFlags();
        increaseFlagHoldingTime();
        applyEffects();
        setCanMove();
        info[turn].pushToHand();
        setCanAttack();
        increaseMana();
        unSelect();
        if (turn % 2 == 0)
            decreaseCooldown();
    }

    private void decreaseCooldown() {
        for (Attacker attacker : getBothGroundedAttackers())
            if (attacker instanceof Hero)
                ((Hero) attacker).decreaseCooldown();
    }

    private void increaseFlagHoldingTime() {
        if (whichTeamHasTheFlag() != -1)
            flags.get(0).increaseHoldingTime();
    }

    private void setCanMove() {
        for (Attacker attacker : getBothGroundedAttackers())
            attacker.setCanMove();
    }

    private void setCanAttack() {
        for (Attacker attacker : getBothGroundedAttackers())
            attacker.setCanAttack();
    }

    private void increaseMana() {
        for (PlayerMatchInfo pInfo : info)
            pInfo.setMp(3 + (turnCount) / 2);
    }

    private void unSelect() {
        this.selectedCard = null;
    }

    public String getWinner() {
        return winner;
    }

    public String getLoser() {
        return loser;
    }

    public List<BattleAction> getBattleActions() {
        return battleActions;
    }

    public LocalDateTime getGameTime() {
        return gameTime;
    }

    public void withdraw(Player player) {
        if (player.getUsername().equals(getThisTurnsPlayer().getUsername()))
            endMatch(players[1 - turn], players[turn]);
        else
            endMatch(players[turn], players[1 - turn]);
    }

    public int getTeamOfPlayer(Player player) {
        for (int i = 0; i < 2; i++)
            if (player.getUsername().equalsIgnoreCase(players[i].getUsername()))
                return i;
        return -1;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setInfo(PlayerMatchInfo[] info) {
        this.info = info;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public static int getTimeLimit() {
        return timeLimit;
    }

    public static void setTimeLimit(int timeLimit) {
        Match.timeLimit = timeLimit;
    }

    public long getLastEndTurn() {
        return lastEndTurn;
    }
}
