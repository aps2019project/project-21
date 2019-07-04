package view;

import controller.menus.GlobalChatMenu;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import models.GlobalChat;

import java.io.FileInputStream;

public class GlobalChatView {
    private static GlobalChatView instance = new GlobalChatView();

    private GlobalChatView() {
    }

    public static GlobalChatView getInstance() {
        return instance;
    }

    private Group root = new Group();
    private Scene scene = new Scene(root, 1536, 801.59);
    private VBox messages = new VBox();
    private TextField input = new TextField();
    private Button back = new Button("BACK");

    void run() {
        View.getInstance().setScene(scene);
    }

    {
        scene.getStylesheets().add("view/stylesheets/global_chat_view.css");

//        setBackground();

        draw();

        setOnActions();

        handleChanges();
    }

    private void draw() {
        back.relocate(10, 10);
        messages.relocate(100, 100);
        input.relocate(100, 700);
        root.getChildren().addAll(messages, input, back);

        drawMessages();
    }

    private void drawMessages() {
        for (Pair<String, String> msgPair : GlobalChat.getInstance().getMessages())
            drawMessage(msgPair);
    }

    private void drawMessage(Pair<String, String> msgPair) {
        if (msgPair == null)
            return;
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        Label name = new Label(msgPair.getKey());
        Label msg = new Label(msgPair.getValue());
        hBox.getChildren().addAll(name, msg);
        messages.getChildren().add(hBox);
    }

    private void updateMessages() {
        if (messages.getChildren().size() < GlobalChat.getInstance().getMessages().size())
            drawMessage(GlobalChat.getInstance().getMessages().get(messages.getChildren().size()));
    }

    private void setOnActions() {
        back.setOnAction(event -> View.getInstance().back());
        input.setOnAction(event -> {
            GlobalChatMenu.getInstance().addMessage(input.getText());
            input.setText("");
        });
    }

    private void setBackground() {
        try {
            ImageView background = new ImageView(new Image(new FileInputStream
                    ("src/assets/resources/scenes/load/scene_load_background@2x.jpg")));
            background.fitWidthProperty().bind(scene.widthProperty());
            background.fitHeightProperty().bind(scene.heightProperty());
            root.getChildren().add(background);
        } catch (Exception e) {
            View.printThrowable(e);
        }
    }

    private void handleChanges() {
        new AnimationTimer() {
            long last;

            @Override
            public void handle(long now) {
                if (now - last > 100) {
                    updateMessages();
                    last = now;
                }
            }
        }.start();
    }
}
