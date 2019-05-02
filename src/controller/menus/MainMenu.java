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
        showMenu();

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
        }
    }

    private void help() {

    }

    private void shop() {
        MenuManager.getInstance().changeMenu(MenuType.SHOP_MENU);
    }

    private void battle() {
        MenuManager.getInstance().changeMenu(MenuType.BATTLE_MENU);
    }

    private void collection() {
        MenuManager.getInstance().changeMenu(MenuType.COLLECTION_MENU);
    }

    protected void showMenu() {
        System.out.println("-------MainMenu-------");
        System.out.println("1. collection");
        System.out.println("2. shop");
        System.out.println("3. battle");
        System.out.println("4. show menu");
        System.out.println("5. help");
        System.out.println("6. back");
        System.out.println("7. exit");
    }

    private void back() {
        MenuManager.getInstance().gotoAccount();
    }
}
