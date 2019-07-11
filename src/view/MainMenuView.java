package view;

import controller.menus.AccountMenu;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import models.Player;

import java.io.FileInputStream;

public class MainMenuView {
    private static MainMenuView instance = new MainMenuView();

    private MainMenuView() {
    }

    public static MainMenuView getInstance() {
        return instance;
    }

    private Group root = new Group();
    private Scene scene = new Scene(root, 1536, 801.59);
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
    private Button customCard = new Button("CUSTOM CARD");
    private Button showHistory = new Button("SHOW HISTORY");
    private Label currentPlayer = new Label("");
    private boolean isInMainMenu;
    private Button collection = new Button("COLLECTION");
    private Button shop = new Button("SHOP");
    private Button battle = new Button("BATTLE");
    private Button back = new Button("BACK");
    private Button volume = new Button("VOLUME");
    private Button globalChat = new Button("GLOBAL CHAT");
    private Button scoreboard = new Button("SCOREBOARD");

    void run() {
        View.setScene(scene);
        VoicePlay.setThisMenu("main menu");
    }

    {
        scene.getStylesheets().add("view/stylesheets/mainmenu_view.css");

        setBackground();

        draw();

        setOnActions();

        handleChanges();

        root.getChildren().add(beforeLoginOptions);
    }

    private void draw() {
        beforeLoginOptions.getChildren().addAll(createAccount, login, exit, username, password);
        beforeLoginOptions.relocate(150, 170);
        beforeLoginOptions.setSpacing(7);

        afterLoginOptions.getChildren().addAll(gotoMainMenu, scoreboard, showHistory, save, logout, hesoyam, globalChat);
        afterLoginOptions.relocate(beforeLoginOptions.getLayoutX(), beforeLoginOptions.getLayoutY());
        afterLoginOptions.setSpacing(7);

        mainMenuOptions.getChildren().addAll(battle, collection, shop, customCard, volume, back);
        mainMenuOptions.relocate(beforeLoginOptions.getLayoutX(), beforeLoginOptions.getLayoutY());
        mainMenuOptions.setSpacing(7);

        username.setPromptText("USERNAME");
        username.setText("a");
        password.setPromptText("PASSWORD");
        password.setText("a");
        username.relocate(200, 200);
        password.relocate(200, 260);

        currentPlayer.relocate(1250, 760);
        currentPlayer.setStyle("-fx-text-fill: #ffce4e");

        VBox[] vBoxes = {beforeLoginOptions, afterLoginOptions, mainMenuOptions};
        for (Node node : vBoxes) {
            if (node instanceof VBox) {
                VBox vBox = (VBox) node;
                for (Node node1 : vBox.getChildren()) {
                    if (node1 instanceof Button) {
                        Button button = (Button) node1;
                        button.setFont(Font.font(17));
                        View.buttonEffect(button);
                    }
                }
            }
        }
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
        showHistory.setOnAction(event -> {
            MatchHistoryView.getInstance().run();
            VoicePlay.setThisMenu("match history");
        });
        save.setOnAction(event -> AccountMenu.getInstance().save());
        hesoyam.setOnAction(event -> AccountMenu.getInstance().hesoyam());
        exit.setOnAction(event -> View.exit());
        collection.setOnAction(event -> CollectionView.getInstance().run());
        shop.setOnAction(event -> {
            ShopView.getInstance().run();
            VoicePlay.setThisMenu("shop");
        });
        customCard.setOnAction(event -> CustomCardView.getInstance().run());
        battle.setOnAction(event -> createNewMatch());
        back.setOnAction(event -> isInMainMenu = false);
        volume.setOnAction(event -> VolumeController.getInstance().run());
        globalChat.setOnAction(event -> GlobalChatView.run());
        scoreboard.setOnAction(event -> Scoreboard.run());
    }

    private void setBackground() {
        try {
            ImageView background = new ImageView(new Image(
                    new FileInputStream("src/assets/resources/scenes/obsidian_woods/obsidian_woods_background@2x.jpg")));
            background.fitWidthProperty().bind(scene.widthProperty());
            background.fitHeightProperty().bind(scene.heightProperty());
            ImageView pillar = new ImageView(new Image(
                    new FileInputStream("src/assets/resources/scenes/obsidian_woods/obsidian_woods_pillar@2x.png")));
            pillar.setFitWidth(350);
            pillar.setFitHeight(540);
            pillar.relocate(scene.getWidth() - pillar.getFitWidth() + 93, 250);
            ImageView cliff = new ImageView(new Image(
                    new FileInputStream("src/assets/resources/scenes/obsidian_woods/obsidian_woods_cliff@2x.png")));
            cliff.setFitWidth(scene.getWidth());
            cliff.setFitHeight(330);
            cliff.relocate(0, scene.getHeight() - cliff.getFitHeight() + 50);
            ImageView vignette = new ImageView(new Image(
                    new FileInputStream("src/assets/resources/scenes/obsidian_woods/obsidian_woods_vignette.png")));
            vignette.setFitWidth(500);
            vignette.setFitHeight(500);
            ImageView brand = new ImageView(new Image(
                    new FileInputStream("src/assets/resources/ui/brand_duelyst@2x.png")));
            brand.setFitWidth(250);
            brand.setFitHeight(54);
            brand.relocate(100, 85);
            root.getChildren().addAll(background, pillar, cliff, vignette, brand);
        } catch (Exception e) {
            View.printThrowable(e);
        }
    }

    private void createNewMatch() {
        if (!Player.getCurrentPlayer().hasAValidMainDeck())
            View.printMessage(Message.DECK_IS_NOT_VALID);
        else
            CreateMatchView.getInstance().run();
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
                        if (!root.getChildren().contains(currentPlayer))
                            root.getChildren().add(currentPlayer);
                    } else {
                        root.getChildren().remove(currentPlayer);
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

    public Scene getScene() {
        return scene;
    }
}
