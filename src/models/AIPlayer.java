package models;

import java.util.ArrayList;
import java.util.List;

public class AIPlayer extends Player {
    private static List<AIPlayer> aiPlayers = new ArrayList<>();
    private int aiID; // mode
    private int winningPrize;

    public AIPlayer(int aiID, Deck deck, int winningPrize) {
        super();
        super.getCollection().addDeck(deck);
        this.aiID = aiID;
        this.winningPrize = winningPrize;
        this.setUsername(Integer.toString(aiID));
    }

    public static void addAIPlayer(List<AIPlayer> aiPlayers) {
        if (aiPlayers == null)
            return;
        for (AIPlayer aiPlayer : aiPlayers)
            addAIPlayer(aiPlayer);
    }

    public static void addAIPlayer(AIPlayer aiPlayer) {
        if (aiPlayer == null)
            return;
        aiPlayers.add(aiPlayer);
    }

    public static List<AIPlayer> getAiPlayers() {
        return aiPlayers;
    }

    private void play() {

    }

    public int getAiID() {
        return aiID;
    }

    public int getWinningPrize() {
        return winningPrize;
    }
}
