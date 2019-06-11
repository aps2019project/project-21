package controller.menus;

import controller.InputScanner;
import models.AIPlayer;
import models.Player;
import models.match.GameMode;
import models.match.GameType;
import models.match.GoalMode;
import models.match.Match;
import view.Message;
import view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu extends Menu {
    private static MainMenu instance = new MainMenu();
    private Player second;
    private GameMode gameMode;
    private GameType gameType;
    private GoalMode goalMode;
    private int flagCount;

    public static MainMenu getInstance() {
        return instance;
    }

    private MainMenu() {

    }

    private boolean createStoryMatch() {
        System.out.print("choose one of 3 story matches below: (enter 1, 2 or 3)\n");
        System.out.print("1. Hero: Dive Sefid, Mode: Kill Hero\n");
        System.out.print("2. Hero: Zahak, Mode: Hold Flag\n");
        System.out.print("3. Hero: Arash, Mode: Gather Flag\n");
        String num = InputScanner.nextLine();
        if (!num.matches("[1-3]")) {
            invalidCommand();
            return false;
        }
        AIPlayer aiPlayer = AIPlayer.getAIPlayer(Integer.parseInt(num));
        if (aiPlayer == null) {
            View.getInstance().printError(Message.AIPLAYER_IS_NULL);
            return false;
        }
        second = aiPlayer;
        goalMode = GoalMode.KILL_HERO;
        gameMode = GameMode.SINGLE_PLAYER;
        gameType = GameType.STORY;
        return true;
    }

    private boolean createCustomMatch() {
        System.out.println("choose one of these decks: (enter the number)");
        for (AIPlayer aiPlayer : AIPlayer.getAiPlayers())
            if (aiPlayer != null && aiPlayer.getCollection().hasMainDeck()) {
                System.out.println("AI " + aiPlayer.getAiID() + ":");
                view.showDeck(aiPlayer.getCollection().getMainDeck());
            }
        System.out.println("enter main game command ...");
        String commandLine = InputScanner.nextLine();
        String regex = "start game (\\d+) (\\w+)( (\\d+))?";
        Matcher matcher = Pattern.compile(regex).matcher(commandLine);
        if (!matcher.matches()) {
            invalidCommand();
            return false;
        }
        int aiID = Integer.parseInt(matcher.group(1));
        AIPlayer aiPlayer = AIPlayer.getAIPlayer(aiID);
        if (aiPlayer == null) {
            view.printError(Message.AIPLAYER_IS_NULL);
            return false;
        }
        GoalMode goalMode;
        try {
            goalMode = GoalMode.valueOf(matcher.group(2));
        } catch (Exception e) {
            invalidCommand();
            return false;
        }
        int flagCount = 0;
        if (goalMode.equals(GoalMode.GATHER_FLAG))
            if (commandLine.matches("start game (\\d+) (\\w+) (\\d+)"))
                flagCount = Integer.parseInt(matcher.group(4));
            else {
                invalidCommand();
                return false;
            }
        second = aiPlayer;
        this.goalMode = goalMode;
        this.gameType = GameType.CUSTOM;
        this.gameMode = GameMode.SINGLE_PLAYER;
        this.flagCount = flagCount;
        return true;
    }

    public boolean chooseOpp(String name) {
        second = Player.getPlayerByUsername(name);
        if (second == null) {
            View.getInstance().printError(Message.NO_SUCH_USER);
            return false;
        }
        if (!second.hasAValidMainDeck()) {
            View.getInstance().printError(Message.DECK_IS_NOT_VALID);
            return false;
        }
        return true;
    }

    public boolean chooseAI(String num) {
        int n;
        try {
            n = Integer.parseInt(num);
        } catch (Exception e) {
            View.getInstance().printError(Message.INVALID_AI);
            return false;
        }
        AIPlayer aiPlayer = AIPlayer.getAIPlayer(n);
        if (aiPlayer == null) {
            view.printError(Message.AIPLAYER_IS_NULL);
            return false;
        }
        second = aiPlayer;
        return true;
    }

    public void setKillHero() {
        goalMode = GoalMode.KILL_HERO;
    }

    public void setHoldFlag(String flagCount) {
        goalMode = GoalMode.HOLD_FLAG;
        try {
            this.flagCount = Integer.parseInt(flagCount);
        } catch (Exception e) {
            this.flagCount = 1;
        }
    }

    public void setGatherFlag(String flagCount) {
        goalMode = GoalMode.GATHER_FLAG;
        try {
            this.flagCount = Integer.parseInt(flagCount);
        } catch (Exception e) {
            this.flagCount = 1;
        }
    }

    public void setSingle() {
        this.gameMode = GameMode.SINGLE_PLAYER;
    }

    public void setMultiplayer() {
        this.gameMode = GameMode.MULTI_PLAYER;
        gameType = null;
    }

    public void startMatch() {
        Match match = new Match(Player.getCurrentPlayer(), second, gameMode, gameType, goalMode, flagCount);
        Match.setCurrentMatch(match);
        //  view.goto battle
    }

    public void startStoryMatch(int num) {
        if (num > 3 || num < 1)
            return;
        AIPlayer aiPlayer = AIPlayer.getAIPlayer(num);
        if (aiPlayer == null) {
            View.getInstance().printError(Message.AIPLAYER_IS_NULL);
            return;
        }
        second = aiPlayer;
        goalMode = GoalMode.KILL_HERO;
        gameMode = GameMode.SINGLE_PLAYER;
        gameType = GameType.STORY;
        startMatch();
    }

    private boolean createMultiPlayer() {
        for (Player player : Player.getPlayers())
            if (player != null && player != Player.getCurrentPlayer())
                System.out.println(player.getUsername());
        System.out.println("choose opp:");
        String regex = "select user (\\w+)";
        String command = InputScanner.nextLine();
        Matcher matcher = Pattern.compile(regex).matcher(command);
        if (!matcher.matches()) {
            invalidCommand();
            return false;
        }
        String name = matcher.group(1);
        Player player = Player.getPlayerByUsername(name);
        if (player == null) {
            invalidCommand();
            return false;
        }
        System.out.println("enter goalMode and flags (if needed)");
        command = InputScanner.nextLine();
        regex = "start multiplayer game (\\w+)( (\\d+))?";
        matcher = Pattern.compile(regex).matcher(command);
        if (!matcher.matches()) {
            invalidCommand();
            return false;
        }
        GoalMode goalMode;
        try {
            goalMode = GoalMode.valueOf(matcher.group(1));
        } catch (Exception e) {
            invalidCommand();
            return false;
        }
        int flags = 0;
        if (goalMode == GoalMode.GATHER_FLAG)
            if (command.matches("start multiplayer game (\\w+) (\\d+)"))
                flags = Integer.parseInt(matcher.group(3));
            else {
                invalidCommand();
                return false;
            }
        this.flagCount = flags;
        this.second = player;
        this.goalMode = goalMode;
        this.gameMode = GameMode.MULTI_PLAYER;
        this.gameType = GameType.CUSTOM;
        return true;
    }
}
