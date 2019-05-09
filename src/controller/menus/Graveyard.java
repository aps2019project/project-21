package controller.menus;

import controller.request.GraveyardRequest;
import models.match.Match;

public class Graveyard extends Menu {
    private static Graveyard instance = new Graveyard();

    static Menu getInstance() {
        return instance;
    }

    private Graveyard() {

    }

    public void main() {
        if (showMenu) {
            showMenu();
            showMenu = false;
        }

        request = new GraveyardRequest();

        request.getNewCommand();

        request.extractType();

        switch (request.getType()) {
            case SHOW_INFO:
                showInfo();
                break;
            case SHOW_CARDS:
                showCards();
                break;
            case EXIT:
                exit();
                break;
            case BACK:
                back();
                break;
            case HELP:
                showMenu();
                break;
            case SHOW_MENU:
                showMenu();
                break;
            case INVALID:
                invalidCommand();
                break;
        }
    }

    protected void showMenu() {
        view.showMenu("Graveyard");
    }

    private void showInfo() {
        Match.getCurrentMatch().showCardInfoInGraveyard(request.getCommandArguments().get(0));
    }

    private void showCards() {
        Match.getCurrentMatch().showGraveyardCards();
    }

    private void back() {
        MenuManager.getInstance().gotoBattle();
    }

}
