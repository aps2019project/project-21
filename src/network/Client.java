package network;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;
import json.Initializer;
import models.GlobalChat;
import models.Player;
import network.message.Message;
import view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Application {
    private static Socket socket;
    private static ObjectInputStream ois;
    private static ObjectOutputStream oos;

    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", 7777);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            View.printThrowable(ex);
        }
        System.out.println("connected to host.");

        new Thread(Initializer::main).start();

        new Thread(Client::receiveMessages).start();

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        View.getInstance().setPrimaryStage(primaryStage);
        View.getInstance().run();
        primaryStage.show();
    }

    private static void receiveMessages() {
        while (!socket.isClosed()) {
            Message message = read();
            if (message != null)
                System.out.println("message received:  " + message.getMsgType());
            handleMessage(message);
        }
        try {
            socket.close();
            oos.close();
            ois.close();
        } catch (IOException ex) {
            View.printThrowable(ex);
        }
    }

    private static void handleMessage(Message message) {
        if (message == null)
            return;
        switch (message.getMsgType()) {
            case PLAYER:
                Player player = (Player) message.getObj();
                Player.addPlayer(player);
                Player.setCurrentPlayer(player);
                break;
            case GLOBAL_CHAT:
                GlobalChat.init((GlobalChat) message.getObj());
                break;
            case GLOBAL_CHAT_MESSAGE:
                GlobalChat.getInstance().addMessage((Pair<String, String>) message.getObj());
                break;
            case MESSAGE:
                String msg = (String) message.getObj();
                View.getInstance().addpopupMessage(msg);
                break;
        }
    }

    public static void write(Message message) {
        System.out.println("writing...");
        try {
            if (Player.hasAnyoneLoggedIn())
                message.setAuthToken(Player.getCurrentPlayer().getAuthToken());
            oos.writeObject(message);
            oos.flush();
        } catch (IOException ex) {
            View.printThrowable(ex);
        }
    }

    private static Message read() {
        System.out.println("reading...");
        try {
            return (Message) ois.readObject();
        } catch (Exception ex) {
            View.printThrowable(ex);
            return null;
        }
    }
}
