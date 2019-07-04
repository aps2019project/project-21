package network;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;
import json.Initializer;
import models.GlobalChat;
import models.Player;
import network.message.Request;
import view.View;
import view.WaitingForOppView;

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

        Thread initializer = new Thread(Initializer::main);
        initializer.start();

        Thread contactHost = new Thread(Client::receiveMessages);
        contactHost.start();

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
            Request request = read();
            if (request != null)
                System.out.println("request received:  " + request.getReqType());
            handleMessage(request);
        }
        try {
            socket.close();
            oos.close();
            ois.close();
        } catch (IOException ex) {
            View.printThrowable(ex);
        }
    }

    private static void handleMessage(Request request) {
        if (request == null)
            return;
        switch (request.getReqType()) {
            case PLAYER:
                Player player = (Player) request.getObj();
                Player.addPlayer(player);
                Player.setCurrentPlayer(player);
                break;
            case GLOBAL_CHAT:
                GlobalChat.init((GlobalChat) request.getObj());
                break;
            case GLOBAL_CHAT_MESSAGE:
                GlobalChat.getInstance().addMessage((Pair<String, String>) request.getObj());
                break;
            case MESSAGE:
                String msg = (String) request.getObj();
                View.getInstance().addPopupMessage(msg);
                break;
            case INTRODUCE_OPP:
                WaitingForOppView.getCurrent().takeOppName((String) request.getObj());
                break;
        }
    }

    public static void write(Request request) {
        System.out.println("writing...");
        try {
            if (Player.hasAnyoneLoggedIn())
                request.setAuthToken(Player.getCurrentPlayer().getAuthToken());
            oos.writeObject(request);
            oos.flush();
        } catch (IOException ex) {
            View.printThrowable(ex);
        }
    }

    private static Request read() {
        System.out.println("reading...");
        try {
            return (Request) ois.readObject();
        } catch (Exception ex) {
            View.printThrowable(ex);
            return null;
        }
    }
}
