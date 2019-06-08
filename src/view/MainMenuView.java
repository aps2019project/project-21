package view;

import controller.menus.AccountMenu;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.Player;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class MainMenuView {
    private static MainMenuView instance = new MainMenuView();

    private MainMenuView() {
    }

    public static MainMenuView getInstance() {
        return instance;
    }

    private Group root = new Group();
    private Scene scene = new Scene(root);
    private VBox beforeLoginOptions = new VBox();
    private VBox afterLoginOptions = new VBox();
    private VBox mainMenuOptions = new VBox();
    private TextField username = new TextField();
    private PasswordField password = new PasswordField();
    private Button gotoMainMenu = new Button("MAIN MENU");
    private Button createAccount = new Button("CREATE ACCOUNT");
    private Button login = new Button("LOGIN");
    private Button logout = new Button("LOGOUT");
    private Button exit = new Button("EXIT");
    private Button save = new Button("SAVE");
    private Button hesoyam = new Button("HESOYAM");
    private Button showHistory = new Button("SHOW HISTORY");
    private Label currentPlayer = new Label("");
    private boolean isInMainMenu = false;
    private Button collection = new Button("COLLECTION");
    private Button shop = new Button("SHOP");
    private Button battle = new Button("BATTLE");
    private Button back = new Button("BACK");


    void run() {
        View.getInstance().setScene(scene);
    }

    {
        setBackground();

        draw();

        handleButtons();

        setOnActions();

        handleChanges();

        root.getChildren().add(beforeLoginOptions);
    }

    private void draw() {
        beforeLoginOptions.getChildren().addAll(createAccount, login, exit, username, password);
        beforeLoginOptions.relocate(100, 100);
        beforeLoginOptions.setSpacing(15);

        afterLoginOptions.getChildren().addAll(gotoMainMenu, showHistory, save, logout, hesoyam, currentPlayer);
        afterLoginOptions.relocate(100, 100);
        afterLoginOptions.setSpacing(15);

        mainMenuOptions.getChildren().addAll(collection, shop, battle, back);
        mainMenuOptions.relocate(100, 100);
        mainMenuOptions.setSpacing(15);

        username.setPromptText("USERNAME");
        username.setText("a");
        password.setPromptText("PASSWORD");
        password.setText("a");
        username.relocate(200, 200);
        password.relocate(200, 260);
        currentPlayer.setTextFill(Color.ORANGE);
    }

    private void setOnActions() {
        gotoMainMenu.setOnAction(event -> isInMainMenu = true);
        createAccount.setOnAction(event -> AccountMenu.getInstance()
                .createAccount(username.getText(), password.getText()));
        password.setOnAction(event -> AccountMenu.getInstance()
                .login(username.getText(), password.getText()));
        login.setOnAction(event -> AccountMenu.getInstance()
                .login(username.getText(), password.getText()));
        logout.setOnAction(event -> AccountMenu.getInstance().logout());
        showHistory.setOnAction(event -> AccountMenu.getInstance().showMatchHistory());
        save.setOnAction(event -> AccountMenu.getInstance().save());
        hesoyam.setOnAction(event -> AccountMenu.getInstance().hesoyam());
        exit.setOnAction(event -> View.getInstance().exit());
        collection.setOnAction(event -> CollectionView.getInstance().run());
        shop.setOnAction(event -> ShopView.getInstance().run());
        battle.setOnAction(event -> {
        });
        back.setOnAction(event -> isInMainMenu = false);
    }

    private void handleButtons() {
        List<Node> nodes = new ArrayList<>(beforeLoginOptions.getChildren());
        nodes.addAll(afterLoginOptions.getChildren());
        nodes.addAll(mainMenuOptions.getChildren());
        for (Node node : nodes)
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setOnMouseEntered(event -> {
                    button.setTranslateX(20);
                    button.setTranslateY(5);
                });
                button.setOnMouseExited(event -> {
                    button.setTranslateX(0);
                    button.setTranslateY(0);
                });
                button.setStyle("-fx-background-color: transparent; -fx-text-fill: #c2c2c2;");
            }
    }

    private void setBackground() {
        try {
            ImageView background = new ImageView(new Image(new FileInputStream("src\\assets\\mainmenu.jpg")));
            background.fitWidthProperty().bind(scene.widthProperty());
            background.fitHeightProperty().bind(scene.heightProperty());
            root.getChildren().add(background);
        } catch (Exception e) {
            View.printThrowable(e);
        }
    }

    private void handleChanges() {
        AnimationTimer handleChanges = new AnimationTimer() {
            long last;

            @Override
            public void handle(long now) {
                if (now - last > 100) {
                    if (isInMainMenu) {
                        root.getChildren().remove(afterLoginOptions);
                        if (!root.getChildren().contains(mainMenuOptions))
                            root.getChildren().add(mainMenuOptions);
                    } else if (Player.hasAnyoneLoggedIn()) {
                        currentPlayer.setText("LOGGED IN:  " + Player.getCurrentPlayer().getUsername());
                        root.getChildren().remove(beforeLoginOptions);
                        root.getChildren().remove(mainMenuOptions);
                        if (!root.getChildren().contains(afterLoginOptions))
                            root.getChildren().add(afterLoginOptions);
                    } else {
                        root.getChildren().remove(afterLoginOptions);
                        if (!root.getChildren().contains(beforeLoginOptions))
                            root.getChildren().add(beforeLoginOptions);
                    }
                    last = now;
                }
            }
        };
        handleChanges.start();
    }
}