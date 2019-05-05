package controller.menus;

import controller.request.MainMenuRequest;

public class MainMenu extends Menu {
    private static MainMenu instance = new MainMenu();

    static Menu getInstance() {
        return instance;
    }

    private MainMenu() {

    }

    public void main() {
        if (showMenu) {
            showMenu();
            showMenu = false;
        }

        request = new MainMenuRequest();

        request.getNewCommand();

        request.extractType();

        switch (request.getType()) {
            case COLLECTION:
                collection();
                break;
            case SHOP:
                shop();
                break;
            case BATTLE:
                battle();
                break;
            case BACK:
                back();
                break;
            case HELP:
                help();
                break;
            case SHOW_MENU:
                showMenu();
                break;
            case EXIT:
                exit();
                break;
            case INVALID:
                invalidCommand();
                break;
        }
    }

    private void help() {

    }

    private void shop() {
        MenuManager.getInstance().gotoShop();
    }

    private void battle() {
        MenuManager.getInstance().gotoBattle();
    }

    private void collection() {
        MenuManager.getInstance().gotoCollection();
    }

    protected void showMenu() {
        view.showMenu("MainMenu");
    }

    private void back() {
        MenuManager.getInstance().gotoAccount();
    }
}
