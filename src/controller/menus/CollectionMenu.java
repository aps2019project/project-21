package controller.menus;

import controller.request.MainMenuRequest;
import controller.request.Request;
import models.Item.Item;
import models.Player;
import models.card.Card;
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
            case SHOW_COLLECTION:
                show();
                break;
            case SEARCH_COLLECTION:
                search();
                break;
            case SAVE:
                save();
                break;
            case CREATE_DECK:
                createDeck();
                break;
            case DELETE_DECK:
                deleteDeck();
                break;
            case ADD_TO_DECK:
                addCardToDeck();
                break;
            case REMOVE_FROM_DECK:
                removeCardToDeck();
                break;
            case VALIDATE_DECK:
                isDeckValid();
                break;
            case SELECT_DECK:
                selectDeck();
                break;
            case SHOW_ALL_DECKS:
                showAllDecks();
                break;
            case SHOW_DECK:
                showDeck();
                break;
            case HELP:
                showMenu();
                break;
        }
    }

    private Player currentPlayer;

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void show() {
        view.showCollection(Player.getCurrentPlayer().getCollection());
    }

    public void search() {
        Item search=Item.getItemByName(request.getCommandArguments().get(0));
        boolean found = true;
        for (Item item: Player.getCurrentPlayer().getCollection().getItems()){
            if (item.twoItemAreSame(search)){
                found = false;
                System.out.println("Item founded!");
                System.out.println(search.getId());
            }
        }
        Card originalCard= Card.getCardByID(request.getCommandArguments().get(0));
        if (found){
            for (Card card: Player.getCurrentPlayer().getCollection().getCards()){
                if (card.TwoCardAreSame(originalCard)){
                    System.out.println("Card founded!");
                    System.out.println(originalCard.getId());
                }
            }
        }
    }

    public void save() {

    }

    public void createDeck() {

    }

    public void deleteDeck() {

    }

    public void addCardToDeck() {

    }

    public void removeCardToDeck() {

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

    protected void showMenu() {
        System.out.println("----Collection----\n" +
                "options:\n" +
                "1 - exit\n" +
                "2 - show\n" +
                "3 - search [card name j item name]\n" +
                "4 - save\n" +
                "5 - create deck[deck name]\n" +
                "6 - delete deck [deck name]\n" +
                "7 - add [card id j card id j hero id] to deck [deck name]\n" +
                "8 - remove [card id j card idj hero id] from deck [deck name]\n" +
                "9 - validate deck [deck name]\n" +
                "10 - select deck [deck name]\n" +
                "11 - show all decks\n" +
                "12 - show deck [deck name]\n" +
                "13 - help");
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
