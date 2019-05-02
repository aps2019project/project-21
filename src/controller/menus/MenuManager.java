package controller.menus;

public class MenuManager {
    private static MenuManager instance = new MenuManager();

    public static MenuManager getInstance() {
        return instance;
    }

    private MenuType menuType = MenuType.ACCOUNT_MENU;

    public void main() {

        menuLoop:
        while (true) {
            switch (menuType) {
                case ACCOUNT_MENU:
                    AccountMenu.getInstance().main();
                    break;
                case MAIN_MENU:
                    MainMenu.getInstance().main();
                    break;
                case SHOP_MENU:
                    ShopMenu.getInstance().main();
                    break;
                case BATTLE_MENU:
                    BattleMenu.getInstance().main();
                    break;
                case GRAVEYARD_MENU:
                    Graveyard.getInstance().main();
                    break;
                case COLLECTION_MENU:
                    CollectionMenu.getInstance().main();
                    break;
                case EXIT:
                    break menuLoop;
            }
        }
    }

    protected void changeMenu(MenuType menuType) {
        this.menuType = menuType;
    }

    protected void gotoShop() {
        menuType = MenuType.SHOP_MENU;
    }

    protected void gotoCollection() {
        menuType = MenuType.COLLECTION_MENU;
    }

    protected void gotoBattle() {
        menuType = MenuType.BATTLE_MENU;
    }

    protected void exit() {
        menuType = MenuType.EXIT;
    }

    protected void gotoGraveyard() {
        menuType = MenuType.GRAVEYARD_MENU;
    }

    protected void gotoAccount() {
        menuType = MenuType.ACCOUNT_MENU;
    }
}
