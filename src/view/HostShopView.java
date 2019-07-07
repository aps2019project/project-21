package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import models.card.Card;

import java.io.FileInputStream;
import java.io.IOException;

public class HostShopView {
    private static Group root = new Group();
    private static Scene scene = new Scene(root, 1536, 801.59);
    private static ScrollPane scrollPane = new ScrollPane();
    private static TilePane tilePane = new TilePane();
    private static ImageView back = new ImageView();

    static void run() {
        View.getInstance().setScene(scene);
    }

    static {
        scene.getStylesheets().add("view/stylesheets/shop_view.css");

        setBackground();

        draw();

        setOnActions();
    }

    private static void setBackground() {
        try {
            ImageView background = new ImageView(new Image(new FileInputStream("src/assets/resources/maps/redrock/background@2x.jpg")));
            background.fitWidthProperty().bind(scene.widthProperty());
            background.fitHeightProperty().bind(scene.heightProperty());
            root.getChildren().add(background);
        } catch (Exception e) {
            View.printThrowable(e);
        }
    }

    private static void draw() {
        drawBack();

        drawScrollbar();

        root.getChildren().addAll(scrollPane, back);
    }

    private static void drawBack() {
        try {
            back = new ImageView(new Image(new FileInputStream
                    ("src/assets/resources/ui/button_back_corner@2x.png")));
            back.setFitWidth(90);
            back.setFitHeight(95);
        } catch (IOException e) {
            View.printThrowable(e);
        }
    }

    private static void drawScrollbar() {
        tilePane.getChildren().clear();

        tilePane.setHgap(20);
        tilePane.setVgap(30);
        tilePane.setPrefColumns(4);

        drawCards();

        scrollPane.setContent(tilePane);
        scrollPane.setPannable(true);
        scrollPane.relocate(250, 100);
        scrollPane.setMaxHeight(600);
        scrollPane.setMaxWidth(1300);
    }

    public static void drawCards() {
        tilePane.getChildren().clear();
        for (Card c : Card.getCards())
            CardView.showCard(c, tilePane, true, true);
    }

    private static void setOnActions() {
        back.setOnMouseClicked(event -> View.getInstance().back());
    }
}
