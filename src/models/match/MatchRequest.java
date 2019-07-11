package models.match;

import java.io.Serializable;

public class MatchRequest implements Serializable {
    private static final long serialVersionUID = 6529685098267757041L;

    private GameMode gameMode;
    private GameType gameType;
    private GoalMode goalMode;
    private int flagCount;

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public GoalMode getGoalMode() {
        return goalMode;
    }

    public void setGoalMode(GoalMode goalMode) {
        this.goalMode = goalMode;
    }

    public int getFlagCount() {
        return flagCount;
    }

    public void setFlagCount(int flagCount) {
        this.flagCount = flagCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MatchRequest))
            return false;
        MatchRequest other = (MatchRequest) obj;
        return this.goalMode == other.goalMode &&
                this.gameType == other.gameType &&
                this.gameMode == other.gameMode &&
                this.flagCount == other.flagCount;
    }
}
