package Controller;

import View.View;

public class Controller {
    private View view = new View();

    public void main() {

        mainLoop:
        while (true) {
            Request request = new Request();
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

    private void help(Request request) {

    }

    private void shop(Request request) {

    }

    private void battle(Request request) {

    }

    private void collection(Request request) {

    }
}
