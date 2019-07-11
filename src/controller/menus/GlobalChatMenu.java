package controller.menus;

import models.GlobalChat;
import models.Player;
import network.Client;
import network.message.Request;

public class GlobalChatMenu extends Menu {
    private static final long serialVersionUID = 6529685098267757003L;

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
        Client.write(Request.makeGlobalChatMessage(GlobalChat.getInstance()));
    }
}
