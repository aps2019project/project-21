package controller.menus;

import controller.request.MainMenuRequest;
import controller.request.Request;
import view.View;

public class Graveyard extends Menu {
    private static Graveyard instance = new Graveyard();

    static Menu getInstance() {
        return instance;
    }

    private Graveyard() {

    }

    public void main() {
        showMenu();

        request = new MainMenuRequest();

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

    protected void showMenu() {

    }

    private void showInfo() {

    }

    private void showCards() {

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
