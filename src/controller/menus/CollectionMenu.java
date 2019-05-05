package controller.menus;

import controller.request.MainMenuRequest;
import models.Deck;
import models.Item.Item;
import models.Player;
import models.card.Card;
import models.card.Hero;
import view.View;

public class CollectionMenu extends Menu {
    private static CollectionMenu instance = new CollectionMenu();

    static Menu getInstance() {
        return instance;
    }

    private CollectionMenu() {

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

    public void show() {
        view.showCollection(Player.getCurrentPlayer().getCollection());
    }

    public void search() {
        Item search = Item.getItemByName(request.getCommandArguments().get(0));
        boolean found = true;
        for (Item item : Player.getCurrentPlayer().getCollection().getUsables()) {
            if (item.twoItemAreSame(search)) {
                found = false;
                System.out.println("Item founded!");
                System.out.println(search.getId());
            }
        }
        Card originalCard = Card.getCardByID(request.getCommandArguments().get(0));
        if (found) {
            for (Card card : Player.getCurrentPlayer().getCollection().getCards()) {
                if (card.TwoCardAreSame(originalCard)) {
                    System.out.println("Card founded!");
                    System.out.println(originalCard.getId());
                }
            }
        }
    }

    public void save() {

    }

    public void createDeck() {
        Deck deck = new Deck(request.getCommandArguments().get(0));
        Player.getCurrentPlayer().getCollection().addDeck(deck);
    }

    public void deleteDeck() {
        Deck deck = new Deck(request.getCommandArguments().get(0));
        Player.getCurrentPlayer().getCollection().deleteDeck(deck);
    }

    public void addCardToDeck() {
        Card card = Card.getCardByID(request.getCommandArguments().get(0));
        Object object = card;
        if (card == null) {
            Item item = Item.getItemByName(request.getCommandArguments().get(0));
            object = item;
            if (item == null) {
                Hero hero = Hero.getHeroByID(request.getCommandArguments().get(0));
                object = hero;
                if (hero == null) {
                    System.out.println();
                }
            }
        }
        if (object != null) {
            Player.getCurrentPlayer().getCollection().searchDeck(request.getCommandArguments().get(1)).addObject(object);
        }
    }

    public void removeCardToDeck() {
        Card card = Card.getCardByID(request.getCommandArguments().get(0));
        Object object = card;
        if (card == null) {
            Item item = Item.getItemByName(request.getCommandArguments().get(0));
            object = item;
            if (item == null) {
                Hero hero = Hero.getHeroByID(request.getCommandArguments().get(0));
                object = hero;
                if (hero == null) {
                    System.out.println();
                }
            }
        }
        if (object != null) {
            Player.getCurrentPlayer().getCollection().searchDeck(request.getCommandArguments().get(1)).deleteObject(object);
        }
    }


    public boolean isDeckValid() {
        Deck deck = Player.getCurrentPlayer().getCollection().searchDeck(request.getCommandArguments().get(0));
        if (deck != null) {
            return deck.deckIsValid();
        }
        return false;
    }

    public void selectDeck() {
        Deck deck = Player.getCurrentPlayer().getCollection().searchDeck(request.getCommandArguments().get(0));
        Player.getCurrentPlayer().getCollection().setMainDeck(deck);
    }

    public void showAllDecks() {
        for (Deck deck : Player.getCurrentPlayer().getCollection().getDecks()) {
            view.showDeck(deck);
        }
    }

    public void showDeck() {
        Deck deck = Player.getCurrentPlayer().getCollection().searchDeck(request.getCommandArguments().get(0));
        if (deck != null) {
            view.showDeck(deck);
        }
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
}
