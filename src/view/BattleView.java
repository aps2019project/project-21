package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class BattleView {
    private Group root = new Group();
    private Scene scene = new Scene(root, 1536, 801.59);

    public void run() {
        View.getInstance().setScene(scene);
    }

    {
        scene.getStylesheets().add("view/stylesheets/battle_view.css");

        setBackground();

    }

    private void setBackground() {
        try {
            ImageView background = new ImageView(new Image(new FileInputStream("src/assets/resources/maps/vanar/background.jpg")));
            background.fitWidthProperty().bind(scene.widthProperty());
            background.fitHeightProperty().bind(scene.heightProperty());
            root.getChildren().add(background);
        } catch (Exception e) {
            View.printThrowable(e);
        }
    }
}
