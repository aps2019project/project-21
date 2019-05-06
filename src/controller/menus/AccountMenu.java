package controller.menus;

import controller.InputScanner;
import controller.request.AccountMenuRequest;
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
        if (showMenu) {
            showMenu();
            showMenu = false;
        }

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
            case HESOYAM:
                hesoyam();
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
            case INVALID:
                invalidCommand();
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
        else
            view.printError(ErrorMode.LOGIN_SUCCESSFUL);
    }

    private void createAccount() {
        if (Player.getCurrentPlayer() != null){
            view.printError(ErrorMode.YOU_MUST_LOGOUT);
            return;
        }
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
        view.showMenu("Account");
    }

    private void hesoyam() {
        if (Player.getCurrentPlayer() != null)
            Player.getCurrentPlayer().setDrake(Integer.MAX_VALUE);
    }

    private void gotoMainMenu() {
        if (Player.getCurrentPlayer() == null) {
            view.printError(ErrorMode.YOU_MUST_LOG_IN);
            return;
        }
        MenuManager.getInstance().gotoMainMenu();
    }
}
