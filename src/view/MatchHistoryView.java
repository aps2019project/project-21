package view;

import javafx.animation.AnimationTimer;
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
import json.CardMaker;
import models.BattleAction;
import models.Player;
import models.match.GameMode;
import models.match.Match;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

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
        View.setScene(scene);
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

        root.getChildren().addAll(scrollPane);
    }

    private void showMatchHistory() {
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
        imageView.relocate(250, 0);

        Label time = new Label(match.getGameTime().toString());
        time.relocate(400, 10);
        time.setFont(Font.font(18));
        time.setTextFill(Color.WHITE);


        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(800);
        rectangle.setHeight(50);
        rectangle.setFill(Color.rgb(128, 0, 255));
        ret.getChildren().addAll(rectangle, name, imageView, time);

        Button button = new Button("REPLAY");
        button.relocate(650, 10);
        button.setTextFill(Color.WHITE);
        button.setFont(Font.font(17));
        button.setOnAction(event -> replay(match));
        if (match.getGameMode() == GameMode.SINGLE_PLAYER && match.getWinner() == null) {
            button.setText("CONTINUE");
            button.setOnAction(event -> continueMatch(match));
        }
        ret.getChildren().addAll(button);

        return ret;
    }

    private void replay(Match m) {
        Match match = CardMaker.deepCopy(m.getInitialCopy(), Match.class);
        if (match == null) {
            View.err("match is null for replay.");
            return;
        }
        Match.setCurrentMatch(match);
        BattleView battleView = new BattleView();
        match.setBattleView(battleView);
        battleView.run();
        List<BattleAction> battleActionList = m.getBattleActions();
        new AnimationTimer() {
            int ind = 0;
            long last;

            @Override
            public void handle(long now) {
                if (now - last > 900000000) {
                    if (ind >= battleActionList.size()) {
                        stop();
                        return;
                    }
                    BattleAction b = battleActionList.get(ind++);
                    match.action(b);
                    last = now;
                }
            }
        }.start();
    }

    private void continueMatch(Match match) {
        Match.setCurrentMatch(match);
        BattleView battleView = new BattleView();
        match.setBattleView(battleView);
        battleView.run();
    }

    private void handleButtons() {
        back.setOnAction(event -> View.back());

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
