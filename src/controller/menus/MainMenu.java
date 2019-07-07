package controller.menus;

import models.AIPlayer;
import models.Player;
import models.match.*;
import view.BattleView;
import view.Message;
import view.View;

public class MainMenu extends Menu {
    private static MainMenu instance = new MainMenu();
    private MatchRequest matchRequest = new MatchRequest();
    private Player second;

    public static MainMenu getInstance() {
        return instance;
    }

    private MainMenu() {
    }

    public MatchRequest getMatchRequest() {
        return matchRequest;
    }

    public boolean chooseOpp(String name) {
        second = Player.getPlayerByUsername(name);
        if (second == null) {
            View.printMessage(Message.NO_SUCH_USER);
            return false;
        }
        if (!second.hasAValidMainDeck()) {
            View.printMessage(Message.DECK_IS_NOT_VALID);
            return false;
        }
        return true;
    }

    public boolean chooseAI(int num) {
        AIPlayer aiPlayer = AIPlayer.getAIPlayer(num);
        if (aiPlayer == null) {
            View.printMessage(Message.AIPLAYER_IS_NULL);
            return false;
        }
        second = aiPlayer;
        return true;
    }

    public void setKillHero() {
        matchRequest.setGoalMode(GoalMode.KILL_HERO);
    }

    public void setHoldFlag(String flagCount) {
        matchRequest.setGoalMode(GoalMode.HOLD_FLAG);
        try {
            matchRequest.setFlagCount(Integer.parseInt(flagCount));
        } catch (Exception e) {
            matchRequest.setFlagCount(1);
        }
    }

    public void setGatherFlag(String flagCount) {
        matchRequest.setGoalMode(GoalMode.GATHER_FLAG);
        try {
            matchRequest.setFlagCount(Integer.parseInt(flagCount));
        } catch (Exception e) {
            matchRequest.setFlagCount(1);
        }
    }

    public void setSingle() {
        matchRequest.setGameMode(GameMode.SINGLE_PLAYER);
    }

    public void setMultiplayer() {
        matchRequest.setGameMode(GameMode.MULTI_PLAYER);
        matchRequest.setGameType(null);
    }

    public void startMatch() {
        Match match = new Match(Player.getCurrentPlayer(), second, matchRequest);
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
            View.printMessage(Message.AIPLAYER_IS_NULL);
            return;
        }
        if (num == 1)
            matchRequest.setGoalMode(GoalMode.KILL_HERO);
        else if (num == 2) {
            matchRequest.setGoalMode(GoalMode.HOLD_FLAG);
            matchRequest.setFlagCount(1);
        } else {
            matchRequest.setGoalMode(GoalMode.GATHER_FLAG);
            matchRequest.setFlagCount(3);
        }
        second = aiPlayer;
        matchRequest.setGameMode(GameMode.SINGLE_PLAYER);
        matchRequest.setGameType(GameType.STORY);
        startMatch();
    }
}
