package controller.menu;

import controller.request.AccountMenuRequest;
import controller.request.Request;
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
    private Request request = new ShopMenuRequest();

    public void main() {
        outerLoop:
        while (true) {
            showMenu();

            request = new AccountMenuRequest();

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

    private void showMenu() {

    }
}
