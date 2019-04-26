package controller.menus;

import controller.request.AccountMenuRequest;
import controller.request.Request;
import models.card.Card;
import models.Item.Item;
import models.Player;
import view.ErrorMode;
import view.View;

import java.util.ArrayList;
import java.util.List;

public class ShopMenu extends Menu {
    private static ShopMenu instance = new ShopMenu();

    static Menu getInstance() {
        return instance;
    }

    private ShopMenu() {

    }

    private List<Card> cards = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    public void main() {
        showMenu();

        request = new AccountMenuRequest();

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

    private void showCollection(Player player) {
        view.showCollection(player.getCollection());
    }

    private int search(String name) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getName().equals(name)) {
                System.out.println(cards.get(i).getId());
                return cards.get(i).getId();
            }
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(name)) {
                System.out.println(items.get(i).getId());
                return items.get(i).getId();
            }
        }
        view.printError(ErrorMode.NOT_IN_SHOP);
        return -1;
    }

    private int searchCollection(Player player, String name) {
        boolean isItInCollection = false;
        for (int i = 0; i < player.getCollection().getCards().size(); i++) {
            if (player.getCollection().getCards().get(i).getName().equals(name)) {
                System.out.println(player.getCollection().getCards().get(i).getId());
                isItInCollection = true;
            }
        }
        for (int i = 0; i < player.getCollection().getItems().size(); i++) {
            if (player.getCollection().getItems().get(i).getName().equals(name)) {
                System.out.println(player.getCollection().getItems().get(i).getId());
                isItInCollection = true;
            }
        }
        if (!isItInCollection) {
            view.printError(ErrorMode.CARD_IS_NOT_IN_COLLECTION);
            return 0;
        }
        return 1;
    }

    private void buy(Player player, String name) {
        boolean isItCard = false;
        boolean isItInShop = false;
        Card card = new Card();
        for (int i = 0; i < cards.size() && !isItCard; i++) {
            if (cards.get(i).getName().equals(name)) {
                card = cards.get(i);
                isItCard = true;
                isItInShop = true;
            }
        }
        Item item = new Item();
        for (int i = 0; i < items.size() && !isItCard; i++) {
            if (items.get(i).getName().equals(name)) {
                item = items.get(i);
                isItInShop = true;
            }
        }
        if (!isItInShop) {
            view.printError(ErrorMode.NOT_IN_SHOP);
            return;
        }
        if (isItCard) {
            if (card.getPrice() > player.getDrake()) {
                view.printError(ErrorMode.NOT_ENOUGH_MONEY);
                return;
            }
            card.makeCopyAndAddToCollection(player);
            player.setDrake(player.getDrake() - card.getPrice());
        }
        if (item.getPrice() > player.getDrake()) {
            view.printError(ErrorMode.NOT_ENOUGH_MONEY);
            return;
        }
        if (player.getCollection().getItems().size() == 3) {
            view.printError(ErrorMode.HAVE_3_ITEMS);
            return;
        }
        item.makeCopyAndAddToCollection(player);
        player.setDrake(player.getDrake() - item.getPrice());
    }

    private void sell(Player player, String name) {
        boolean isItInCollection = false;
        boolean isItCard = false;
        Card card = new Card();
        Item item = new Item();
        for (int i = 0; i < player.getCollection().getCards().size() && !isItInCollection; i++) {
            if (player.getCollection().getCards().get(i).getName().equals(name)) {
                card = player.getCollection().getCards().get(i);
                isItCard = true;
                isItInCollection = true;
            }
        }
        for (int i = 0; i < player.getCollection().getItems().size() && !isItInCollection; i++) {
            if (player.getCollection().getItems().get(i).getName().equals(name)) {
                item = player.getCollection().getItems().get(i);
                isItInCollection = true;
            }
        }
        if (!isItInCollection) {
            view.printError(ErrorMode.CARD_IS_NOT_IN_COLLECTION);
            return;
        }
        if (isItCard) {
            player.getCollection().removeCard(card);
            player.setDrake(player.getDrake() + card.getPrice());
            return;
        }
        player.getCollection().removeItem(item);
        player.setDrake(player.getDrake() + item.getPrice());
    }

    private void show() {
        view.showShop(this);
    }

    private void help() {
        view.help("shopMenu");
    }

    private void addCardToShop(Card card) {
        if (!this.cards.contains(card))
            this.cards.add(card);
    }

    private void addItemToShop(Item item) {
        if (!this.items.contains(item))
            this.items.add(item);
    }

    private void showMenu() {
        view.showMenu("shopMenu");
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
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
}
