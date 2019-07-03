package controller.menus;

import models.Player;
import view.Message;

public class AccountMenu extends Menu {
    private static AccountMenu instance = new AccountMenu();

    public static AccountMenu getInstance() {
        return instance;
    }

    private AccountMenu() {
    }

    public void login(String username, String password) {
        if (!Player.hasThisPlayer(username)) {
            view.printMessage(Message.INVALID_USERNAME);
            return;
        }
        if (!Player.login(username, password))
            view.printMessage(Message.LOGIN_FAILED);
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
        if (Player.hasThisPlayer(username)) {
            view.printMessage(Message.USERNAME_IS_TAKEN);
            return;
        }
        Player.createAccount(username, password);
    }

    public void logout() {
        Player.logout();
    }

    public void save() {
        if (!Player.hasAnyoneLoggedIn()) {
            view.printMessage(Message.YOU_MUST_LOG_IN);
            return;
        }
        Player.savePlayer();
    }

    public void hesoyam() {
        if (Player.hasAnyoneLoggedIn())
            Player.getCurrentPlayer().hesoyam();
    }
}
