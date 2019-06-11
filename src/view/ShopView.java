package view;

import controller.menus.ShopMenu;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import models.Item.Usable;
import models.Player;
import models.card.Card;
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

    private int selectedId = -1;
    private String selectedName = null;

    private int buttonSelect = 1;
    private int cardType = 2;
    private Group root = new Group();
    private Scene scene = new Scene(root, 1536, 801.59);
    private Button back = new Button("BACK");
    private Button myCollection = new Button("COLLECTION");
    private Button shop = new Button("SHOP");
    private Button minion = new Button("MINION");
    private Button hero = new Button("HERO");
    private Button spell = new Button("SPELL");
    private Button item = new Button("ITEM");
    private TilePane items = new TilePane();
    private ScrollPane scrollPane = new ScrollPane();
    private TextField search = new TextField();
    private Button searchCard = new Button("SEARCH(SELECT IF EXIST)");
    private Button buySell = new Button("SELL");
    private Group searchedCard = new Group();
    private Label drake = new Label("DRAKE : " + Player.getCurrentPlayer().getDrake());


    void run() {
        View.getInstance().setScene(scene);
    }

    {
        scene.getStylesheets().add("view/stylesheets/shop_view.css");

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
        back.relocate(100, 475);
        myCollection.relocate(100, 350);
        shop.relocate(100, 225);
        drake.relocate(100, 100);

        minion.relocate(900, 50);
        hero.relocate(300, 50);
        spell.relocate(500, 50);
        item.relocate(700, 50);


        search.relocate(1000, 100);
        buySell.relocate(1000, 600);
        searchCard.relocate(1100,600);

        showCards();

        root.getChildren().addAll(drake, spell, hero, minion, item, myCollection, shop,
                back, search, buySell, searchCard, scrollPane);
    }

    private void showCards() {
        items.getChildren().clear();

        items.setHgap(20);
        items.setVgap(30);
        items.setPrefColumns(3);

        showByType();

        scrollPane.setContent(items);
        scrollPane.setPannable(true);
        scrollPane.relocate(200, 100);
        scrollPane.setMaxHeight(500);
        scrollPane.setMaxWidth(700);
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
                        if (minion != null) {
                            CardView.showCard(minion, items);
                        }
                    }
                    break;
                case 2:
                    for (Hero hero : thisPlayer.getCollection().getHeroes()) {
                        if (hero != null) {
                            CardView.showCard(hero, items);
                        }
                    }
                    break;
                case 3:
                    for (Spell spell : thisPlayer.getCollection().getSpells()) {
                        if (spell != null) {
                            CardView.showCard(spell, items);
                        }
                    }
                    break;
                case 4:
                    for (Usable usable : thisPlayer.getCollection().getUsables()) {
                        if (usable != null) {
                            CardView.showCard(usable, items);
                        }
                    }
                    break;
            }
        } else {
            switch (cardType) {
                case 1:
                    for (Minion minion : Minion.getMinions()) {
                        if (minion != null) {
                            CardView.showCard(minion, items);
                        }
                    }
                    break;
                case 2:
                    for (Hero hero : Hero.getHeroes()) {
                        if (hero != null) {
                            CardView.showCard(hero, items);
                        }
                    }
                    break;
                case 3:
                    for (Spell spell : Spell.getSpells()) {
                        if (spell != null) {
                            CardView.showCard(spell, items);
                        }
                    }
                    break;
                case 4:
                    for (Usable usable : Usable.getUsables()) {
                        if (usable != null) {
                            CardView.showCard(usable, items);
                        }
                    }
                    break;
            }
        }
    }


    private void setOnActions() {
        back.setOnAction(event -> View.getInstance().back());

        minion.setOnAction(event -> {
            cardType = 1;
            showCards();
        });

        hero.setOnAction(event -> {
            cardType = 2;
            showCards();
        });

        spell.setOnAction(event -> {
            cardType = 3;
            showCards();
        });

        item.setOnAction(event -> {
            cardType = 4;
            showCards();
        });

        myCollection.setOnAction(event -> {
            if (buttonSelect != 1) {
                buttonSelect = 1;
                buySell.setText("SELL");
                items.getChildren().clear();
                showCards();

                root.getChildren().remove(searchedCard);
                selectedName = null;
                selectedId = -1;
            }
        });

        shop.setOnAction(event -> {
            if (buttonSelect != 2) {
                buttonSelect = 2;
                buySell.setText("BUY");
                items.getChildren().clear();

                showCards();

                root.getChildren().remove(searchedCard);
                selectedName = null;
                selectedId = -1;
            }
        });

        searchCard.setOnAction(event -> {
            String name = search.getCharacters().toString();
            if (buttonSelect == 2) {
                Card message = ShopMenu.search(name);
                if (message == null) {
                    View.getInstance().popup("No card with this name");
                } else {
                    selectedName = name;
                    selectedId = -1;
                    searchedCard = CardView.shopCardGroup(message);
                    searchedCard.relocate(950, 200);
                    root.getChildren().add(searchedCard);
                }
            } else {
                Card message = ShopMenu.searchCollection(name);
                if (message == null) {
                    View.getInstance().popup("You haven't this card");
                } else {
                    selectedId = message.getId();
                    selectedName = null;
                    searchedCard = CardView.shopCardGroup(message);

                    searchedCard.relocate(950, 200);
                    root.getChildren().add(searchedCard);

                }
            }
        });

        buySell.setOnAction(event -> {
            if (buttonSelect == 2) {
                if (selectedName == null) {
                    View.getInstance().popup("No card selected");
                } else {
                    String mes = ShopMenu.buy(selectedName);
                    View.getInstance().popup(mes);
                    if (mes.equals("Buy successful")) {
                        drake.setText("DRAKE : " + Player.getCurrentPlayer().getDrake());
                    }
                }
            } else {
                if (selectedId == -1) {
                    View.getInstance().popup("No card selected");
                } else {
                    String mes = ShopMenu.sell(selectedId);
                    View.getInstance().popup(mes);
                    if (mes.equals("Sell successful")) {
                        drake.setText("DRAKE : " + Player.getCurrentPlayer().getDrake());
                    }
                }
            }
            root.getChildren().remove(searchedCard);
            selectedName = null;
            selectedId = -1;
        });
    }

    private void handleButtons() {
        List<Node> nodes = new ArrayList<>(root.getChildren());
        for (Node node : nodes)
            if (node instanceof Button) {
                Button button = (Button) node;
            }
    }
}
