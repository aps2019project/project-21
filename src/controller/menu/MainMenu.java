package controller.menu;

import controller.request.MainMenuRequest;
import controller.request.Request;
import controller.request.RequestType;
import view.View;

public class MainMenu {
    private View view = new View();

    public void main() {

        mainLoop:
        while (true) {
            showOptions();

            Request request = new MainMenuRequest();
            request.getNewCommand();

            if (request.getType() == RequestType.EXIT)
                break;
            if (!request.isValid()) {
                view.printError(request.getError());
                continue;
            }

            switch (request.getType()) {
                case MAIN_MENU:
                    continue mainLoop;
                case HELP:
                    help(request);
                    break;
                case SHOP:
                    shop(request);
                    break;
                case BATTLE:
                    battle(request);
                    break;
                case COLLECTION:
                    collection(request);
                    break;
            }
        }
    }

    private void showOptions() {

    }

    private void help(Request request) {

    }

    private void shop(Request request) {

    }

    private void battle(Request request) {

    }

    private void collection(Request request) {

    }
}

