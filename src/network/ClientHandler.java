package network;

import javafx.util.Pair;
import models.GlobalChat;
import models.Player;
import network.message.CreateAccountMessage;
import network.message.LoginMessage;
import network.message.Message;
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
    private Message lastMsg;
    private String currentAuthToken;

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
        write(Message.makeGlobalChat(GlobalChat.getInstance()));
        while (!client.isClosed()) {
            try {
                Message message = read();
                lastMsg = message;
                if (message != null)
                    System.out.println("message received:  " + message.getMsgType());
                handleMessage(message);
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

    private void handleMessage(Message message) {
        if (message == null)
            return;
        switch (message.getMsgType()) {
            case LOGIN:
                LoginMessage loginMsg = (LoginMessage) message;
                if (!Player.hasThisPlayer(loginMsg.getUsername())) {
                    sendMessage(view.Message.INVALID_USERNAME);
                    return;
                }
                if (!Player.login(loginMsg.getUsername(), loginMsg.getPassword()))
                    sendMessage(view.Message.LOGIN_FAILED);
                else {
                    Player player = Player.getPlayerByUsername(loginMsg.getUsername());
                    if (player != null) {
                        write(Message.makeAccount(player));
                        currentAuthToken = player.getAuthToken();
                        System.out.println(player.getUsername() + " logged in.");
                    }
                }
                break;
            case LOGOUT:
                Player.logout(message.getAuthToken());
                currentAuthToken = null;
                break;
            case CREATE_ACCOUNT:
                CreateAccountMessage createAccountMsg = (CreateAccountMessage) message;
                if (Player.hasThisPlayer(createAccountMsg.getUsername())) {
                    sendMessage(view.Message.USERNAME_IS_TAKEN);
                    return;
                }
                Player.createAccount(createAccountMsg.getUsername(), createAccountMsg.getPassword());
                Player player = Player.getPlayerByUsername(createAccountMsg.getUsername());
                if (player != null) {
                    write(Message.makeAccount(player));
                    currentAuthToken = player.getAuthToken();
                    System.out.println(player.getUsername() + " created account.");
                }
                break;
            case PLAYER:
                Player.savePlayer((Player) message.getObj());
                break;
            case GLOBAL_CHAT_MESSAGE:
                Pair<String, String> msgPair = (Pair<String, String>) message.getObj();
                GlobalChat.getInstance().addMessage(msgPair);
                GlobalChat.save();
                sendGlobalChatMessageToOthers();
                break;
        }
    }

    private void sendGlobalChatMessageToOthers() { // what if two clients are for one user?
        for (ClientHandler cl : clientHandlers)
            if (!currentAuthToken.equals(cl.currentAuthToken))
                cl.write(Message.makeGlobalChatMessage(GlobalChat.getInstance()));
    }

    private void sendMessage(view.Message msg) {
        write(Message.makeMessage(msg.getMessage()));
    }

    private void write(Message message) {
        System.out.println("writing...");
        try {
            oos.writeObject(message);
            oos.flush();
        } catch (IOException ex) {
            View.printThrowable(ex);
        }
    }

    private Message read() throws Exception {
        System.out.println("reading...");
        return (Message) ois.readObject();
    }
}
