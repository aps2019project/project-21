package controller.menus;

import models.AIPlayer;
import models.Player;
import models.match.GameMode;
import models.match.GameType;
import models.match.GoalMode;
import models.match.Match;
import view.BattleView;
import view.Message;
import view.View;

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


    public boolean chooseOpp(String name) {
        second = Player.getPlayerByUsername(name);
        if (second == null) {
            View.getInstance().printMessage(Message.NO_SUCH_USER);
            return false;
        }
        if (!second.hasAValidMainDeck()) {
            View.getInstance().printMessage(Message.DECK_IS_NOT_VALID);
            return false;
        }
        return true;
    }

    public boolean chooseAI(int num) {
        AIPlayer aiPlayer = AIPlayer.getAIPlayer(num);
        if (aiPlayer == null) {
            view.printMessage(Message.AIPLAYER_IS_NULL);
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
        BattleView battleView = new BattleView();
        match.setBattleView(battleView);
        battleView.run();
    }

    public void startStoryMatch(int num) {
        if (num > 3 || num < 1)
            return;
        AIPlayer aiPlayer = AIPlayer.getAIPlayer(num);
        if (aiPlayer == null) {
            View.getInstance().printMessage(Message.AIPLAYER_IS_NULL);
            return;
        }
        second = aiPlayer;
        goalMode = GoalMode.KILL_HERO;
        gameMode = GameMode.SINGLE_PLAYER;
        gameType = GameType.STORY;
        startMatch();
    }
}
