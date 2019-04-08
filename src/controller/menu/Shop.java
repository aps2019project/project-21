package controller.menu;

import controller.request.Request;
import controller.request.RequestType;
import controller.request.ShopMenuRequest;
import models.Card.Card;
import models.Item.Item;
import models.Player;
import view.View;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    private List<Card> cards = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private View view = new View();

    public void main() {

        outerLoop:
        while (true) {
            showOptions();

            Request request = new ShopMenuRequest();
            request.getNewCommand();

            if (request.getType() == RequestType.EXIT)
                break;
            if (!request.isValid()) {
                view.printError(request.getError());
                continue;
            }

            switch (request.getType()) {
                case SHOP_MENU:
                    continue outerLoop;
                case HELP:
                    help();
                    break;
            }
        }
    }

    private void showOptions() {

    }

    private void showCollection(Player player) {

    }

    private int search(String name) {
        return 0;
    }

    private int[] searchCollection(Player player, String name) {
        int[] a = {0};
        return a;
    }

    private void buy(Player player, String name) {

    }

    private void sell(Player player, String name) {

    }

    private void show() {

    }

    private void help() {

    }

    private void addCardToShop(Card card) {

    }

    private void addItemToShop(Card card) {

    }
}
