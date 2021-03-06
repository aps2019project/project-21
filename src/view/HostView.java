package view;

import controller.menus.AccountMenu;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import models.Player;
import network.ClientHandler;

import java.io.FileInputStream;

public class HostView {
    private static HostView instance = new HostView();

    private HostView() {
    }

    public static HostView getInstance() {
        return instance;
    }

    private Group root = new Group();
    private Scene scene = new Scene(root, 1536, 801.59);
    private VBox options = new VBox();
    private Button exit = new Button("EXIT");
    private Button save = new Button("SAVE");
    private Button shop = new Button("SHOP");
    private Button scoreboard = new Button("SCOREBOARD");
    private Button globalChat = new Button("GLOBAL CHAT");
    private Button customCard = new Button("CUSTOM CARD");
    private TextField limit = new TextField();

    void run() {
        View.setScene(scene);
    }

    {
        scene.getStylesheets().add("view/stylesheets/mainmenu_view.css");

        setBackground();

        draw();

        setOnActions();

        root.getChildren().add(options);
    }

    private void draw() {
        options.getChildren().addAll(shop, scoreboard, globalChat, customCard, limit, exit);
        options.relocate(150, 170);
        options.setSpacing(7);
        limit.setPromptText("SET END TURN TIME LIMIT");

        for (Node node1 : options.getChildren()) {
            if (node1 instanceof Button) {
                Button button = (Button) node1;
                button.setFont(Font.font(17));
                View.giveGlowEffect(button);
            }
        }
    }

    private void setOnActions() {
        save.setOnAction(event -> AccountMenu.getInstance().save());
        exit.setOnAction(event -> View.exit());
        shop.setOnAction(event -> HostShopView.run());
        scoreboard.setOnAction(event -> Scoreboard.run());
        globalChat.setOnAction(event -> {
            GlobalChatView.run();
            GlobalChatView.setOnlineUsersName(Player.getOnlineUsersName());
            GlobalChatView.hideInputTextfield();
        });
        customCard.setOnAction(event -> CustomCardView.getInstance().run());
        limit.setOnAction(event -> {
            ClientHandler.sendLimitToAll(limit.getText());
        });
    }

    private void setBackground() {
        try {
            ImageView background = new ImageView(new Image(
                    new FileInputStream("src/assets/resources/scenes/obsidian_woods/obsidian_woods_background@2x.jpg")));
            background.fitWidthProperty().bind(scene.widthProperty());
            background.fitHeightProperty().bind(scene.heightProperty());

            ImageView brand = new ImageView(new Image(
                    new FileInputStream("src/assets/resources/ui/brand_duelyst@2x.png")));
            brand.setFitWidth(250);
            brand.setFitHeight(54);
            brand.relocate(100, 85);
            root.getChildren().addAll(background, brand);
        } catch (Exception e) {
            View.printThrowable(e);
        }
    }

    public Scene getScene() {
        return scene;
    }
}
