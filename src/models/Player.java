package models;

import models.match.Match;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private static List<Player> players = new ArrayList<>();
    private static Player currentPlayer;
    private String username;
    private String password;
    private Collection collection;
    private int drake;
    private List<Match> matchHistory = new ArrayList<>();
    private int cardCurrentID;

    public int getCardCurrentID() {
        return cardCurrentID;
    }

    public void setCardCurrentID(int cardCurrentID) {
        this.cardCurrentID = cardCurrentID;
    }

    public Player(String username, String password) {
        this.drake = 15000;
        this.username = username;
        this.password = password;
        this.cardCurrentID = 0;
    }

    void showMatchHistory() {

    }

    public static Player createAccount(String username, String password) {
        Player newPlayer = new Player(username, password);
        players.add(newPlayer);
        return newPlayer;
    }

    public static void login(String username, String password) {

    }

    public void logout() {

    }

    public static void showLeaderBoard() {

    }

    public static void help() {
    }

    public static List<Player> getPlayers() {
        return players;
    }

    public static void setPlayers(List<Player> players) {
        Player.players = players;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public int getDrake() {
        return drake;
    }

    public void setDrake(int drake) {
        this.drake = drake;
    }

    public List<Match> getMatchHistory() {
        return matchHistory;
    }

    public void setMatchHistory(List<Match> matchHistory) {
        this.matchHistory = matchHistory;
    }

    public static void setCurrentPlayer(Player currentPlayer) {
        Player.currentPlayer = currentPlayer;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }
}

