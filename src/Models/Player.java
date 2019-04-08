package Models;

import Models.Match.Match;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private static List<Player> players = new ArrayList<>();
    private String username;
    private Collection collection;
    private int drake;
    private List<Match> matchHistory = new ArrayList<>();
    private List<Deck> decks = new ArrayList<>();
    private Deck mainDeck;

    public Player(String username) {
        this.drake = 15000;
        this.username = username;
    }

    void showMatchHistory() {

    }

    public void addPlayer(Player player){
        players.add(player);
    }
}

