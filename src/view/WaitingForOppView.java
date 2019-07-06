package view;

import controller.menus.MainMenu;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Player;
import models.match.Match;
import network.Client;
import network.message.Request;

import java.io.FileInputStream;

public class WaitingForOppView {
    private static WaitingForOppView current;
    private Group root = new Group();
    private Scene scene = new Scene(root, 1536, 801.59);
    private Button ready = new Button("READY");
    private Button withdraw = new Button("WITHDRAW");
    private String oppName;
    private Label oppNameLabel = new Label();
    private Player opponent;
    private boolean amIFirst;

    public static WaitingForOppView getCurrent() {
        return current;
    }

    void run() {
        View.getInstance().setScene(scene);
    }

    {
        current = this;

        scene.getStylesheets().add("view/stylesheets/create_match_view.css");

        setBackground();

        draw();

        setOnActions();

        sendRequestToHost();

        handleChanges();
    }

    private void draw() {
        withdraw.relocate(100, 100);
        oppNameLabel.relocate(100, 200);
        ready.relocate(100, 300);
        root.getChildren().addAll(withdraw);
    }

    private void setOnActions() {
        withdraw.setOnAction(event -> View.getInstance().back());
        ready.setOnAction(event -> Client.write(Request.makeReadyRequest()));
    }

    private void sendRequestToHost() {
//        new Thread(() -> {
//            long time = System.currentTimeMillis();
        //  at least 2 second for withdraw
//            while (System.currentTimeMillis() - time < 2000) ;
//        }).start();
        Client.write(Request.makeMatchRequest(MainMenu.getInstance().getMatchRequest()));
    }

    private void setBackground() {
        try {
            ImageView background = new ImageView(new Image(new FileInputStream
                    ("src/assets/resources/scenes/load/scene_load_background@2x.jpg")));
            BoxBlur boxBlur = new BoxBlur();
            background.setEffect(boxBlur);
            background.fitWidthProperty().bind(scene.widthProperty());
            background.fitHeightProperty().bind(scene.heightProperty());
            root.getChildren().add(background);
        } catch (Exception e) {
            View.printThrowable(e);
        }
    }

    public void takeOppName(String oppName) {
        this.oppName = oppName;
    }

    private void handleChanges() {
        new AnimationTimer() {
            long last;

            @Override
            public void handle(long now) {
                if (now - last > 100) {
                    if (oppName != null) {
                        oppNameLabel.setText(oppName);
                        if (!root.getChildren().contains(ready))
                            root.getChildren().addAll(ready);
                        if (!root.getChildren().contains(oppNameLabel))
                            root.getChildren().addAll(oppNameLabel);
                    }
                    if (opponent != null) {
                        startMatch();
                        this.stop();
                    }
                    last = now;
                }
            }
        }.start();
    }

    private void startMatch() {
        Match match;
        if (amIFirst)
            match = new Match(Player.getCurrentPlayer(), opponent, MainMenu.getInstance().getMatchRequest());
        else
            match = new Match(opponent, Player.getCurrentPlayer(), MainMenu.getInstance().getMatchRequest());
        Match.setCurrentMatch(match);
        BattleView battleView = new BattleView();
        match.setBattleView(battleView);
        battleView.run();
    }

    public void setOpponent(Player opponent, boolean amIFirst) {
        this.opponent = opponent;
        this.amIFirst = amIFirst;
    }
}
