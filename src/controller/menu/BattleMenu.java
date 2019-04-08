package controller.menu;

import controller.request.MainMenuRequest;
import controller.request.Request;
import controller.request.RequestType;
import view.View;

public class BattleMenu {
    private View view = new View();

    public void main() {

        battleLoop:
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

            }
        }
    }

    private void showOptions(){

    }


}

