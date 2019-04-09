package controller.menu;

import controller.request.MainMenuRequest;
import controller.request.Request;
import models.Player;
import view.View;

public class CollectionMenu {
    private View view = new View();
    private Request request;

    public void main() {
        outerLoop:
        while (true) {
            showMenu();

            request = new MainMenuRequest();

            request.getNewCommand();

            request.checkSyntax();

            switch (request.getType()) {
                //  add cases
                case HELP:
                    break;
                case SHOW_MENU:
                    continue outerLoop;
                case EXIT:
                    break outerLoop;
            }
        }
    }

    private Player currentPlayer;

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void show() {

    }

    public void search() {

    }

    public void save() {

    }

    public void createDeck() {

    }

    public void deleteDeck() {

    }

    public void addCardToDeck() {

    }

    public void addItemToDeck() {

    }

    public void addHeroToDeck() {

    }

    public void removeCardToDeck() {

    }

    public void removeItemToDeck() {

    }

    public void removeHeroToDeck() {

    }

    public boolean isDeckValid() {
        return true;
    }

    public void selectDeck() {

    }

    public void showAllDecks() {

    }

    public void showDeck() {

    }

    public void help() {

    }

    private void showMenu() {

    }
}
