package network;

import json.Initializer;
import models.GlobalChat;
import models.Player;
import view.View;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Host {
    public static void main(String[] args) {
        Host host = new Host();
        host.run();
    }

    private ServerSocket server;

    private void initialize() {
        new Thread(() -> {
            try {
                Player.addPlayer(Initializer.initPlayers());
                GlobalChat.init(Initializer.initGlobalChat());
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
