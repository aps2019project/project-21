package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import models.Item.Usable;
import models.Player;
import models.card.Hero;
import models.card.Minion;
import models.card.Spell;

import java.io.FileInputStream;

public class ShopView {
    private static ShopView instance = new ShopView();

    public static ShopView getInstance() {
        return instance;
    }

    private ShopView() {
    }

    private int buttonSelect = 1;
    private int minionType = 1;
    private Group root = new Group();
    private Scene scene = new Scene(root);
    private Button back = new Button("BACK");
    private Button myCollection = new Button("COLLECTION");
    private Button shop = new Button("SHOP");
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
        back.relocate(100, 300);
        myCollection.relocate(100, 200);
        shop.relocate(100, 100);

        showMyCollection();

//        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
//        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        root.getChildren().addAll(myCollection, shop, back, scrollPane);
    }

    private void showMyCollection() {

        items.setHgap(20);
        items.setVgap(30);
        items.setPrefColumns(5);


/*
        for (int i = 0; i < 20; i++)
            try {
                StackPane stackPane = new StackPane();
                ImageView background = new ImageView(new Image(new FileInputStream("src\\assets\\card_background.png")));
                stackPane.getChildren().addAll(background);
                items.getChildren().addAll(stackPane);
            } catch (Exception e) {
                View.printThrowable(e);
            }
*/
        scrollPane.setContent(items);
        scrollPane.setPannable(true);
        scrollPane.relocate(200, 100);
        scrollPane.setMaxHeight(500);
        scrollPane.setMaxWidth(1000);
        scrollPane.setStyle("-fx-background-color: transparent");
        scrollPane.setStyle("}.scroll-pane > .viewport {\n" +
                "   -fx-background-color: transparent;}\n" +
                "{");
        items.setStyle("-fx-background-color: transparent");

    }

    private void showByType() {
        if (buttonSelect == 1) {
            Player thisPlayer = Player.getCurrentPlayer();
            switch (minionType) {
                case 1:
                    for (Minion minion : thisPlayer.getCollection().getMinions()) {
                        CardView.showCard(minion, items);
                    }
                    break;
                case 2:
                    for (Hero hero : thisPlayer.getCollection().getHeroes()) {
                        CardView.showCard(hero, items);
                    }
                    break;
                case 3:
                    for (Spell spell : thisPlayer.getCollection().getSpells()) {
                        CardView.showCard(spell, items);
                    }
                    break;
                case 4:
                    for (Usable usable : thisPlayer.getCollection().getUsables()) {
                        CardView.showCard(usable, items);
                    }
                    break;
            }
        } else {

        }
    }

    private void showAllCards() {

    }

    private void setOnActions() {
        back.setOnAction(event -> View.getInstance().back());
    }
}
