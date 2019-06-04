package view;

import controller.menus.AccountMenu;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AccountView {
    private Scene scene;
    private Group root;



    private TextField userNameTextFilled = new TextField();
    private PasswordField passwordTextFilled = new PasswordField();


    private Button createAccountButton = new Button("CREATE ACCOUNT");
    private Button loginButton = new Button("LOGIN");
    private Button logoutButton = new Button("LOGOUT");
    private Button exitButton = new Button("EXIT");
    private Button backButton = new Button("BACK");



    private static  AccountView instance = new AccountView();

    private AccountView() {

    }


//    void getKey (Scene scene, Group root){
//        scene.setOnKeyReleased(event -> {
//
//        });
//    }
//

    void run(Stage primaryStage) {


        root = new Group();
        scene = new Scene(root, 1500, 1000);
        primaryStage.setScene(scene);
        createAccountButton.relocate(50, 50);
        loginButton.relocate(50, 80);
        logoutButton.relocate(50, 110);
        exitButton.relocate(50, 140);
        backButton.relocate(50, 170);


        userNameTextFilled.setPromptText("USERNAME");
        passwordTextFilled.setPromptText("PASSWORD");














        createAccountButton.setOnAction(event -> {
            AccountMenu.getInstance().createAccount(userNameTextFilled.getText(), passwordTextFilled.getText());
        });

        loginButton.setOnAction(event -> {
            AccountMenu.getInstance().login(userNameTextFilled.getText(), passwordTextFilled.getText());
        });












        root.getChildren().addAll(createAccountButton, loginButton, logoutButton, exitButton, backButton, passwordTextFilled, userNameTextFilled);

    }

    public static AccountView getInstance() {
        return instance;
    }
}
