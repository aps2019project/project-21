package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.IOException;

public class CustomCardView {
    private static CustomCardView instance = new CustomCardView();

    public static CustomCardView getInstance() {
        return instance;
    }

    public void run() {
        View.getInstance().setScene(scene);
    }

    private Group root = new Group();
    private Scene scene = new Scene(root, 1536, 801.59);
    private Label back = new Label("BACK");
    private Label ok = new Label("OK");
    private Label name = new Label("Name");
    private TextField Name = new TextField();
    private Label type = new Label("TYPE");
    private TextField Type = new TextField();

    private ImageView volume = new ImageView();

    {
        try {
            volume.setImage(new Image(new FileInputStream("src/assets/volume.png")));
            volume.setScaleY(0.1);
            volume.setScaleX(0.1);
        } catch (IOException ex) {
            View.printThrowable(ex);
        }
        volume.relocate(1000, 450);

        scene.getStylesheets().add("view/stylesheets/shop_view.css");

        setBackground();

        //draw();

        //setOnActions();

    }

    private void setBackground() {
        try {
            ImageView background = new ImageView(new Image(new FileInputStream("src\\assets\\cards" +
                    "\\shop.jpg")));
            background.fitWidthProperty().bind(scene.widthProperty());
            background.fitHeightProperty().bind(scene.heightProperty());
            root.getChildren().add(background);
        } catch (Exception e) {
            View.printThrowable(e);
        }
    }
}
