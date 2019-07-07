package controller.menus;

import models.Player;
import network.Client;
import network.message.CreateAccountRequest;
import network.message.LoginRequest;
import network.message.Request;
import view.Message;

public class AccountMenu extends Menu {
    private static AccountMenu instance = new AccountMenu();

    public static AccountMenu getInstance() {
        return instance;
    }

    private AccountMenu() {
    }

    public void login(String username, String password) {
        LoginRequest loginMessage = new LoginRequest(username, password);
        Client.write(loginMessage);
    }

    public void createAccount(String username, String password) {
        if (Player.hasAnyoneLoggedIn()) {
            view.printMessage(Message.YOU_MUST_LOGOUT);
            return;
        }
        if (username.equals("")) {
            view.printMessage(Message.INVALID_USERNAME);
            return;
        }
        if (password.equals("")) {
            view.printMessage(Message.PASSWORD_EMPTY);
            return;
        }
        CreateAccountRequest createAccountMessage = new CreateAccountRequest(username, password);
        Client.write(createAccountMessage);
    }

    public void logout() {
        Request logoutRequest = Request.makeLogout();
        Client.write(logoutRequest);
        Player.logout();
    }

    public void save() {
        if (!Player.hasAnyoneLoggedIn()) {
            view.printMessage(Message.YOU_MUST_LOG_IN);
            return;
        }
        Client.write(Request.makePlayer(Player.getCurrentPlayer()));
    }

    public void hesoyam() {
        if (Player.hasAnyoneLoggedIn())
            Player.getCurrentPlayer().hesoyam();
    }
}
