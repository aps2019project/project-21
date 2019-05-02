import controller.menus.MenuManager;
import json.Initializer;

public class Main {
    public static void main(String[] args) {
        Initializer.main();

        MenuManager menuManager = MenuManager.getInstance();
        menuManager.main();
    }
}
