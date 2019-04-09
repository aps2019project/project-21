package controller.menu;

import controller.request.AccountMenuRequest;
import controller.request.Request;
import view.View;

public class AccountMenu {
    private View view = new View();
    Request request = new AccountMenuRequest();

    public void main() {

        outerLoop:
        while (true) {
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
                    continue outerLoop;
                case EXIT:
                    break outerLoop;
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
