package view;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.card.Attacker;
import models.match.GoalMode;
import models.match.Match;
import models.match.PlayerMatchInfo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class View {
    private static boolean isOppPlaying;
    private static Stack<Scene> scenes = new Stack<>();
    private static Stage primaryStage;
    private static Queue<String> messagesQueue = new ArrayDeque<>();

    public static void printThrowable(Throwable throwable) {
        System.err.println("exception:");
        throwable.printStackTrace();
        System.err.flush();
    }

    public static void err(String message) {
        System.err.println(message);
        System.err.flush();
    }

    public static void run(Stage primaryStage) {
        View.primaryStage = primaryStage;
        primaryStage.setTitle("Duelyst");
        primaryStage.setMaximized(true);
        handlePopups();
        MainMenuView.getInstance().run();
    }

    public static void runForHost(Stage primaryStage) {
        View.primaryStage = primaryStage;
        primaryStage.setTitle("Host");
        primaryStage.setIconified(true);
        handlePopups();
        HostView.getInstance().run();
    }

    public static void setScene(Scene scene) {
        primaryStage.setScene(scene);
        scenes.push(scene);
        setCursor(scene);
        VoicePlay.setScene();
    }

    public static void back() {
        scenes.pop();
        primaryStage.setScene(scenes.peek());
        VoicePlay.back();
    }

    static void setCursor(Scene scene) {
        try {
            Image cursor = new Image(
                    new FileInputStream("src/assets/resources/ui/mouse_auto@2x.png"));
            scene.setCursor(new ImageCursor(cursor));
        } catch (IOException e) {
            View.printThrowable(e);
        }
    }

    public static void setIsOppPlaying(boolean isOppPlaying) {
        View.isOppPlaying = isOppPlaying;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        View.primaryStage = primaryStage;
    }

    static Stage getPrimaryStage() {
        return primaryStage;
    }

    static void exit() {
        primaryStage.close();
    }

    public static void popup(String message) {
        if (isOppPlaying || message == null)
            return;
        final Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(primaryStage);
        popup.setResizable(false);
        Label label = new Label(message);
        Label okLabel = new Label("OK");
        okLabel.setFont(Font.font(20));
        okLabel.setTextFill(Color.WHITE);
        Group group = new Group();
        Rectangle background = new Rectangle(500, 210);
        background.setStyle("-fx-fill: white");
        group.getChildren().add(background);
        StackPane s = new StackPane();
        label.relocate(10, 50);
        s.relocate(120, 120);
        try {
            ImageView i = new ImageView(new Image(new FileInputStream("src/assets/resources/ui/button_cancel.png")));
            i.setFitHeight(50);
            i.setFitWidth(150);
            i.setOnMouseClicked(event -> popup.close());
            group.getChildren().add(i);
            s.getChildren().addAll(i, okLabel);

        } catch (IOException e) {
            View.printThrowable(e);
        }
        group.getChildren().addAll(label, s);
        Scene dialogScene = new Scene(group, 380, 171);
        dialogScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                popup.close();
        });
        try {
            Image cursor = new Image(
                    new FileInputStream("src/assets/resources/ui/mouse_auto@2x.png"));
            dialogScene.setCursor(new ImageCursor(cursor));
        } catch (IOException e) {
            View.printThrowable(e);
        }
        popup.setScene(dialogScene);
        VoicePlay.notification();
        popup.show();
    }

    public static void addPopupMessage(String message) {
        messagesQueue.add(message);
    }

    private static void handlePopups() {
        new AnimationTimer() {
            long last;

            @Override
            public void handle(long now) {
                if (now - last > 100) {  //  try blocking queue
                    if (!messagesQueue.isEmpty())
                        popup(messagesQueue.poll());
                    last = now;
                }
            }
        }.start();
    }

    public static void printMessage(Message msg) {
        if (msg == null)
            return;
        popup(msg.getMessage());
    }

    public static void showGameInfo() {
        Match match = Match.getCurrentMatch();
        if (match == null)
            return;
        String message = "";
        if (match.getGoalMode() == GoalMode.KILL_HERO) {
            for (PlayerMatchInfo p : match.getPlayersMatchInfo())
                message = message.concat(p.getHero().getCardIDInGame() + "'s hp: " + p.getHero().getHP());
        } else if (match.getGoalMode() == GoalMode.HOLD_FLAG) {
            if (match.whoHasFlag() != null)
                message += match.whoHasFlag().getName();
            message += "flags position: (" + match.getFlags().get(0).getCurrentCell().getX() + ", "
                    + match.getFlags().get(0).getCurrentCell().getY() + ")";
        } else if (match.getGoalMode() == GoalMode.GATHER_FLAG) {
            message += "These guys have the flags: ";
            for (Attacker attacker : match.whoHasFlags())
                message = message.concat(attacker.getCardIDInGame() + " in team " + (match.getCardsTeam(attacker) + 1));
        }
        popup(message);
    }

    public static void showMatchResults(Match match) {
        popup("Player " + match.getWinner() + " won " + match.getLoser() + ".");
    }

    static void giveGlowEffect(Node node) {
        Glow glow = new Glow();
        node.setEffect(glow);
        node.setOnMouseEntered(event -> glow.setLevel(1));
        node.setOnMouseExited(event -> glow.setLevel(0));
    }

    static void buttonEffect(Button button) {
        Glow glow = new Glow();
        button.setEffect(glow);
        button.setOnMouseClicked(event -> {
            Platform.runLater(VoicePlay::select);
        });
        button.setOnMouseEntered(event -> {
            glow.setLevel(1);
            VoicePlay.hover();
        });
        button.setOnMouseExited(event -> {
            glow.setLevel(0);
        });
    }
}
