package models;

import java.util.ArrayList;
import java.util.List;

public class AIPlayer extends Player {
    private static final long serialVersionUID = 6529685098267757043L;

    private static List<AIPlayer> aiPlayers = new ArrayList<>();
    private int aiID; // mode
    private int winningPrize;

    public AIPlayer(int aiID, Deck deck, int winningPrize) {
        super();
        super.getCollection().addDeck(deck);
        super.getCollection().autoChooseDeck();
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

    private static void addAIPlayer(AIPlayer aiPlayer) {
        if (aiPlayer == null)
            return;
        aiPlayers.add(aiPlayer);
    }

    public static List<AIPlayer> getAiPlayers() {
        return aiPlayers;
    }

    public int getAiID() {
        return aiID;
    }

    public int getWinningPrize() {
        return winningPrize;
    }

    public static AIPlayer getAIPlayer(int aiID) {
        for (AIPlayer aiPlayer : aiPlayers)
            if (aiPlayer.aiID == aiID)
                return aiPlayer;
        return null;
    }
}
