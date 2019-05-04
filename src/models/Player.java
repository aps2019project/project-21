package models;

import json.CardMaker;
import models.match.Match;
import view.ErrorMode;
import view.View;

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
    private int wins;
    private int cardCurrentID;

    public int getCardCurrentID() {
        return cardCurrentID;
    }

    public void setWins() {

    }

    public int getWins() {
        return wins;
    }

    public void setCardCurrentID(int cardCurrentID) {
        this.cardCurrentID = cardCurrentID;
    }

    public Player(String username, String password) {
        this.wins = 0;
        this.drake = 15000;
        this.username = username;
        this.password = password;
        this.cardCurrentID = 0;
    }

    void showMatchHistory() {

    }

    public static void createAccount(String username, String password) {
        if (hasThisPlayer(username)) {
            View.getInstance().printError(ErrorMode.USERNAME_IS_TAKEN);
            return;
        }
        Player newPlayer = new Player(username, password);
        players.add(newPlayer);
        savePlayer(newPlayer);
        setCurrentPlayer(newPlayer);
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

    public static Player getPlayerByUsername(String username) {
        for (Player player : players)
            if (player.username.equals(username))
                return player;
        return null;
    }

    public static boolean hasThisPlayer(String username) {
        return getPlayerByUsername(username) != null;
    }

    public static boolean login(String username, String password) {
        Player player = getPlayerByUsername(username);
        if (player == null)
            return false;
        if (!player.password.equals(password))
            return false;
        setCurrentPlayer(player);
        return true;
    }

    public boolean compareTwoPlayer(Player player) {
        //after run should check
        if (player.getWins() > this.wins) {
            return true;
        } else if (player.getWins() < this.wins) {
            return false;
        } else if (this.username.compareTo(player.getUsername()) < 0) {
            return true;
        }
        return false;
    }

    public static void addPlayer(Player player) {
        if (player == null)
            return;
        players.add(player);
    }

    public static void addPlayer(List<Player> players) {
        if (players == null)
            return;
        for (Player player : players)
            addPlayer(player);
    }

    public static void savePlayer(Player player) {
        CardMaker.saveToFile(player);
    }

    public static void savePlayer() {
        if (currentPlayer == null)
            return;
        CardMaker.saveToFile(currentPlayer);
    }

}

