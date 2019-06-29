package controller.menus;

public class MenuManager {
    private static MenuManager instance = new MenuManager();

    public static MenuManager getInstance() {
        return instance;
    }

    private MenuType menuType = MenuType.ACCOUNT_MENU;

    void gotoBattle() {
        menuType = MenuType.BATTLE_MENU;
        BattleMenu.getInstance().showMenu = true;
    }


	public void gotoMainMenu() {
        menuType = MenuType.MAIN_MENU;
        MainMenu.getInstance().showMenu = true;
    }
    protected void exit() {
        menuType = MenuType.EXIT;
    }

    void gotoGraveyard() {
        menuType = MenuType.GRAVEYARD_MENU;
        Graveyard.getInstance().showMenu = true;
    }
}
