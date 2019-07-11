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
import javafx.util.Pair;
import models.Player;
import network.Client;
import network.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Scoreboard {
    private static Group root = new Group();
    private static Scene scene = new Scene(root, 1536, 801.59);
    private static Button back = new Button("BACK");
    private static VBox scoreboard = new VBox();
    private static ScrollPane scrollPane = new ScrollPane();
    private static VBox onlineMatches = new VBox();
    private static ScrollPane scrollPane2 = new ScrollPane();

    public static void run() {
        View.setScene(scene);
    }

    static {
        scene.getStylesheets().add("view/stylesheets/scoreboard.css");

        setBackground();

        draw();

        setOnActions();

        root.getChildren().addAll(back, scrollPane, scrollPane2);
    }

    private static void draw() {
        back.relocate(10, 10);
        back.setFont(Font.font(20));
        scrollPane.setContent(scoreboard);
        scrollPane.setMaxHeight(600);
        scrollPane.setMaxWidth(400);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.relocate(330, 150);
        scrollPane2.setContent(onlineMatches);
        scrollPane2.setMaxHeight(600);
        scrollPane2.setMaxWidth(400);
        scrollPane2.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane2.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane2.relocate(830, 150);
        drawScoreboardForHost();
    }

    public static void drawOnlineMatches(List<Pair<String, String>> matches) {
        onlineMatches.getChildren().clear();
        onlineMatches.setSpacing(5);

        HBox hBox = new HBox();
        hBox.setSpacing(1);

        Label name = new Label("PLAYER 1");
        Label wins = new Label("PLAYER 2");
        Label losses = new Label("WATCH");

        hBox.getChildren().addAll(makeStackPane(name, 151, -1),
                makeStackPane(wins, 100, -1), makeStackPane(losses, 100, -1));
        scoreboard.getChildren().add(hBox);

        for (Pair p : matches)
            drawOnlineMatch(p);
    }

    private static void drawOnlineMatch(Pair<String, String> pair) {
        HBox hBox = new HBox();
        hBox.setSpacing(1);

        Label player1 = new Label(pair.getKey());
        Label player2 = new Label(pair.getValue());
        Button button = new Button("WATCH");
        button.setOnAction(event -> Client.write(Request.makeWatchOnlineRequest(pair)));

        hBox.getChildren().addAll(makeStackPane(player1, 100, -1)
                , makeStackPane(player2, 100, -1), makeStackPane(button, 100, -1));
        onlineMatches.getChildren().add(hBox);
    }

    public static void drawScoreboard(List<Player> sortedPlayers) {
        scoreboard.getChildren().clear();
        scoreboard.setSpacing(5);

        HBox hBox = new HBox();
        hBox.setSpacing(1);

        Label name = new Label("PLAYER");
        Label wins = new Label("WINS");
        Label losses = new Label("LOSSES");

        hBox.getChildren().addAll(makeStackPane(name, 151, -1),
                makeStackPane(wins, 100, -1), makeStackPane(losses, 100, -1));
        scoreboard.getChildren().add(hBox);

        for (Player player : sortedPlayers)
            drawPlayer(player);
    }

    public static void drawScoreboardForHost() {
        drawScoreboard(Player.getPlayersSortedForScoreboard());
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
            int status = 0;
            if (player.isOnline())
                status = 1;
            hBox.getChildren().add(makeStackPane(icon, 50, status));
        } catch (IOException ex) {
            View.printThrowable(ex);
        }

        Label name = new Label(player.getUsername());
        Label wins = new Label(Integer.toString(player.getMultiplayerWins()));
        Label losses = new Label(Integer.toString(player.getMultiplayerLosses()));

        hBox.getChildren().addAll(makeStackPane(name, 100, -1)
                , makeStackPane(wins, 100, -1), makeStackPane(losses, 100, -1));
        scoreboard.getChildren().add(hBox);
    }

    private static StackPane makeStackPane(Node node, int width, int onlineStatus) {
        StackPane stackPane = new StackPane();
        Rectangle rectangle = new Rectangle(width, 50);
        if (onlineStatus == -1)
            rectangle.setStyle("-fx-fill: rgba(255,255,255,0.45)");
        else if (onlineStatus == 0)
            rectangle.setStyle("-fx-fill: rgba(255,0,3,0.25)");
        else if (onlineStatus == 1)
            rectangle.setStyle("-fx-fill: rgba(6,255,18,0.25)");
        stackPane.getChildren().addAll(rectangle, node);
        return stackPane;
    }

    private static void setOnActions() {
        back.setOnAction(event -> View.back());
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
