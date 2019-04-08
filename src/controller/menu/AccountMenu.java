package controller.menu;

import controller.request.MainMenuRequest;
import controller.request.Request;
import controller.request.RequestType;
import view.View;

public class AccountMenu {
    private View view = new View();

    public void main() {

        outerLoop:
        while (true) {
            showMenu();

            Request request = new MainMenuRequest();
            request.getNewCommand();

            if (request.getType() == RequestType.EXIT)
                break;
            if (!request.isValid()) {
                view.printError(request.getError());
                continue;
            }
            switch (request.getType()) {

            }
        }
    }

    private void login() {

    }

    private void createAccount() {

    }

    private void logout() {

    }

    private void showLeaderBoard() {

    }

    private void save() {

    }

    private void help() {

    }

    private void showMenu() {

    }

}
