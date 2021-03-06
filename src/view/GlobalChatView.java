package view;

import controller.menus.GlobalChatMenu;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Pair;
import models.GlobalChat;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GlobalChatView {
    private static Group root = new Group();
    private static Scene scene = new Scene(root, 1536, 801.59);
    private static VBox messages = new VBox();
    private static TextField input = new TextField();
    private static Button back = new Button("BACK");
    private static ScrollPane scrollPane = new ScrollPane();
    private static VBox onlineUsers = new VBox();
    private static List<String> onlineUsersName = new ArrayList<>();

    static void run() {
        View.setScene(scene);
    }

    static {
        scene.getStylesheets().add("view/stylesheets/global_chat_view.css");

        setBackground();

        draw();

        setOnActions();

        handleChanges();
    }

    private static void draw() {
        back.relocate(10, 10);
        back.setFont(Font.font(20));
        drawMessagePane();
        drawOnlineUsersPane();
        root.getChildren().addAll(back, scrollPane, onlineUsers);
    }

    private static void drawOnlineUsersPane() {
        onlineUsers.relocate(1000, 100);
        onlineUsers.setSpacing(15);
        onlineUsers.getChildren().clear();
        Label label = new Label("ONLINE USERS");
        label.setStyle("-fx-text-fill: white");
        label.setFont(Font.font(30));
        label.relocate(50, 0);
        onlineUsers.getChildren().add(label);

        for (String username : onlineUsersName)
            drawOnlineUser(username);
    }

    private static void drawOnlineUser(String username) {
        Group group = new Group();
        Label name = new Label(username);
        name.setStyle("-fx-text-fill: white");
        name.setFont(Font.font(22));
        name.relocate(80, 5);
        group.getChildren().addAll(name);
        try {
            String n = Integer.toString(Math.abs(username.hashCode()) % 17 + 1);
            ImageView icon = new ImageView(new Image(new FileInputStream("src/assets/profile_icons/icon_" + n + ".png")));
            icon.setFitHeight(50);
            icon.setFitWidth(50);
            group.getChildren().add(icon);
        } catch (IOException ex) {
            View.printThrowable(ex);
        }
        onlineUsers.getChildren().add(group);
    }

    private static void drawMessagePane() {
        input.relocate(510, 700);
        input.setPrefWidth(250);
        messages.setSpacing(15);
        scrollPane.relocate(450, 100);
        scrollPane.setContent(messages);
        scrollPane.setMaxHeight(560);
        scrollPane.setMaxWidth(350);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        root.getChildren().addAll(input);

        drawMessages();
    }

    private static void drawMessages() {
        for (Pair<String, String> msgPair : GlobalChat.getInstance().getMessages())
            drawMessage(msgPair);
    }

    private static void drawMessage(Pair<String, String> msgPair) {
        if (msgPair == null)
            return;
        Group group = new Group();

        Rectangle rectangle = new Rectangle(250, 50);
        rectangle.relocate(60, 0);
        rectangle.setStyle("-fx-fill: rgba(255,255,255,0.45)");
        Label name = new Label(msgPair.getKey());
        name.setStyle("-fx-text-fill: #4c80ff");
        name.setFont(Font.font(12));
        name.relocate(75, 3);
        Label msg = new Label(msgPair.getValue());
        msg.relocate(75, 22);
        msg.setFont(Font.font(16));
        group.getChildren().addAll(rectangle, name, msg);
        try {
            String n = Integer.toString(Math.abs(msgPair.getKey().hashCode()) % 17 + 1);
            ImageView icon = new ImageView(new Image(new FileInputStream("src/assets/profile_icons/icon_" + n + ".png")));
            icon.setFitHeight(50);
            icon.setFitWidth(50);
            group.getChildren().add(icon);
        } catch (IOException ex) {
            View.printThrowable(ex);
        }
        messages.getChildren().add(group);
    }

    private static void updateMessages() {
        if (messages.getChildren().size() < GlobalChat.getInstance().getMessages().size())
            drawMessage(GlobalChat.getInstance().getMessages().get(messages.getChildren().size()));
    }

    private static void setOnActions() {
        back.setOnAction(event -> View.back());
        input.setOnAction(event -> {
            GlobalChatMenu.getInstance().addMessage(input.getText());
            input.setText("");
        });
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

    private static void handleChanges() {
        new AnimationTimer() {
            long last;

            @Override
            public void handle(long now) {
                if (now - last > 100) {
                    updateMessages();
                    drawOnlineUsersPane();
                    last = now;
                }
            }
        }.start();
    }

    public static void setOnlineUsersName(List<String> onlineUsersName) {
        GlobalChatView.onlineUsersName = onlineUsersName;
    }

    public static void hideInputTextfield() {  // for host
        root.getChildren().remove(input);
    }
}
