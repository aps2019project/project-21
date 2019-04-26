package controller.menus;

import controller.request.MainMenuRequest;
import controller.request.Request;
import models.Player;
import view.View;

public class CollectionMenu extends Menu {
    private static CollectionMenu instance = new CollectionMenu();

    static Menu getInstance() {
        return instance;
    }

    private CollectionMenu() {

    }

    public void main() {
        showMenu();

        request = new MainMenuRequest();

        request.getNewCommand();

        request.extractType();

        switch (request.getType()) {
            //  add cases
            case HELP:
                break;
            case SHOW_MENU:
                break;
            case EXIT:
                break;
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

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
