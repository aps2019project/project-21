package view;

import controller.menus.AccountMenu;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class AccountView {
    private static AccountView instance = new AccountView();

    private AccountView() {
    }

    public static AccountView getInstance() {
        return instance;
    }

    private Group root = new Group();
    private Scene scene = new Scene(root, 1500, 1000);
    private VBox accountViewMenuItems = new VBox();
    private TextField userNameTextFilled = new TextField();
    private PasswordField passwordTextFilled = new PasswordField();
    private Button mainMenuButton = new Button("MAIN MENU");
    private Button createAccountButton = new Button("CREATE ACCOUNT");
    private Button loginButton = new Button("LOGIN");
    private Button logoutButton = new Button("LOGOUT");
    private Button exitButton = new Button("EXIT");

    void run(Stage primaryStage) {

        View.getInstance().setScene(scene);
        accountViewMenuItems.relocate(100, 100);

        userNameTextFilled.setPromptText("USERNAME");
        passwordTextFilled.setPromptText("PASSWORD");
        userNameTextFilled.relocate(200, 200);
        passwordTextFilled.relocate(200, 260);

        accountViewMenuItems.getChildren().addAll(mainMenuButton, createAccountButton,
                loginButton, logoutButton, exitButton, userNameTextFilled, passwordTextFilled);
        accountViewMenuItems.setSpacing(10);

        mainMenuButton.setOnAction(event -> AccountMenu.getInstance().gotoMainMenu());

        createAccountButton.setOnAction(event -> AccountMenu.getInstance().createAccount(userNameTextFilled.getText(), passwordTextFilled.getText()));

        loginButton.setOnAction(event -> AccountMenu.getInstance().login(userNameTextFilled.getText(), passwordTextFilled.getText()));

        logoutButton.setOnAction(event -> AccountMenu.getInstance().logout());

        exitButton.setOnAction(event -> primaryStage.close());

        setBackground();

        handleButtons();

        root.getChildren().addAll(accountViewMenuItems);
    }

    private void handleButtons() {
        for (Node node : accountViewMenuItems.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setOnMouseEntered(event -> button.setTranslateX(20));
                button.setOnMouseExited(event -> button.setTranslateX(0));
                button.setStyle("-fx-background-color: #00000000");
                button.setTextFill(Color.WHITE);
            }
        }

    }

    private void setBackground() {
        try {
            ImageView background = new ImageView(new Image(new FileInputStream("src/assets/background.jpg")));
            background.fitWidthProperty().bind(scene.widthProperty());
            background.fitHeightProperty().bind(scene.heightProperty());

            root.getChildren().add(background);

        } catch (Exception e) {
            View.printThrowable(e);
        }
    }
}
