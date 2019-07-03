package network;

import models.Player;
import network.message.CreateAccountMessage;
import network.message.LoginMessage;
import network.message.Message;
import view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket client;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    ClientHandler(Socket client) {
        this.client = client;
        try {
            this.oos = new ObjectOutputStream(client.getOutputStream());
            this.ois = new ObjectInputStream(client.getInputStream());
        } catch (IOException ex) {
            View.printThrowable(ex);
        }
        System.out.println(12);
    }

    @Override
    public void run() {
        while (!client.isClosed()) {
            Message message = read();
            System.out.println("message received:  " + message.getMsgType());
            handleMessage(message);
        }
    }

    private void handleMessage(Message message) {
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
                        System.out.println(player.getUsername() + " logged in.");
                    }
                }
                break;
            case LOGOUT:
                Player.logout(message.getAuthToken());
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
                    System.out.println(player.getUsername() + " created account.");
                }
                break;
        }
    }

    private void sendMessage(view.Message msg) {
        write(new Message(msg.getMessage()));
    }

    private void write(Message message) {
        try {
            oos.writeObject(message);
            oos.flush();
        } catch (IOException ex) {
            View.printThrowable(ex);
        }
    }

    private Message read() {
        try {
            return (Message) ois.readObject();
        } catch (Exception ex) {
            View.printThrowable(ex);
            return null;
        }
    }
}
