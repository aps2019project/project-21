package network;

import controller.menus.HostShopMenu;
import javafx.application.Platform;
import javafx.util.Pair;
import models.GlobalChat;
import models.Player;
import models.card.Card;
import models.match.MatchRequest;
import network.request.CreateAccountRequest;
import network.request.LoginRequest;
import network.request.Request;
import view.GlobalChatView;
import view.Scoreboard;
import view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClientHandler extends Thread {
    private static List<ClientHandler> clientHandlers = new ArrayList<>();

    private Socket client;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private String currentAuthToken;
    private MatchRequest matchRequest;
    private MatchHandler matchHandler;

    ClientHandler(Socket client) {
        this.client = client;
        try {
            this.oos = new ObjectOutputStream(client.getOutputStream());
            this.ois = new ObjectInputStream(client.getInputStream());
        } catch (IOException ex) {
            View.printThrowable(ex);
        }
        clientHandlers.add(this);
    }

    @Override
    public void run() {
        write(Request.makeGlobalChat(GlobalChat.getInstance()));
        while (!client.isClosed()) {
            try {
                Request request = read();
                if (request != null)
                    System.out.println("request received:  " + request.getReqType());
                handleRequest(request);
            } catch (Exception ex) {
                View.err("client disconnected.");
                break;
            }
        }
        finish();
    }

    private void finish() {
        try {
            client.close();
            ois.close();
            oos.close();
        } catch (IOException ex) {
            //
        }
        if (currentAuthToken != null)
            Player.logout(currentAuthToken);
        clientHandlers.remove(this);
        sendOnlineUsersToAll();
        sendScoreboardToAll();
        if (matchHandler != null)
            matchHandler.finish();
        Platform.runLater(Scoreboard::drawScoreboardForHost);
    }

    private void handleRequest(Request request) {
        if (request == null)
            return;
        if (matchHandler != null) {
            matchHandler.addRequest(this, request);
            return;
        }
        switch (request.getReqType()) {
            case LOGIN:
                LoginRequest loginMsg = (LoginRequest) request;
                if (!Player.hasThisPlayer(loginMsg.getUsername())) {
                    sendMessage(view.Message.INVALID_USERNAME);
                    return;
                }
                if (!Player.login(loginMsg.getUsername(), loginMsg.getPassword()))
                    sendMessage(view.Message.LOGIN_FAILED);
                else {
                    Player player = Player.getPlayerByUsername(loginMsg.getUsername());
                    if (player != null) {
                        sendOnlineUsersToAll();
                        sendScoreboardToAll();
                        write(Request.makeOnlineMatchesRequest(getOnlineMatches()));
                        write(Request.makePlayer(player));
                        currentAuthToken = player.getAuthToken();
                        System.out.println(player.getUsername() + " logged in.");
                        Platform.runLater(Scoreboard::drawScoreboardForHost);
                        sendCustomCards();
                    }
                }
                break;
            case LOGOUT:
                Player.logout(request.getAuthToken());
                sendOnlineUsersToAll();
                sendScoreboardToAll();
                currentAuthToken = null;
                Platform.runLater(Scoreboard::drawScoreboardForHost);
                break;
            case CREATE_ACCOUNT:
                CreateAccountRequest createAccountMsg = (CreateAccountRequest) request;
                if (Player.hasThisPlayer(createAccountMsg.getUsername())) {
                    sendMessage(view.Message.USERNAME_IS_TAKEN);
                    return;
                }
                Player.createAccount(createAccountMsg.getUsername(), createAccountMsg.getPassword());
                Player player = Player.getPlayerByUsername(createAccountMsg.getUsername());
                if (player != null) {
                    sendOnlineUsersToAll();
                    sendScoreboardToAll();
                    write(Request.makeOnlineMatchesRequest(getOnlineMatches()));
                    write(Request.makePlayer(player));
                    currentAuthToken = player.getAuthToken();
                    System.out.println(player.getUsername() + " created account.");
                    Platform.runLater(Scoreboard::drawScoreboardForHost);
                    sendCustomCards();
                }
                break;
            case GLOBAL_CHAT_MESSAGE:
                Pair<String, String> msgPair = (Pair<String, String>) request.getObj();
                GlobalChat.getInstance().addMessage(msgPair);
                GlobalChat.save();
                sendGlobalChatRequestToOthers();
                break;
            case MATCH_REQUEST:
                matchRequest = (MatchRequest) request.getObj();
                findMatch();
                break;
            case READY:
                matchHandler.addRequest(this, request);
                break;
            case WITHDRAW:
                setMatchNull();
                break;
            case TAKE_ONLINE_USERS:
                GlobalChatView.setOnlineUsersName((List<String>) request.getObj());
                break;
            case BUY:
                HostShopMenu.buy(getPlayer(), (String) request.getObj());
                break;
            case SELL:
                HostShopMenu.sell(getPlayer(), Integer.parseInt((String) request.getObj()));
                break;
            case HESOYAM:
                Player player1 = getPlayer();
                if (player1 != null)
                    player1.hesoyam();
                break;
            case PLAYER:  // receiving single player match result
                Player.saveOldPlayer((Player) request.getObj());
                sendScoreboardToAll();
                Platform.runLater(Scoreboard::drawScoreboardForHost);
                break;
            case WATCH_ONLINE:
                MatchHandler m = MatchHandler.getMatchHandlerByNames((Pair<String, String>) request.getObj());
                if (m != null)
                    m.addWatchingClient(this);
                break;
            default:
                View.err("request case not detected: " + request.getReqType());
                break;
        }
    }

    private void findMatch() {
        for (ClientHandler other : clientHandlers)
            if (!currentAuthToken.equals(other.currentAuthToken))
                if (this.matchRequest.equals(other.matchRequest)) {
                    prepareMatchHandler(other);
                    return;
                }
    }

    private void prepareMatchHandler(ClientHandler other) {
        int rand = new Random().nextInt(2);
        MatchHandler matchHandler;
        if (rand == 0)
            matchHandler = new MatchHandler(this, other, matchRequest);
        else
            matchHandler = new MatchHandler(other, this, matchRequest);
        this.matchHandler = matchHandler;
        other.matchHandler = matchHandler;
        this.write(Request.makeIntroduceOppRequest(other.getPlayer().getUsername()));
        other.write(Request.makeIntroduceOppRequest(this.getPlayer().getUsername()));
        matchHandler.start();
    }

    private void sendOnlineUsersToAll() {
        Request request = Request.makeOnlineUsersRequest(Player.getOnlineUsersName());
        sendRequestToAll(request);
        handleRequest(request);  // for host itself!
    }

    void sendScoreboardToAll() {
        sendRequestToAll(Request.makeScoreboardRequest());
    }

    void sendOnlineMatchesToAll() {
        sendRequestToAll(Request.makeOnlineMatchesRequest(getOnlineMatches()));
    }

    private List<Pair<String, String>> getOnlineMatches() {
        List<Pair<String, String>> matches = new ArrayList<>();
        for (MatchHandler m : MatchHandler.getMatchHandlers())
            matches.add(new Pair<>(m.getPlayer1().getUsername(), m.getPlayer2().getUsername()));
        return matches;
    }

    private void sendRequestToAll(Request request) {
        for (ClientHandler cl : clientHandlers)
            cl.write(request);
    }

    private void sendGlobalChatRequestToOthers() { // what if two clients are for one user?
        for (ClientHandler cl : clientHandlers)
            if (!currentAuthToken.equals(cl.currentAuthToken))
                cl.write(Request.makeGlobalChatMessage(GlobalChat.getInstance()));
    }

    public static void sendCardToAll(Card c) {
        for (ClientHandler cl : clientHandlers)
            cl.write(Request.makeCardRequest(c));
    }

    public void sendCustomCards() {
        for (Card custom : Card.getCustomCards())
            write(Request.makeCardRequest(custom));
    }

    private void sendMessage(view.Message msg) {
        write(Request.makeMessage(msg.getMessage()));
    }

    public Player getPlayer() {
        return Player.getPlayerByAuthToken(currentAuthToken);
    }

    void setMatchNull() {
        this.matchRequest = null;
        this.matchHandler = null;
    }

    public static List<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    public static void sendLimitToAll(String limit) {
        for (ClientHandler cl : clientHandlers)
            cl.write(Request.makeTimeLimitRequest(limit));
    }

    public void write(Request request) {
        System.out.println("writing...");
        try {
            oos.reset();
            oos.writeObject(request);
            oos.flush();
        } catch (IOException ex) {
            View.printThrowable(ex);
        }
    }

    private Request read() throws Exception {
        System.out.println("reading...");
        return (Request) ois.readObject();
    }
}
