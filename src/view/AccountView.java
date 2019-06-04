package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AccountView {
    private Scene scene;
    private Group root;
    private Button createAccount;
    private Button login;
    private Button logout;
    private Button exit;
    private Button back;

    private static  AccountView instance = new AccountView();

    private AccountView() {
    }

    void run(Stage primaryStage) {
        root = new Group();
        scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);
    }

    public static AccountView getInstance() {
        return instance;
    }
}
