package models;

import java.util.ArrayList;
import java.util.List;

public class AIPlayer {
    private static List<AIPlayer> aiPlayers = new ArrayList<>();
    private int id;

    private void play() {

    }

    public static List<AIPlayer> getAiPlayers() {
        return aiPlayers;
    }

    public static void setAiPlayers(List<AIPlayer> aiPlayers) {
        AIPlayer.aiPlayers = aiPlayers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
