package controller.menus;

import controller.request.MainMenuRequest;
import controller.request.Request;
import view.View;

public class MainMenu extends Menu {
    private static MainMenu instance = new MainMenu();

    static Menu getInstance() {
        return instance;
    }

    private MainMenu() {

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

    private void help() {

    }

    private void shop() {

    }

    private void battle() {

    }

    private void collection() {

    }

    protected void showMenu() {

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
