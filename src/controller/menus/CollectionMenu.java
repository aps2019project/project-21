package controller.menus;

import json.CardMaker;
import models.Deck;
import models.Item.Usable;
import models.Player;
import models.card.Card;
import models.card.Hero;
import view.Message;
import view.View;

import java.util.List;

public class CollectionMenu extends Menu {
    private static CollectionMenu instance = new CollectionMenu();

    public static CollectionMenu getInstance() {
        return instance;
    }

    private CollectionMenu() {
    }

    public void exportDeck(String deckName) {
        if (!Player.hasAnyoneLoggedIn())
            return;
        Player.getCurrentPlayer().exportDeck(deckName);
    }

    public void importDeck(String filename) {
        if (!Player.hasAnyoneLoggedIn())
            return;
        Player.getCurrentPlayer().importDeck(filename);
    }

    private void search() {
        String name = request.getCommandArguments().get(0);
        List<Card> cards = Card.getAllCardByName(name,
                Player.getCurrentPlayer().getCollection().getCards());
        if (cards.isEmpty()) {
            view.printMessage(Message.NO_CARD_WITH_THIS_NAME);
            return;
        }
        for (Card card : cards)
            System.out.println("collectionID: " + card.getCollectionID());
    }

    private void save() {
        CardMaker.saveToFile(Player.getCurrentPlayer());
    }

    public void createDeck(String name) {
        if (Player.getCurrentPlayer().getCollection().hasThis(name)) {
            view.printMessage(Message.DECK_ALREADY_EXISTS);
            return;
        }
        Player.getCurrentPlayer().createDeck(name);
    }

    public void deleteDeck(String deckName) {
        if (!Player.getCurrentPlayer().getCollection().hasThis(deckName)) {
            view.printMessage(Message.NO_SUCH_DECK);
            return;
        }
        Player.getCurrentPlayer().deleteDeck(deckName);
    }

    public void addCardToDeck(int collectionID, String deckName) {
        Deck deck = Player.getCurrentPlayer().getCollection().getDeck(deckName);
        if (deck == null) {
            view.printMessage(Message.NO_SUCH_DECK);
            return;
        }
        Card card = Player.getCurrentPlayer().getCollection().getCardByCollectionID(collectionID);
        if (card == null) {
            view.printMessage(Message.CARD_IS_NOT_IN_COLLECTION);
            return;
        }
        if (deck.hasThisCard(card)) {
            view.printMessage(Message.ALREADY_IN_DECK);
            return;
        }
        if (card.getClass().equals(Hero.class) && deck.hasHero()) {
            view.printMessage(Message.DECK_HAS_HERO);
            return;
        }
        if (card.getClass().equals(Usable.class) && deck.hasUsable()) {
            view.printMessage(Message.DECK_HAS_ITEM);
            return;
        }
        if (deck.isFull()) {
            view.printMessage(Message.DECK_IS_FULL);
            return;
        }
        if (card.getClass().equals(Hero.class)
                && Player.getCurrentPlayer().getCollection().isThisHeroInADeck((Hero) card)) {
            view.printMessage(Message.HERO_IS_USED_IN_A_DECK);
            return;
        }
        deck.addCard(card);
        view.printMessage(Message.ADDED_SUCCESSFULLY);
    }

    public void removeCardFromDeck(int collectionID, String deckName) {
        Deck deck = Player.getCurrentPlayer().getCollection().getDeck(deckName);
        if (deck == null) {
            view.printMessage(Message.NO_SUCH_DECK);
            return;
        }
        Card card = deck.getCardByCollectionID(collectionID);
        if (card == null) {
            view.printMessage(Message.CARD_IS_NOT_IN_DECK);
            return;
        }
        deck.remove(card);
        view.printMessage(Message.DELETED_SUCCESSFULLY);
    }

    public void selectDeck(String name) {
        Deck deck = Player.getCurrentPlayer().getCollection().getDeck(name);
        if (deck == null) {
            view.printMessage(Message.NO_SUCH_DECK);
            return;
        }
        if (!deck.isValid()) {
            view.printMessage(Message.DECK_CANNOT_BE_MAIN);
            return;
        }
        Player.getCurrentPlayer().setMainDeck(deck);
        View.getInstance().popup("Deck " + name + " selected as main deck.");
    }
}
