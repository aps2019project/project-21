package models;

import json.CardMaker;
import models.Item.Usable;
import models.card.Card;
import models.match.Match;
import view.ErrorMode;
import view.View;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private static List<Player> players = new ArrayList<>();
    private static Player currentPlayer;
    private String username = "";
    private String password = "";
    private Collection collection = new Collection();
    private int drake;
    private List<Match> matchHistory = new ArrayList<>();
    private int wins;
    private int losses;
    private int cardCurrentID;

    public int getWins() {
        return wins;
    }

    public Player(String username, String password) {
        this.wins = 0;
        this.drake = 15000;
        this.username = username;
        this.password = password;
        this.cardCurrentID = 0;
    }

    public Player() {

    }

    public static boolean ifLoggedIN(){
        return currentPlayer != null;
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

    public static List<Player> getPlayers() {
        return players;
    }

    public String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
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

    private static void addPlayer(Player player) {
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

    private static void savePlayer(Player player) {
        CardMaker.saveToFile(player);
    }

    public static void savePlayer() {
        if (currentPlayer == null)
            return;
        CardMaker.saveToFile(currentPlayer);
    }

    public boolean hasLessThanThreeItems() {
        return this.collection.hasLessThanThreeItems();
    }

    public void buy(Card card) {
        if (card == null || drake < card.getPrice())
            return;
        if (card.getClass().equals(Usable.class) && !hasLessThanThreeItems())
            return;
        drake -= card.getPrice();
        card = CardMaker.deepCopy(card, Card.class);
        collection.addCard(card);
    }

    public void sell(Card card) {
        if (card == null)
            return;
        if (!collection.hasThisCard(card))
            return;
        collection.removeCard(card);
        drake += card.getPrice();
    }

    public void createDeck(String deckName) {
        collection.createDeck(deckName);
    }

    public void deleteDeck(String deckName) {
        collection.deleteDeck(deckName);
    }

    public void setMainDeck(Deck deck) {
        collection.setMainDeck(deck);
    }

    public boolean hasAValidMainDeck() {
        return collection.hasValidMainDeck();
    }

    public void incrementWins() {
        this.wins++;
    }

    public void incrementLosses() {
        this.losses++;
    }

    public void addToHistory(Match match) {
        if (match == null)
            return;
        this.matchHistory.add(match);
    }

    public void addDrake(int value) {
        this.drake += value;
    }
}

