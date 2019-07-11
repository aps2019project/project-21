package controller.menus;

import models.Player;
import network.Client;
import network.request.CreateAccountRequest;
import network.request.LoginRequest;
import network.request.Request;
import view.View;

public class AccountMenu extends Menu {
    private static final long serialVersionUID = 6529685098267757000L;

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
            View.printMessage(view.Message.YOU_MUST_LOGOUT);
            return;
        }
        if (username.equals("")) {
            View.printMessage(view.Message.INVALID_USERNAME);
            return;
        }
        if (password.equals("")) {
            View.printMessage(view.Message.PASSWORD_EMPTY);
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
        if (!Player.hasAnyoneLoggedIn())
            View.printMessage(view.Message.YOU_MUST_LOG_IN);
    }

    public void hesoyam() {
        Client.write(Request.makeHesoyamRequest());
    }
}
