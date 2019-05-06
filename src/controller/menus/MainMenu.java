package controller.menus;

import controller.InputScanner;
import controller.request.MainMenuRequest;
import models.Player;
import models.match.GameMode;
import models.match.GameType;
import view.ErrorMode;

public class MainMenu extends Menu {
    private static MainMenu instance = new MainMenu();

    static Menu getInstance() {
        return instance;
    }

    private MainMenu() {

    }

    public void main() {
        if (showMenu) {
            showMenu();
            showMenu = false;
        }

        request = new MainMenuRequest();

        request.getNewCommand();

        request.extractType();

        switch (request.getType()) {
            case COLLECTION:
                collection();
                break;
            case SHOP:
                shop();
                break;
            case BATTLE:
                battle();
                break;
            case BACK:
                back();
                break;
            case HELP:
                help();
                break;
            case SHOW_MENU:
                showMenu();
                break;
            case EXIT:
                exit();
                break;
            case INVALID:
                invalidCommand();
                break;
        }
    }

    private void help() {

    }

    private void shop() {
        MenuManager.getInstance().gotoShop();
    }

    private void collection() {
        MenuManager.getInstance().gotoCollection();
    }

    protected void showMenu() {
        view.showMenu("MainMenu");
    }

    private void back() {
        MenuManager.getInstance().gotoAccount();
    }

    private void battle() {
        if (!Player.getCurrentPlayer().hasAValidMainDeck()) {
            view.printError(ErrorMode.MAIN_DECK_IS_INVALID);
            return;
        }
        if (createNewMatch())
            MenuManager.getInstance().gotoBattle();
    }

    private boolean createNewMatch() {
        System.out.println("choose match mode: (enter 1 or 2) \n" +
                "1. single player" +
                "2. multi player");
        String num = InputScanner.nextLine();
        if (!num.matches("[1|2]")) {
            invalidCommand();
            return false;
        }
        if (num.equals("1"))
            return createSinglePlayer();
        else
            return createMultiPlayer();
    }

    private boolean createSinglePlayer() {
        System.out.println("choose gameType: (enter 1 or 2):\n" +
                "1. story" +
                "2. custom game");
        String num = InputScanner.nextLine();
        if (!num.matches("[1|2]")) {
            invalidCommand();
            return false;
        }
        if (num.equals("1"))
            return createStoryMatch();
        else
            return createCustomMatch();
    }

    private boolean createStoryMatch() {
        System.out.println("choose on of 3 story matches below:\n");
        return true;
    }

    private boolean createCustomMatch() {
        return true;
    }

    private boolean createMultiPlayer() {
        return true;
    }


}
