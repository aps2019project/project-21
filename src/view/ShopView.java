package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

import java.io.FileInputStream;

public class ShopView {
    private static ShopView instance = new ShopView();

    public static ShopView getInstance() {
        return instance;
    }

    private ShopView() {
    }

    private Group root = new Group();
    private Scene scene = new Scene(root);
    private Button back = new Button("BACK");
    private TilePane items = new TilePane();
    private ScrollPane scrollPane = new ScrollPane();


    void run() {
        View.getInstance().setScene(scene);
        scene.getStylesheets().add("view/style.css");
    }

    {
        setBackground();

        draw();

        setOnActions();
    }

    private void setBackground() {
        try {
            ImageView background = new ImageView(new Image(new FileInputStream("src\\assets\\shop.jpg")));
            background.fitWidthProperty().bind(scene.widthProperty());
            background.fitHeightProperty().bind(scene.heightProperty());
            root.getChildren().add(background);
        } catch (Exception e) {
            View.printThrowable(e);
        }
    }

    private void draw() {
        back.relocate(100, 100);


        items.setHgap(20);
        items.setVgap(30);
        items.setPrefColumns(5);

        for (int i = 0; i < 20; i++)
            try {
                StackPane stackPane = new StackPane();
                ImageView background = new ImageView(new Image(new FileInputStream("src\\assets\\card_background.png")));
                stackPane.getChildren().addAll(background);
                items.getChildren().addAll(stackPane);
            } catch (Exception e) {
                View.printThrowable(e);
            }
        scrollPane.setContent(items);
        scrollPane.setPannable(true);
        scrollPane.relocate(150, 100);
        scrollPane.setMaxHeight(500);
        scrollPane.setMaxWidth(1000);
        scrollPane.setStyle("-fx-background-color: transparent");
        scrollPane.setStyle("}.scroll-pane > .viewport {\n" +
                "   -fx-background-color: transparent;}\n" +
                "{");
        items.setStyle("-fx-background-color: transparent");
//        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
//        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        root.getChildren().addAll(back, scrollPane);
    }

    private void setOnActions() {
        back.setOnAction(event -> View.getInstance().back());
    }
}
