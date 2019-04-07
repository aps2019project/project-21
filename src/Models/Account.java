package Models;

import Models.Match.Match;

import java.util.ArrayList;
import java.util.List;

public class Account {
    String username;
    Collection collection;
    int drake;
    List<Match> matchHistory = new ArrayList<>();
    List<Deck> decks = new ArrayList<>();
    Deck mainDeck;

    public Account(String username) {
        this.drake = 15000;
        this.username = username;
    }

    void showMatchHistory() {

    }
}

