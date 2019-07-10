package models;

import javafx.application.Platform;
import json.CardMaker;
import models.Item.Usable;
import models.card.Card;
import models.match.GameMode;
import models.match.Match;
import network.ClientHandler;
import network.message.Request;
import view.HostShopView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Player implements Serializable {
    private static int loginCount = 0;
    private static List<Player> players = new ArrayList<>();
    private static Player currentPlayer;
    private String username = "";
    private String password = "";
    private Collection collection = new Collection();
    private int drake;
    private List<Match> matchHistory = new ArrayList<>();
    private int wins;
    private int losses;
    private String authToken;

    public Player(String username, String password) {
        this.wins = 0;
        this.drake = 15000;
        this.username = username;
        this.password = password;
    }

    public static List<Player> getPlayersSortedForScoreboard() {
        List<Player> list = new ArrayList<>(players);
        list.sort(Comparator.comparingInt(Player::getMultiplayerWins).reversed()
                .thenComparing(Player::getMultiplayerLosses).thenComparing(Player::getUsername));
        return list;
    }

    public boolean isOnline() {
        return authToken != null;
    }

    public static void setAuthNulls() {
        for (Player player : players)
            player.authToken = null;
    }

    private void takeAuthToken() {
        authToken = Integer.toString(loginCount++);
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public int getWins() {
        return wins;
    }

    public int getMultiplayerWins() {
        int ret = 0;
        for (Match match : matchHistory)
            if (match.getGameMode() == GameMode.MULTI_PLAYER)
                if (match.getWinner() != null)
                    if (this.username.equals(match.getWinner()))
                        ret++;
        return ret;
    }

    public int getMultiplayerLosses() {
        int ret = 0;
        for (Match match : matchHistory)
            if (match.getGameMode() == GameMode.MULTI_PLAYER)
                if (match.getLoser() != null)
                    if (this.username.equals(match.getLoser()))
                        ret++;
        return ret;
    }

    public Player() {
    }

    public static List<String> getOnlineUsersName() {
        List<String> usernames = new ArrayList<>();
        for (Player player : players)
            if (player.authToken != null)
                usernames.add(player.getUsername());
        return usernames;
    }

    public static void createAccount(String username, String password) {
        Player newPlayer = new Player(username, password);
        players.add(newPlayer);
        savePlayer(newPlayer);
        setCurrentPlayer(newPlayer);
        newPlayer.takeAuthToken();
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

    public void hesoyam() {
        drake = 20000000;
        this.getCL().write(Request.makePlayer(this));
    }

    public List<Match> getMatchHistory() {
        return matchHistory;
    }

    public static Player getPlayerByAuthToken(String authToken) {
        if (authToken == null)
            return null;
        for (Player player : players)
            if (authToken.equals(player.authToken))
                return player;
        return null;
    }

    public static void logout(String authToken) {
        Player player = getPlayerByAuthToken(authToken);
        if (player != null)
            player.authToken = null;
        logout();
        Player.savePlayer(player);
    }

    public static void logout() {
        currentPlayer = null;
    }

    public static void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static boolean hasAnyoneLoggedIn() {
        return currentPlayer != null;
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
        player.takeAuthToken();
        setCurrentPlayer(player);
        return true;
    }

    public static void update(Player player) {
        Player p = getPlayerByUsername(player.username);
        if (p == null) {
            addPlayer(player);
            return;
        }
        players.remove(p);
        addPlayer(player);
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

    public static void saveOldPlayer(Player player) {
        List<Player> copy = new ArrayList<>(players);
        for (Player p : copy)
            if (p.username.equals(player.username))
                players.remove(p);
        addPlayer(player);
        savePlayer(player);
    }

    public static void savePlayer() {
        if (currentPlayer == null)
            return;
        CardMaker.saveToFile(currentPlayer);
    }

    public boolean hasLessThanThreeItems() {
        return this.collection.hasLessThanThreeItems();
    }

    private ClientHandler getCL() {
        for (ClientHandler cl : ClientHandler.getClientHandlers())
            if (cl.getPlayer() != null && username.equals(cl.getPlayer().getUsername()))
                return cl;
        return null;
    }

    public void buy(Card card) {
        if (card == null || drake < card.getPrice())
            return;
        if (card.getClass().equals(Usable.class) && !hasLessThanThreeItems())
            return;
        if (card.getCount() <= 0) {
            this.getCL().write(Request.makeMessage("OUT OF STOCK!"));
            return;
        }
        drake -= card.getPrice();
        card.decreaseCount();
        CardMaker.saveToFile(card);
        card = CardMaker.deepCopy(card, Card.class);
        collection.addCard(card);
        this.getCL().write(Request.makeMessage("BUY SUCCESSFUL"));
        this.getCL().write(Request.makePlayer(this));
        savePlayer(this);
        Platform.runLater(HostShopView::drawCards);
    }

    public void sell(Card card) {
        if (card == null)
            return;
        if (!collection.hasThisCard(card))
            return;
        collection.removeCard(card);
        drake += card.getPrice();
        Card.getCardByName(card.getName()).increaseCount();  // todo: test this for custom cards.
        this.getCL().write(Request.makeMessage("SELL SUCCESSFUL"));
        this.getCL().write(Request.makePlayer(this));
        savePlayer(this);
        Platform.runLater(HostShopView::drawCards);
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
        if (matchHistory == null)
            matchHistory = new ArrayList<>();
        this.matchHistory.add(match);
    }

    public void addDrake(int value) {
        this.drake += value;
    }

    public void exportDeck(String deckName) {
        collection.exportDeck(deckName);
    }

    public void importDeck(String filename) {
        collection.importDeck(filename);
    }
}

