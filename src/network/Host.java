package network;

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

import static json.Initializer.*;

public class Host {
    public static void main(String[] args) {
        Host host = new Host();
        host.run();
    }

    private ServerSocket server;

    private void initialize() {
        new Thread(() -> {
            try {
                Player.addPlayer(initPlayers());
                GlobalChat.init(initGlobalChat());
                Hero.addHero(initHeroes());
                Minion.addMinion(initMinions());
                Spell.addSpell(initSpells());
                Collectable.addCollectable(initCollectables());
                Usable.addUsable(initUsables());
            } catch (IOException ex) {
                View.printThrowable(ex);
            }
        }).start();
        Player.setAuthNulls();
    }

    private void run() {
        initialize();

        try {
            server = new ServerSocket(7777);
            System.out.println("host is up.");

            while (!server.isClosed()) {
                Socket client = server.accept();
                System.out.println("client connected.");
                new ClientHandler(client).start();
            }
        } catch (IOException ex) {
            View.printThrowable(ex);
        }
    }
}
