package network;

import com.gilecode.yagson.YaGson;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Pair;
import json.Initializer;
import models.BattleAction;
import models.GlobalChat;
import models.Player;
import models.match.Match;
import network.message.Request;
import view.GlobalChatView;
import view.ShopView;
import view.View;
import view.WaitingForOppView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Client extends Application {
    private static final int DEFAULT_PORT = 8000;
    private static Socket socket;
    private static ObjectInputStream ois;
    private static ObjectOutputStream oos;

    public static void main(String[] args) {
        int port;

        try {
            String json = new String(Files.readAllBytes(Paths.get("src/json/port/port.config")), StandardCharsets.UTF_8);
            port = new YaGson().fromJson(json, int.class);
        } catch (IOException e) {
            View.printThrowable(e);
            port = DEFAULT_PORT;
        }

        try {
            socket = new Socket("localhost", port);
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

        finish();
    }

    private static void finish() {
        try {
            Platform.runLater(() -> View.popup("NOT CONNECTED TO HOST"));
            ois.close();
            socket.close();
            oos.close();
        } catch (IOException ex) {
            //
        }
    }

    @Override
    public void start(Stage primaryStage) {
        View.setPrimaryStage(primaryStage);
        View.run(primaryStage);
        primaryStage.show();
    }

    private static void receiveMessages() {
        while (!socket.isClosed()) {
            Request request = read();
            if (request != null)
                System.out.println("request received:  " + request.getReqType());
            handleMessage(request);
        }
        finish();
    }

    private static void handleMessage(Request request) {
        if (request == null)
            return;
        switch (request.getReqType()) {
            case PLAYER:
                Player player = (Player) request.getObj();
                Player.update(player);
                Player.setCurrentPlayer(player);
                Platform.runLater(ShopView.getInstance()::showCards);
                break;
            case GLOBAL_CHAT:
                GlobalChat.init((GlobalChat) request.getObj());
                break;
            case MESSAGE:
                String msg = (String) request.getObj();
                View.addPopupMessage(msg);
                break;
            case INTRODUCE_OPP:
                WaitingForOppView.getCurrent().takeOppName((String) request.getObj());
                break;
            case START_MATCH_FIRST:
                WaitingForOppView.getCurrent().setAmIFirst(true);
                break;
            case START_MATCH_SECOND:
                WaitingForOppView.getCurrent().setAmIFirst(false);
                break;
            case MATCH:
                WaitingForOppView.getCurrent().setMatch((Match) request.getObj());
                Platform.runLater(WaitingForOppView.getCurrent()::startMatch);
                break;
            case BATTLE_ACTION:
                Match.getCurrentMatch().getBattleView().addBattleAction((BattleAction) request.getObj());
                break;
            case WITHDRAW:
                Platform.runLater(View::back);
                break;
            case GLOBAL_CHAT_MESSAGE:
                GlobalChat.getInstance().addMessage((Pair<String, String>) request.getObj());
                break;
            case TAKE_ONLINE_USERS:
                GlobalChatView.setOnlineUsersName((List<String>) request.getObj());
                break;
        }
    }

    public static void write(Request request) {
        System.out.println("writing " + request.getReqType());
        try {
            if (Player.hasAnyoneLoggedIn())
                request.setAuthToken(Player.getCurrentPlayer().getAuthToken());
            oos.reset();
            oos.writeObject(request);
            oos.flush();
        } catch (IOException ex) {
            View.err("cannot write to host.");
        }
    }

    private static Request read() {
        System.out.println("reading...");
        try {
            return (Request) ois.readObject();
        } catch (Exception ex) {
            View.err("cannot read from host.");
            View.printThrowable(ex);
            finish();
            return null;
        }
    }
}
