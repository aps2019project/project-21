package view;

import javafx.scene.Group;
import javafx.scene.Node;
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
import java.util.ArrayList;
import java.util.List;

public class ShopView {
    private static ShopView instance = new ShopView();

    public static ShopView getInstance() {
        return instance;
    }

    private ShopView() {
    }

    private int buttonSelect = 1;
    private int cardType = 1;
    private Group root = new Group();
    private Scene scene = new Scene(root);
    private Button back = new Button("BACK");
    private Button myCollection = new Button("COLLECTION");
    private Button shop = new Button("SHOP");
    private Button minion = new Button("MINION");
    private Button hero = new Button("HERO");
    private Button spell = new Button("SPELL");
    private Button item = new Button("ITEM");
    private TilePane items = new TilePane();
    private ScrollPane scrollPane = new ScrollPane();


    void run() {
        View.getInstance().setScene(scene);
        scene.getStylesheets().add("view/style.css");
    }

    {
        setBackground();

        draw();

        handleButtons();

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

        minion.relocate(900, 50);
        hero.relocate(300, 50);
        spell.relocate(500, 50);
        item.relocate(700, 50);



        showCards();

        root.getChildren().addAll(spell, hero, minion, item, myCollection, shop, back, scrollPane);
    }

    private void showCards() {
        items.getChildren().clear();

        items.setHgap(0);
        items.setVgap(0);
        items.setPrefColumns(3);

        showByType();
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
        items.getChildren().clear();
        if (buttonSelect == 1) {
            Player thisPlayer = Player.getCurrentPlayer();
            switch (cardType) {
                case 1:
                    for (Minion minion : thisPlayer.getCollection().getMinions()) {
                        if (minion != null)
                            CardView.showCard(minion, items);
                    }
                    break;
                case 2:
                    for (Hero hero : thisPlayer.getCollection().getHeroes()) {
                        if (hero != null)
                            CardView.showCard(hero, items);
                    }
                    break;
                case 3:
                    for (Spell spell : thisPlayer.getCollection().getSpells()) {
                        if (spell != null)
                            CardView.showCard(spell, items);
                    }
                    break;
                case 4:
                    for (Usable usable : thisPlayer.getCollection().getUsables()) {
                        if (usable != null)
                            CardView.showCard(usable, items);
                    }
                    break;
            }
        } else {
            switch (cardType) {
                case 1:
                    for (Minion minion : Minion.getMinions()) {
                        if (minion != null)
                            CardView.showCard(minion, items);
                    }
                    break;
                case 2:
                    for (Hero hero : Hero.getHeroes()) {
                        if (hero != null)
                            CardView.showCard(hero, items);
                    }
                    break;
                case 3:
                    for (Spell spell : Spell.getSpells()) {
                        if (spell != null)
                            CardView.showCard(spell, items);
                    }
                    break;
                case 4:
                    for (Usable usable : Usable.getUsables()) {
                        if (usable != null)
                            CardView.showCard(usable, items);
                    }
                    break;
            }
        }
    }


    private void setOnActions() {
        back.setOnAction(event -> View.getInstance().back());

        minion.setOnAction(event -> {
            cardType = 1;
        });
        hero.setOnAction(event -> {
            cardType = 2;
        });
        spell.setOnAction(event -> {
            cardType = 3;
        });
        item.setOnAction(event -> {
            cardType = 4;
        });

        myCollection.setOnAction(event -> {
            if (buttonSelect != 1 ){
                buttonSelect = 1;
                items.getChildren().clear();
                showCards();
            }
        });
        shop.setOnAction(event -> {
            if (buttonSelect != 2){
                buttonSelect = 2;
                items.getChildren().clear();
                showCards();
            }
        });
    }

    private void handleButtons() {
        List<Node> nodes = new ArrayList<>(root.getChildren());
        for (Node node : nodes)
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setOnMouseEntered(event -> {
                    button.setTranslateX(20);
                    button.setTranslateY(5);
                });
                button.setOnMouseExited(event -> {
                    button.setTranslateX(0);
                    button.setTranslateY(0);
                });
                button.setStyle("-fx-background-color: transparent; -fx-text-fill: black;");
            }
    }
}
