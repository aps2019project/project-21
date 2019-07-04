package network;

import javafx.util.Pair;
import models.GlobalChat;
import models.Player;
import models.match.MatchRequest;
import network.message.CreateAccountRequest;
import network.message.LoginRequest;
import network.message.Request;
import view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler extends Thread {
    private static List<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket client;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Request lastMsg;
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
                lastMsg = request;
                if (request != null)
                    System.out.println("request received:  " + request.getReqType());
                handleRequest(request);
            } catch (Exception ex) {
                View.printThrowable(ex);
                break;
            }
        }
        try {
            client.close();
            ois.close();
            oos.close();
        } catch (IOException ex) {
            View.printThrowable(ex);
        }
        if (lastMsg != null && lastMsg.getAuthToken() != null)
            Player.logout(lastMsg.getAuthToken());
        clientHandlers.remove(this);
    }

    private void handleRequest(Request request) {
        if (request == null)
            return;
        if (matchHandler != null)
            matchHandler.addRequest(this, request);
        else
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
                            write(Request.makeAccount(player));
                            currentAuthToken = player.getAuthToken();
                            System.out.println(player.getUsername() + " logged in.");
                        }
                    }
                    break;
                case LOGOUT:
                    Player.logout(request.getAuthToken());
                    currentAuthToken = null;
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
                        write(Request.makeAccount(player));
                        currentAuthToken = player.getAuthToken();
                        System.out.println(player.getUsername() + " created account.");
                    }
                    break;
                case PLAYER:
                    Player.savePlayer((Player) request.getObj());
                    break;
                case GLOBAL_CHAT_MESSAGE:
                    Pair<String, String> msgPair = (Pair<String, String>) request.getObj();
                    GlobalChat.getInstance().addMessage(msgPair);
                    GlobalChat.save();
                    sendGlobalChatMessageToOthers();
                    break;
                case MATCH_REQUEST:
                    matchRequest = (MatchRequest) request.getObj();
                    findMatch();
                    break;
                case READY:
                    matchHandler.addRequest(this, request);
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
        MatchHandler matchHandler = new MatchHandler(this, other, matchRequest);
        this.matchHandler = matchHandler;
        other.matchHandler = matchHandler;
        this.write(Request.makeIntroduceOppRequest(other.getPlayer().getUsername()));
        other.write(Request.makeIntroduceOppRequest(this.getPlayer().getUsername()));
        matchHandler.start();
    }

    private void sendGlobalChatMessageToOthers() { // what if two clients are for one user?
        for (ClientHandler cl : clientHandlers)
            if (!currentAuthToken.equals(cl.currentAuthToken))
                cl.write(Request.makeGlobalChatMessage(GlobalChat.getInstance()));
    }

    private void sendMessage(view.Message msg) {
        write(Request.makeMessage(msg.getMessage()));
    }

    private Player getPlayer() {
        return Player.getPlayerByAuthToken(currentAuthToken);
    }

    private void write(Request request) {
        System.out.println("writing...");
        try {
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
