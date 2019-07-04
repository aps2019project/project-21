package controller.menus;

import models.GlobalChat;
import models.Player;
import network.Client;
import network.message.Message;

public class GlobalChatMenu extends Menu {
    private static GlobalChatMenu instance = new GlobalChatMenu();

    public static GlobalChatMenu getInstance() {
        return instance;
    }

    private GlobalChatMenu() {
    }

    public void addMessage(String message) {
        if (!Player.hasAnyoneLoggedIn())
            return;
        GlobalChat.getInstance().addMessage(Player.getCurrentPlayer().getUsername(), message);
        Client.write(Message.makeGlobalChatMessage(GlobalChat.getInstance()));
    }
}
