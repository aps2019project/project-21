package view;

import controller.menus.MainMenu;
import javafx.animation.AnimationTimer;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import models.Player;
import models.match.Match;
import network.Client;
import network.message.Request;

import java.io.FileInputStream;
import java.io.IOException;

public class WaitingForOppView {
    private static WaitingForOppView current;

    public static WaitingForOppView getCurrent() {
        return current;
    }

    private Group root = new Group();
    private Scene scene = new Scene(root, 1536, 801.59);
    private Group before = new Group();
    private ImageView back = new ImageView();
    private StackPane findingMatch = new StackPane();
    private Group after = new Group();
    private StackPane ready = new StackPane();
    private StackPane withdraw = new StackPane();
    private Match match;

    private String oppName;
    private Player opponent;
    private boolean amIFirst;

    void run() {
        View.setScene(scene);
        scene.setCursor(Cursor.WAIT);
    }

    {
        current = this;

        scene.getStylesheets().add("view/stylesheets/waiting_for_opp_view.css");

        setBackground();

        draw();

        setOnActions();

        sendRequestToHost();

        handleChanges();
    }

    private void draw() {
        drawBefore();

        root.getChildren().addAll(before);
    }

    private void drawBefore() {
        try {
            back = new ImageView(new Image(new FileInputStream
                    ("src/assets/resources/ui/button_back_corner@2x.png")));
            back.setFitWidth(90);
            back.setFitHeight(95);
        } catch (IOException e) {
            View.printThrowable(e);
        }
        try {
            ImageView blue = new ImageView(new Image(new FileInputStream("src/assets/resources/ui/button_primary_left@2x.png")));
            blue.setFitHeight(72);
            blue.setFitWidth(232);
            Label label = new Label("FINDING MATCH...");
            label.setFont(Font.font(20));
            label.setStyle("-fx-text-fill: #9df0ff");
            findingMatch.getChildren().addAll(blue, label);
            findingMatch.relocate(650, 650);
        } catch (IOException ex) {
            View.printThrowable(ex);
        }
        before.getChildren().addAll(back, findingMatch);
    }

    private void drawAfter() {
        if (!after.getChildren().isEmpty())
            return;
        after.getChildren().addAll(withdraw, ready);

        try {
            ImageView confirm = new ImageView(new Image(new FileInputStream("src/assets/resources/ui/button_confirm@2x.png")));
            confirm.setFitWidth(210);
            confirm.setFitHeight(70);
            Label readyLabel = new Label("READY");
            readyLabel.setFont(Font.font(20));
            readyLabel.setStyle("-fx-text-fill: white");
            ready.getChildren().addAll(confirm, readyLabel);
            ready.relocate(470, 650);

            ImageView cancel = new ImageView(new Image(new FileInputStream("src/assets/resources/ui/button_cancel@2x.png")));
            cancel.setFitWidth(210);
            cancel.setFitHeight(70);
            Label withdrawLabel = new Label("WITHDRAW");
            withdrawLabel.setFont(Font.font(20));
            withdrawLabel.setStyle("-fx-text-fill: white");
            withdraw.getChildren().addAll(cancel, withdrawLabel);
            withdraw.relocate(820, 650);
        } catch (IOException ex) {
            View.printThrowable(ex);
        }

        try {
            String n = Integer.toString(Math.abs(Player.getCurrentPlayer().getUsername().hashCode()) % 7 + 1);
            String m = Integer.toString(Math.abs(oppName.hashCode()) % 7 + 1);

            ImageView icon1 = new ImageView(new Image(new FileInputStream("src/assets/generals/general_" + n + ".png")));
            ImageView icon2 = new ImageView(new Image(new FileInputStream("src/assets/generals/general_" + m + ".png")));
            icon1.setScaleX(0.5);
            icon1.setScaleY(icon1.getScaleX());
            icon2.setScaleX(icon1.getScaleY());
            icon2.setScaleY(icon2.getScaleX());
            icon1.relocate(355, 0);
            icon2.relocate(705, 0);

            Label name1 = new Label(Player.getCurrentPlayer().getUsername());
            name1.setTextFill(Color.WHITE);
            name1.setFont(Font.font(30));
            name1.relocate(563, 380);
            Label name2 = new Label(oppName);
            name2.setTextFill(Color.BLACK);
            name2.setFont(Font.font(30));
            name2.relocate(913, 380);

            Label vs = new Label("VS");
            vs.setStyle("-fx-text-fill: #e34350");
            vs.setFont(Font.font(30));
            vs.relocate(730, 300);
            after.getChildren().addAll(name1, name2, icon1, icon2, vs);
        } catch (Exception ex) {
            View.printThrowable(ex);
        }
    }

    private void setOnActions() {
        back.setOnMouseClicked(event -> {
            View.back();
            Client.write(Request.makeWithdrawRequest());
        });
        withdraw.setOnMouseClicked(event -> {
            View.back();
            Client.write(Request.makeWithdrawRequest());
        });
        ready.setOnMouseClicked(event -> Client.write(Request.makeReadyRequest()));
    }

    private void sendRequestToHost() {
        new Thread(() -> {
            //  wait at least 2 seconds
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                View.printThrowable(e);
            }
            Client.write(Request.makeMatchRequest(MainMenu.getInstance().getMatchRequest()));
        }).start();
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
                        drawAfter();
                        if (!root.getChildren().contains(after))
                            root.getChildren().addAll(after);
                        root.getChildren().remove(before);
                        View.setCursor(scene);
                    }
//                    if (opponent != null) {
//                        startMatch();
//                        this.stop();
//                    }
                    last = now;
                }
            }
        }.start();
    }

    public void startMatch() {
        if (amIFirst)
            match.setPlayerOne(Player.getCurrentPlayer());
        else
            match.setPlayerTwo(Player.getCurrentPlayer());
        Match.setCurrentMatch(match);
        BattleView battleView = new BattleView();
        match.setBattleView(battleView);
        battleView.run();
    }

    public void setOpponent(Player opponent, boolean amIFirst) {
        this.opponent = opponent;
        this.amIFirst = amIFirst;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
