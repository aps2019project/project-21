package view;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
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
    public static View getInstance() {
        return view;
    }

    private View() {
    }

    private static View view = new View();

    private boolean isAIPlaying;
    private Stack<Scene> scenes = new Stack<>();
    private Stage primaryStage;
    private Queue<String> messagesQueue = new ArrayDeque<>();

    public static void printThrowable(Throwable throwable) {
        System.err.println("error occurred:");
        throwable.printStackTrace();
        System.err.flush();
    }

    public static void err(String message) {
        System.err.println(message);
        System.err.flush();
    }

    public void run() {
        primaryStage.setTitle("Duelyst");
        primaryStage.setMaximized(true);
        handlePopups();
        MainMenuView.getInstance().run();
    }

    public void runForHost() {
        primaryStage.setTitle("Host");
        primaryStage.setMaximized(true);
//        primaryStage.setIconified(true);
        handlePopups();
        HostView.getInstance().run();
    }

    public void setScene(Scene scene) {
        this.primaryStage.setScene(scene);
        this.scenes.push(scene);
        setCursor(scene);
    }

    public void back() {
        scenes.pop();
        primaryStage.setScene(scenes.peek());
    }

    void setCursor(Scene scene) {
        try {
            Image cursor = new Image(
                    new FileInputStream("src/assets/resources/ui/mouse_auto@2x.png"));
            scene.setCursor(new ImageCursor(cursor));
        } catch (IOException e) {
            View.printThrowable(e);
        }
    }

    public void setAIPlaying(boolean AIPlaying) {
        isAIPlaying = AIPlaying;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    Stage getPrimaryStage() {
        return primaryStage;
    }

    void exit() {
        primaryStage.close();
    }

    public void popup(String message) {
        if (isAIPlaying)
            return;
        final Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(primaryStage);
        popup.setResizable(false);
        Label label = new Label(message);
        Button ok = new Button("OK");
        label.relocate(10, 50);
        ok.relocate(100, 100);
        Group group = new Group();
        group.getChildren().addAll(label, ok);
        Scene dialogScene = new Scene(group, 300, 150);
        ok.setOnAction(event -> popup.close());
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

    public void addPopupMessage(String message) {
        messagesQueue.add(message);
    }

    private void handlePopups() {
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

    public void printMessage(Message msg) {
        if (msg == null)
            return;
        popup(msg.getMessage());
    }

    public void showGameInfo() {
        Match match = Match.getCurrentMatch();
        if (match == null)
            return;
        String message = "";
        if (match.getGoalMode() == GoalMode.KILL_HERO) {
            for (PlayerMatchInfo p : match.getPlayersMatchInfo())
                message += p.getHero().getCardIDInGame() + "'s hp: " + p.getHero().getHP();
        } else if (match.getGoalMode() == GoalMode.HOLD_FLAG) {
            if (match.whoHasFlag() != null)
                message += match.whoHasFlag().getName();
            message += "flags position: (" + match.getFlags().get(0).getCurrentCell().getX() + ", "
                    + match.getFlags().get(0).getCurrentCell().getY() + ")";
        } else if (match.getGoalMode() == GoalMode.GATHER_FLAG) {
            message += "These guys have the flags: ";
            for (Attacker attacker : match.whoHasFlags())
                message += attacker.getCardIDInGame() + " in team " + (match.getCardsTeam(attacker) + 1);
        }
        view.popup(message);
    }

    public void showMatchResults(Match match) {
        popup("Player " + match.getWinner().getUsername() + " won " + match.getLoser().getUsername() + ".");
    }
}
