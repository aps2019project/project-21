package controller.menus;

import controller.request.CollectionMenuRequest;
import json.CardMaker;
import models.Deck;
import models.Item.Usable;
import models.Player;
import models.card.Card;
import models.card.Hero;
import view.ErrorMode;

import java.util.List;

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

        request = new CollectionMenuRequest();

        request.getNewCommand();

        request.extractType();

        switch (request.getType()) {
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
                removeCardFromDeck();
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
            case SHOW_MENU:
                showMenu();
                break;
            case BACK:
                back();
                break;
            case EXIT:
                exit();
                break;
            case INVALID:
                invalidCommand();
                break;
        }
    }

    private void show() {
        view.showCollection(Player.getCurrentPlayer().getCollection());
    }

    private void search() {
        String name = request.getCommandArguments().get(0);
        List<Card> cards = Card.getAllCardByName(name,
                Player.getCurrentPlayer().getCollection().getCards());
        if (cards.isEmpty()) {
            view.printError(ErrorMode.NO_CARD_WITH_THIS_NAME);
            return;
        }
        for (Card card : cards)
            System.out.println("collectionID: " + card.getCollectionID());
    }

    private void save() {
        CardMaker.saveToFile(Player.getCurrentPlayer());
    }

    private void createDeck() {
        String name = request.getCommandArguments().get(0);
        if (Player.getCurrentPlayer().getCollection().hasThis(name)) {
            view.printError(ErrorMode.DECK_ALREADY_EXISTS);
            return;
        }
        Player.getCurrentPlayer().createDeck(name);
    }

    private void deleteDeck() {
        String deckName = request.getCommandArguments().get(0);
        if (!Player.getCurrentPlayer().getCollection().hasThis(deckName)) {
            view.printError(ErrorMode.NO_SUCH_DECK);
            return;
        }
        Player.getCurrentPlayer().deleteDeck(deckName);
    }

    private void addCardToDeck() {
        int collectionID = Integer.parseInt(request.getCommandArguments().get(0));
        String deckName = request.getCommandArguments().get(1);
        Deck deck = Player.getCurrentPlayer().getCollection().getDeck(deckName);
        if (deck == null) {
            view.printError(ErrorMode.NO_SUCH_DECK);
            return;
        }
        Card card = Player.getCurrentPlayer().getCollection().getCardByCollectionID(collectionID);
        if (card == null) {
            view.printError(ErrorMode.CARD_IS_NOT_IN_COLLECTION);
            return;
        }
        if (deck.hasThisCard(card)) {
            view.printError(ErrorMode.ALREADY_IN_DECK);
            return;
        }
        if (card.getClass().equals(Hero.class) && deck.hasHero()) {
            view.printError(ErrorMode.DECK_HAS_HERO);
            return;
        }
        if (card.getClass().equals(Usable.class) && deck.hasUsable()) {
            view.printError(ErrorMode.DECK_HAS_ITEM);
            return;
        }
        if (deck.isFull()) {
            view.printError(ErrorMode.DECK_IS_FULL);
            return;
        }
        if (card.getClass().equals(Hero.class)
                && Player.getCurrentPlayer().getCollection().isThisHeroInADeck((Hero) card)) {
            view.printError(ErrorMode.HERO_IS_USED_IN_A_DECK);
            return;
        }
        deck.addCard(card);
        view.printError(ErrorMode.ADDED_SUCCESSFULLY);
    }

    private void removeCardFromDeck() {
        int collectionID = Integer.parseInt(request.getCommandArguments().get(0));
        String deckName = request.getCommandArguments().get(1);
        Deck deck = Player.getCurrentPlayer().getCollection().getDeck(deckName);
        if (deck == null) {
            view.printError(ErrorMode.NO_SUCH_DECK);
            return;
        }
        Card card = deck.getCardByCollectionID(collectionID);
        if (card == null) {
            view.printError(ErrorMode.CARD_IS_NOT_IN_DECK);
            return;
        }
        deck.remove(card);
        view.printError(ErrorMode.DELETED_SUCCESSFULLY);
    }


    private void isDeckValid() {
        String name = request.getCommandArguments().get(0);
        Deck deck = Player.getCurrentPlayer().getCollection().getDeck(name);
        if (deck == null) {
            view.printError(ErrorMode.NO_SUCH_DECK);
            return;
        }
        if (deck.isValid())
            view.printError(ErrorMode.DECK_IS_TOTALLY_VALID);
        else
            view.printError(ErrorMode.DECK_IS_NOT_VALID);
    }

    private void selectDeck() {
        String name = request.getCommandArguments().get(0);
        Deck deck = Player.getCurrentPlayer().getCollection().getDeck(name);
        if (deck == null) {
            view.printError(ErrorMode.NO_SUCH_DECK);
            return;
        }
        if (!deck.isValid()) {
            view.printError(ErrorMode.DECK_CANNOT_BE_MAIN);
            return;
        }
        Player.getCurrentPlayer().setMainDeck(deck);
    }

    private void showAllDecks() {
        view.showDecks(Player.getCurrentPlayer().getCollection());
    }

    private void showDeck() {
        String name = request.getCommandArguments().get(0);
        Deck deck = Player.getCurrentPlayer().getCollection().getDeck(name);
        if (deck == null) {
            view.printError(ErrorMode.NO_SUCH_DECK);
            return;
        }
        view.showDeck(deck);
    }

    protected void showMenu() {
        view.showMenu("Collection");
    }

    private void back() {
        MenuManager.getInstance().gotoMainMenu();
    }
}
