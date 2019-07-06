package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import models.Player;
import models.match.Match;

import java.io.FileInputStream;
import java.io.IOException;

class MatchHistoryView {
    private static MatchHistoryView instance = new MatchHistoryView();

    private Group root = new Group();
    private Scene scene = new Scene(root, 1536, 801.59);
    private Button back = new Button("BACK");
    private TilePane games = new TilePane();
    private ScrollPane scrollPane = new ScrollPane();
    private ImageView volume = new ImageView();

    public static MatchHistoryView getInstance() {
        return instance;
    }

    public static void setInstance(MatchHistoryView instance) {
        MatchHistoryView.instance = instance;
    }

    void run() {
        View.getInstance().setScene(scene);
        showMatchHistory();
    }

    {
        try {
            volume.setImage(new Image(new FileInputStream("src\\assets\\volume.png")));
            volume.setScaleX(0.1);
            volume.setScaleY(0.1);
            volume.relocate(1000, 450);
        } catch (IOException ex) {
            View.printThrowable(ex);
        }
        scene.getStylesheets().addAll("view/stylesheets/match_history.css");

        setBackground();

        draw();

        drawScrollPane();

        handleButtons();
    }

    private void draw() {
        root.getChildren().addAll(volume);

        back.relocate(200, 100);

        root.getChildren().addAll(back);
    }

    private void drawScrollPane() {
        try {
            Background t = new Background(new BackgroundImage(new Image(new FileInputStream
                    ("src\\assets\\MatchHistory\\TilePane.png")), BackgroundRepeat.REPEAT,
                    BackgroundRepeat.REPEAT
                    , BackgroundPosition.CENTER, BackgroundSize.DEFAULT));
            games.setBackground(t);
        } catch (IOException ex) {
            View.printThrowable(ex);
        }

        games.setVgap(10);
        games.setPrefColumns(1);

        showMatchHistory();

        scrollPane.setContent(games);
        scrollPane.setPannable(true);
        scrollPane.relocate(200, 150);
        scrollPane.setMaxHeight(500);
        scrollPane.setMaxWidth(1100);
        scrollPane.setStyle("-fx-background-color: transparent");
        scrollPane.setStyle("}.scroll-pane > .viewport {\n" +
                "   -fx-background-color: transparent;}\n" +
                "{");

        root.getChildren().addAll(scrollPane);
    }

    private void showMatchHistory(){
        games.getChildren().clear();
        Player thisPlayer = Player.getCurrentPlayer();

        for (Match match : thisPlayer.getMatchHistory())
            games.getChildren().add(matchGroup(match));
    }

    private Group matchGroup(Match match) {
        Group ret = new Group();

        Player thisPlayer = Player.getCurrentPlayer();
        String thisName = thisPlayer.getUsername();
        String opponentName = match.getPlayers()[0].getUsername().equalsIgnoreCase(thisName) ? match.getPlayers()[1]
                .getUsername() : match.getPlayers()[0].getUsername();

        Label name = new Label("Name : " + opponentName);
        name.setTextFill(Color.WHITE);
        name.setFont(Font.font(18));
        name.relocate(25, 10);

        int winner = -1;
        if (match.getWinner() != null) {
            if (match.getWinner().getUsername().equals(thisName)) {
                winner = 1;
            } else {
                winner = 2;
            }
        }


        ImageView imageView = new ImageView();
        try {
            if (winner == -1) {
                imageView.setImage(new Image(new FileInputStream
                        ("src\\assets\\MatchHistory\\pause.png")));
            } else if (winner == 1) {
                imageView.setImage(new Image(new FileInputStream
                        ("src\\assets\\MatchHistory\\win.png")));
            } else {
                imageView.setImage(new Image(new FileInputStream
                        ("src\\assets\\MatchHistory\\loose.png")));
            }
        } catch (IOException ex) {
            View.printThrowable(ex);
        }
        imageView.relocate(300, -10);

        Label time = new Label(match.getGameTime().toString());
        time.relocate(500, 10);
        time.setFont(Font.font(18));
        time.setTextFill(Color.WHITE);

        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(800);
        rectangle.setHeight(50);
        rectangle.setFill(Color.rgb(128, 0, 255));
        ret.getChildren().addAll(rectangle, name, imageView, time);
        return ret;
    }

    private void handleButtons() {
        back.setOnAction(event -> View.getInstance().back());

        volume.setOnMouseClicked(event -> VolumeController.getInstance().run());
    }


    private void setBackground() {
        try {
            ImageView background = new ImageView(new Image(new FileInputStream("src\\assets\\MatchHistory" +
                    "\\background.jpg")));
            background.fitWidthProperty().bind(scene.widthProperty());
            background.fitHeightProperty().bind(scene.heightProperty());
            root.getChildren().add(background);
        } catch (Exception e) {
            View.printThrowable(e);
        }
    }

}
