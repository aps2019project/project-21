package controller.menus;

import controller.InputScanner;
import controller.request.AccountMenuRequest;
import json.CardMaker;
import models.Player;
import view.ErrorMode;
import view.View;

public class AccountMenu extends Menu {
    private static AccountMenu instance = new AccountMenu();

    static Menu getInstance() {
        return instance;
    }

    private AccountMenu() {

    }

    void main() {

        showMenu();

        request = new AccountMenuRequest();

        request.getNewCommand();

        request.extractType();

        switch (request.getType()) {
            case CREATE_ACCOUNT:
                createAccount();
                break;
            case LOGIN:
                login();
                break;
            case SHOW_LEADERBOARD:
                view.scoreBoard();
                break;
            case SAVE:
                save();
                break;
            case LOGOUT:
                logout();
                break;
            case MAIN_MENU:
                gotoMainMenu();
                break;
            case HELP:
                showMenu();
                break;
            case SHOW_MENU:
                showMenu();
                break;
            case BACK:
                break;
            case EXIT:
                exit();
                break;
        }
    }

    private void login() {
        String username = request.getCommandArguments().get(0);
        if (!Player.hasThisPlayer(username)) {
            View.getInstance().printError(ErrorMode.INVALID_USERNAME);
            return;
        }
        System.out.println("Password: ");
        String password = InputScanner.nextLine();
        boolean isLoginSuccessful = Player.login(username, password);
        if (!isLoginSuccessful)
            view.printError(ErrorMode.LOGIN_FAILED);
    }

    private void createAccount() {
        String username = request.getCommandArguments().get(0);
        if (Player.hasThisPlayer(username)) {
            View.getInstance().printError(ErrorMode.USERNAME_IS_TAKEN);
            return;
        }
        System.out.println("Set Password:");
        String password = InputScanner.nextLine();
        Player.createAccount(username, password);
    }

    private void logout() {
        Player.setCurrentPlayer(null);
    }

    private void save() {
        if (Player.getCurrentPlayer() == null) {
            view.printError(ErrorMode.YOU_MUST_LOG_IN);
            return;
        }
        Player.savePlayer();
    }

    protected void showMenu() {
        System.out.println("-------AccountMenu-------");
        if (Player.getCurrentPlayer() != null)
            System.out.println("logged in as " + Player.getCurrentPlayer().getUsername());
        System.out.println("options:");
        System.out.println("1. create account");
        System.out.println("2. login");
        System.out.println("3. show leaderboard");
        System.out.println("4. save");
        System.out.println("5. logout");
        System.out.println("6. main menu");
        System.out.println("7. help");
        System.out.println("8. exit");
    }

    private void gotoMainMenu() {
        if (Player.getCurrentPlayer() == null) {
            view.printError(ErrorMode.YOU_MUST_LOG_IN);
            return;
        }
        MenuManager.getInstance().changeMenu(MenuType.MAIN_MENU);
    }
}
