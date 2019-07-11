package controller.menus;

import models.Deck;
import models.Item.Usable;
import models.Player;
import models.card.Card;
import models.card.Hero;
import view.View;

public class CollectionMenu extends Menu {
    private static final long serialVersionUID = 6529685098267757002L;

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

    public void createDeck(String name) {
        if (Player.getCurrentPlayer().getCollection().hasThis(name)) {
            View.printMessage(view.Message.DECK_ALREADY_EXISTS);
            return;
        }
        Player.getCurrentPlayer().createDeck(name);
    }

    public void deleteDeck(String deckName) {
        if (!Player.getCurrentPlayer().getCollection().hasThis(deckName)) {
            View.printMessage(view.Message.NO_SUCH_DECK);
            return;
        }
        Player.getCurrentPlayer().deleteDeck(deckName);
    }

    public void addCardToDeck(int collectionID, String deckName, String cardName) {
        Deck deck = Player.getCurrentPlayer().getCollection().getDeck(deckName);
        if (deck == null) {
            View.printMessage(view.Message.NO_SUCH_DECK);
            return;
        }
        Card card = Player.getCurrentPlayer().getCollection().getCardByCollectionID(collectionID);
        if (card == null) {
            card = Player.getCurrentPlayer().getCollection().getCardByName(cardName);
            if (card == null){
                View.printMessage(view.Message.CARD_IS_NOT_IN_COLLECTION);
                return;
            }
        }
        if (deck.hasThisCard(card)) {
            View.printMessage(view.Message.ALREADY_IN_DECK);
            return;
        }
        if (card.getClass().equals(Hero.class) && deck.hasHero()) {
            View.printMessage(view.Message.DECK_HAS_HERO);
            return;
        }
        if (card.getClass().equals(Usable.class) && deck.hasUsable()) {
            View.printMessage(view.Message.DECK_HAS_ITEM);
            return;
        }
        if (deck.isFull()) {
            deck.removeRandom();
//            View.printMessage(view.Message.DECK_IS_FULL);
//            return;
        }
        if (card.getClass().equals(Hero.class)
                && Player.getCurrentPlayer().getCollection().isThisHeroInADeck((Hero) card)) {
            View.printMessage(view.Message.HERO_IS_USED_IN_A_DECK);
            return;
        }
        deck.addCard(card);
        View.printMessage(view.Message.ADDED_SUCCESSFULLY);
    }

    public void removeCardFromDeck(int collectionID, String deckName) {
        Deck deck = Player.getCurrentPlayer().getCollection().getDeck(deckName);
        if (deck == null) {
            View.printMessage(view.Message.NO_SUCH_DECK);
            return;
        }
        Card card = deck.getCardByCollectionID(collectionID);
        if (card == null) {
            View.printMessage(view.Message.CARD_IS_NOT_IN_DECK);
            return;
        }
        deck.remove(card);
        View.printMessage(view.Message.DELETED_SUCCESSFULLY);
    }

    public void selectDeck(String name) {
        Deck deck = Player.getCurrentPlayer().getCollection().getDeck(name);
        if (deck == null) {
            View.printMessage(view.Message.NO_SUCH_DECK);
            return;
        }
        if (!deck.isValid()) {
            View.printMessage(view.Message.DECK_CANNOT_BE_MAIN);
            return;
        }
        Player.getCurrentPlayer().setMainDeck(deck);
        View.popup("Deck " + name + " selected as main deck.");
    }
}
