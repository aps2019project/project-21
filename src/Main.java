import controller.menus.MenuManager;
import json.Initializer;
import view.View;

public class Main {
    public static void main(String[] args) {
        new Thread(Main::runLogic).start();

        View.getInstance().main();
    }

    private static void runLogic() {
        MenuManager menuManager = MenuManager.getInstance();
        menuManager.main();
        Initializer.main();
    }
}
