package controller.menus;

import models.Player;
import network.Client;
import network.message.CreateAccountMessage;
import network.message.LoginMessage;
import view.Message;

public class AccountMenu extends Menu {
    private static AccountMenu instance = new AccountMenu();

    public static AccountMenu getInstance() {
        return instance;
    }

    private AccountMenu() {
    }

    public void login(String username, String password) {
        LoginMessage loginMessage = new LoginMessage(username, password);
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
        CreateAccountMessage createAccountMessage = new CreateAccountMessage(username, password);
        Client.write(createAccountMessage);
    }

    public void logout() {
        network.message.Message logoutMessage = network.message.Message.makeLogout();
        Client.write(logoutMessage);
        Player.logout();
//        Player.logout();
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
