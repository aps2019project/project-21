package network;

import com.gilecode.yagson.YaGson;
import javafx.application.Application;
import javafx.stage.Stage;
import models.GlobalChat;
import models.Item.Collectable;
import models.Item.Usable;
import models.Player;
import models.card.Hero;
import models.card.Minion;
import models.card.Spell;
import view.View;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static json.Initializer.*;

public class Host extends Application {
    private static final int DEFAULT_PORT = 8000;

    public static void main(String[] args) {
        Thread initializer = new Thread(Host::initialize);
        initializer.start();

        Host host = new Host();
        Thread contactClients = new Thread(host::run);
        contactClients.start();

        launch(args);

        host.finish();
    }

    @Override
    public void start(Stage primaryStage) {
        View.setPrimaryStage(primaryStage);
        View.runForHost(primaryStage);
        primaryStage.show();
    }

    private ServerSocket server;

    private void run() {
        int port;

        try {
            String json = new String(Files.readAllBytes(Paths.get("src/json/port/port.config")), StandardCharsets.UTF_8);
            port = new YaGson().fromJson(json, int.class);
        } catch (IOException e) {
            View.printThrowable(e);
            port = DEFAULT_PORT;
        }

        try {
            server = new ServerSocket(port);
            System.out.println("host is up.");

            while (!server.isClosed()) {
                Socket client = server.accept();
                System.out.println("client connected.");
                new ClientHandler(client).start();
            }
        } catch (IOException ex) {
            View.err("server socket closed.");
        }
    }

    private static void initialize() {
        try {
            Player.addPlayer(initPlayers());
            Player.setCurrentPlayer(Player.getPlayerByUsername("host"));
            Player.setAuthNulls();
            GlobalChat.init(initGlobalChat());
            Hero.addHero(initHeroes());
            Minion.addMinion(initMinions());
            Spell.addSpell(initSpells());
            Collectable.addCollectable(initCollectables());
            Usable.addUsable(initUsables());
        } catch (IOException ex) {
            View.printThrowable(ex);
        }
    }

    private void finish() {
        try {
            server.close();
        } catch (IOException ex) {
            //
        }
    }
}
