package controller.menu;

import controller.request.AccountMenuRequest;
import controller.request.Request;
import view.View;

public class AccountMenu extends Menu {
    private static AccountMenu instance = new AccountMenu();

    static Menu getInstance() {
        return instance;
    }

    private AccountMenu() {

    }

    void main() {

        showMenu();

        request = new AccountMenuRequest();

        request.getNewCommand();

        request.checkSyntax();

        switch (request.getType()) {
            case CREATE_ACCOUNT:
                break;
            case LOGIN:
                break;
            case SHOW_LEADERBOARD:
                break;
            case SAVE:
                break;
            case LOGOUT:
                break;
            case HELP:
                break;
            case SHOW_MENU:
                break;
            case EXIT:
                break;
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

    private void gotoMainMenu() {

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
