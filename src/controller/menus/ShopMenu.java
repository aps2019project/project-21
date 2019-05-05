package controller.menus;

import controller.request.ShopMenuRequest;
import models.Item.Item;
import models.Player;
import models.card.Card;
import view.ErrorMode;

import java.util.List;

public class ShopMenu extends Menu {
    private static ShopMenu instance = new ShopMenu();

    static Menu getInstance() {
        return instance;
    }

    private ShopMenu() {

    }

    private List<Card> getCards() {
        return Card.getCards();
    }

    private List<Item> getItems() {
        return Item.getItems();
    }

    public void main() {
        if (showMenu) {
            showMenu();
            showMenu = false;
        }

        request = new ShopMenuRequest();

        request.getNewCommand();

        request.extractType();

        switch (request.getType()) {
            case SEARCH:
                search();
                break;
            case SEARCH_COLLECTION:
                searchCollection();
                break;
            case SHOW_COLLECTION:
                showCollection();
                break;
            case SHOW:
                show();
                break;
            case SELL:
                sell();
                break;
            case BUY:
                buy();
                break;
            case HELP:
                showMenu();
                break;
            case SHOW_MENU:
                showMenu();
                break;
            case BACK:
                MenuManager.getInstance().gotoMainMenu();
                break;
            case EXIT:
                exit();
                break;
        }
    }

    private void showCollection() {
        view.showCollection(Player.getCurrentPlayer().getCollection());
    }

    private void search() {
        String cardName = request.getCommandArguments().get(0);
        Card card = Card.getCardByName(cardName);
        if (card == null)
            view.printError(ErrorMode.NO_CARD_WITH_THIS_NAME);
        else
            System.out.println(card.getId());
    }

    private void searchCollection() {
        String cardName = request.getCommandArguments().get(0);
        List<Card> cards = Card.getAllCardByName(cardName, Player.getCurrentPlayer().getCollection().getCards());
        if (cards.isEmpty())
            view.printError(ErrorMode.CARD_IS_NOT_IN_COLLECTION);
        else
            for (Card card : cards)
                System.out.println(card.getCollectionID() + " ");
    }

    private void buy() {
        String cardName = request.getCommandArguments().get(0);
        Card card = Card.getCardByName(cardName);
        if (card == null) {
            view.printError(ErrorMode.NO_CARD_WITH_THIS_NAME);
            return;
        }
        if (Player.getCurrentPlayer().getDrake() < card.getPrice()) {
            view.printError(ErrorMode.NOT_ENOUGH_MONEY);
            return;
        }
        if (!Player.getCurrentPlayer().hasLessThanThreeItems()) {
            view.printError(ErrorMode.HAS_THREE_ITEMS);
            return;
        }
        Player.getCurrentPlayer().buy(card);
        view.printError(ErrorMode.BUY_SUCCESSFUL);
    }

    private void sell() {
        String collectionID = request.getCommandArguments().get(0);
        if (!collectionID.matches("\\d+")) {
            view.printError(ErrorMode.INVALID_CARD_ID);
            return;
        }
        Card card = Player.getCurrentPlayer().getCollection()
                .getCardByCollectionID(Integer.parseInt(collectionID));
        if (card == null) {
            view.printError(ErrorMode.YOU_DONT_HAVE_THIS_CARD);
            return;
        }
        Player.getCurrentPlayer().sell(card);
        view.printError(ErrorMode.SELL_SUCCESSFUL);
    }

    private void show() {
        view.showShop();
    }

    protected void showMenu() {
        view.showMenu("Shop");
    }
}
