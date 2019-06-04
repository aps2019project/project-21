package view;

import controller.menus.AccountMenu;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Player;

public class AccountView {
    private Scene scene;
    private Group root;



    private TextField userNameTextFilled = new TextField();
    private PasswordField passwordTextFilled = new PasswordField();


    private VBox accountViewMenuItems = new VBox();
    private Button mainMenuButton = new Button("MAIN MENU");
    private Button createAccountButton = new Button("CREATE ACCOUNT");
    private Button loginButton = new Button("LOGIN");
    private Button logoutButton = new Button("LOGOUT");
    private Button exitButton = new Button("EXIT");
//    private Button backButton = new Button("BACK");





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
//        backButton.relocate(50, 170);


        userNameTextFilled.setPromptText("USERNAME");
        passwordTextFilled.setPromptText("PASSWORD");
        userNameTextFilled.relocate(200, 200);
        passwordTextFilled.relocate(200, 260);



        accountViewMenuItems.getChildren().addAll(mainMenuButton, createAccountButton, loginButton, logoutButton, exitButton, userNameTextFilled, passwordTextFilled);










        mainMenuButton.setOnAction(event -> {
            if (Player.ifLoggedIN()) {
                MainMenuView.getInstance().run(primaryStage);
            }
        });

        createAccountButton.setOnAction(event -> {
            AccountMenu.getInstance().createAccount(userNameTextFilled.getText(), passwordTextFilled.getText());
        });

        loginButton.setOnAction(event -> {
            AccountMenu.getInstance().login(userNameTextFilled.getText(), passwordTextFilled.getText());
        });

        logoutButton.setOnAction(event -> {
            AccountMenu.getInstance().logout();
        });

        exitButton.setOnAction(event -> primaryStage.close());









        root.getChildren().addAll(accountViewMenuItems);

    }

    public static AccountView getInstance() {
        return instance;
    }
}
