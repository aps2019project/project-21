package controller.menus;

import controller.InputScanner;
import controller.request.AccountMenuRequest;
import controller.request.Request;
import models.Player;

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
                showLeaderBoard();
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
                help();
                break;
            case SHOW_MENU:
                showMenu();
                break;
            case EXIT:
                MenuManager.getInstance().changeMenu(MenuType.EXIT);
                break;
        }
    }

    private void login() {

    }

    private void createAccount() {
        String userName = request.getCommandArguments().get(0);
        String password = InputScanner.nextLine();
        Player.setCurrentPlayer(Player.createAccount(userName, password));
    }

    private void logout() {
        Player.setCurrentPlayer(null);
    }

    private void showLeaderBoard() {

    }

    private void save() {

    }

    private void help() {

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
        MenuManager.getInstance().changeMenu(MenuType.MAIN_MENU);
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
