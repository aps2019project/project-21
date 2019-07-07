package view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import models.Player;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Scoreboard {
    private static Group root = new Group();
    private static Scene scene = new Scene(root, 1536, 801.59);
    private static Button back = new Button("BACK");
    private static VBox scoreboard = new VBox();
    private static ScrollPane scrollPane = new ScrollPane();

    public static void run() {
        View.getInstance().setScene(scene);
    }

    static {
        scene.getStylesheets().add("view/stylesheets/scoreboard.css");

        setBackground();

        draw();

        setOnActions();

        handleChanges();

        root.getChildren().addAll(back, scrollPane);
    }

    private static void draw() {
        back.relocate(10, 10);
        back.setFont(Font.font(20));
        scrollPane.setContent(scoreboard);
        scrollPane.setMaxHeight(600);
        scrollPane.setMaxWidth(400);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.relocate(580, 150);
        drawScoreboard();
    }

    private static void drawScoreboard() {
        scoreboard.getChildren().clear();
        scoreboard.setSpacing(5);

        HBox hBox = new HBox();
        hBox.setSpacing(1);

        Label name = new Label("PLAYER");
        Label wins = new Label("WINS");
        Label losses = new Label("LOSSES");

        hBox.getChildren().addAll( makeStackPane(name, 151),
                makeStackPane(wins, 100), makeStackPane(losses, 100));
        scoreboard.getChildren().add(hBox);

        List<Player> players = Player.getPlayersSortedForScoreboard();
        for (Player player : players)
            drawPlayer(player);
    }

    private static void drawPlayer(Player player) {
        if (player.getUsername().equals("host"))
            return;
        HBox hBox = new HBox();
        hBox.setSpacing(1);

        try {
            String n = Integer.toString(Math.abs(player.getUsername().hashCode()) % 17 + 1);
            ImageView icon = new ImageView(new Image(new FileInputStream("src/assets/profile_icons/icon_" + n + ".png")));
            icon.setFitHeight(40);
            icon.setFitWidth(40);
            hBox.getChildren().add(makeStackPane(icon, 50));
        } catch (IOException ex) {
            View.printThrowable(ex);
        }

        Label name = new Label(player.getUsername());
        Label wins = new Label(Integer.toString(player.getMultiplayerWins()));
        Label losses = new Label(Integer.toString(player.getMultiplayerLosses()));

        hBox.getChildren().addAll(makeStackPane(name, 100), makeStackPane(wins, 100), makeStackPane(losses, 100));
        scoreboard.getChildren().add(hBox);
    }

    private static StackPane makeStackPane(Node node, int width) {
        StackPane stackPane = new StackPane();
        Rectangle rectangle = new Rectangle(width, 50);
        rectangle.setStyle("-fx-fill: rgba(255,255,255,0.45)");
        stackPane.getChildren().addAll(rectangle, node);
        return stackPane;
    }

    private static void setOnActions() {
        back.setOnAction(event -> View.getInstance().back());
    }

    private static void handleChanges() {

    }

    private static void setBackground() {
        try {
            ImageView background = new ImageView(new Image(new FileInputStream
                    ("src/assets/resources/maps/battlemap3_background@2x.png")));
            background.fitWidthProperty().bind(scene.widthProperty());
            background.fitHeightProperty().bind(scene.heightProperty());
            root.getChildren().add(background);
        } catch (Exception e) {
            View.printThrowable(e);
        }
    }
}
