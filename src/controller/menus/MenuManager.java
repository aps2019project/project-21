package controller.menus;

public class MenuManager {
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

    void changeMenu(MenuType menuType) {
        this.menuType = menuType;
    }
}
