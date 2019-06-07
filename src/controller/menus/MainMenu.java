package controller.menus;

import controller.InputScanner;
import controller.request.MainMenuRequest;
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

    static Menu getInstance() {
        return instance;
    }

    private MainMenu() {

    }

    public void main() {
        if (showMenu) {
            showMenu();
            showMenu = false;
        }

        request = new MainMenuRequest();

        request.getNewCommand();

        request.extractType();

        switch (request.getType()) {
            case COLLECTION:
                collection();
                break;
            case SHOP:
                shop();
                break;
            case BATTLE:
                battle();
                break;
            case BACK:
                back();
                break;
            case HELP:
                help();
                break;
            case SHOW_MENU:
                showMenu();
                break;
            case EXIT:
                exit();
                break;
            case INVALID:
                invalidCommand();
                break;
        }
    }

    private void help() {

    }

    private void shop() {
        MenuManager.getInstance().gotoShop();
    }

    private void collection() {
        MenuManager.getInstance().gotoCollection();
    }

    protected void showMenu() {
        view.showMenu("MainMenu");
    }

    private void back() {
        MenuManager.getInstance().gotoAccount();
    }

    private void battle() {
        this.flagCount = 0;
        this.goalMode = null;
        this.gameMode = null;
        this.gameType = null;
        this.second = null;
        if (!Player.getCurrentPlayer().hasAValidMainDeck()) {
            view.printError(Message.MAIN_DECK_IS_INVALID);
            return;
        }
        if (createNewMatch()) {
            if (second == null || goalMode == null
                    || gameMode == null || gameType == null
                    || Player.getCurrentPlayer() == null) {
                invalidCommand();
                return;
            }
            if (!second.hasAValidMainDeck()) {
                view.printError(Message.HIS_MAIN_DECK_INVALID);
                return;
            }
            Match match = new Match(Player.getCurrentPlayer(), second, gameMode, gameType, goalMode, flagCount);
            Match.setCurrentMatch(match);
            MenuManager.getInstance().gotoBattle();
        }
    }

    private boolean createNewMatch() {
        System.out.print("choose match mode: (enter 1 or 2) \n" +
                "1. single player\n" +
                "2. multi player\n");
        String num = InputScanner.nextLine();
        if (!num.matches("[1-2]")) {
            invalidCommand();
            return false;
        }
        if (num.equals("1"))
            return createSinglePlayer();
        else
            return createMultiPlayer();
    }

    private boolean createSinglePlayer() {
        System.out.print("choose gameType: (enter 1 or 2):\n" +
                "1. story\n" +
                "2. custom game\n");
        String num = InputScanner.nextLine();
        if (!num.matches("[1-2]")) {
            invalidCommand();
            return false;
        }
        if (num.equals("1"))
            return createStoryMatch();
        else
            return createCustomMatch();
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
