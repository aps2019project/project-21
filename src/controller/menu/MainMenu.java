package controller.menu;

import controller.request.MainMenuRequest;
import controller.request.Request;
import view.View;

public class MainMenu {
    private View view = new View();
    private Request request;

    public void main() {
        outerLoop:
        while (true) {
            showMenu();

            request = new MainMenuRequest();

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

    private void help() {

    }

    private void shop() {

    }

    private void battle() {

    }

    private void collection() {

    }

    private void showMenu() {

    }
}
