package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;

public class BattleView {
    private Group root = new Group();
    private Scene scene = new Scene(root, 1536, 801.59);
    private int tileSize = 73;
    private int valleySize = 5;
    private Group table = new Group();

    public void run() {
        View.getInstance().setScene(scene);
    }

    {
        scene.getStylesheets().add("view/stylesheets/battle_view.css");

        setBackground();

        drawField();

        drawHub();
    }


    private void drawField() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                Rectangle rectangle = new Rectangle(tileSize, tileSize);
                rectangle.relocate(j * (tileSize + valleySize), i * (tileSize + valleySize));
                rectangle.setStyle("-fx-fill:  rgba(0,0,0,0.2)");
                table.getChildren().add(rectangle);
            }
        }
        PerspectiveTransform p = new PerspectiveTransform();
        int dx = -30;
        int dy = -5;
        p.setUlx(500 + dx);
        p.setUly(250 + dy);
        p.setUrx(1100 + dx);
        p.setUry(250 + dy);
        p.setLlx(450 + dx);
        p.setLly(600 + dy);
        p.setLrx(1150 + dx);
        p.setLry(600 + dy);
        table.setEffect(p);
        root.getChildren().add(table);
    }

    private void drawHub() {

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
        try {
            ImageView midground = new ImageView(new Image(new FileInputStream("src/assets/resources/maps/vanar/midground@2x.png")));
            midground.fitWidthProperty().bind(scene.widthProperty());
            midground.fitHeightProperty().bind(scene.heightProperty());
            root.getChildren().add(midground);
        } catch (Exception e) {
            View.printThrowable(e);
        }
    }
}
