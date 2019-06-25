package view;

import controller.menus.ShopMenu;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import models.Item.Usable;
import models.Player;
import models.card.Card;
import models.card.Hero;
import models.card.Minion;
import models.card.Spell;

import java.io.FileInputStream;
import java.io.IOException;

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
    private Label back = new Label("BACK");
    private Label myCollection = new Label("COLLECTION");
    private Label shop = new Label("SHOP");
    private Label minion = new Label("MINION");
    private Label hero = new Label("HERO");
    private Label spell = new Label("SPELL");
    private Label item = new Label("ITEM");
    private TilePane items = new TilePane();
    private ScrollPane scrollPane = new ScrollPane();
    private TextField search = new TextField();
    private Label searchCard = new Label("SEARCH(SELECT IF EXIST)");
    private Label buySell = new Label("SELL");
    private Group searchedCard = new Group();
    private Label drake = new Label("DRAKE : " + Player.getCurrentPlayer().getDrake());
    private static ImageView left;
    private ImageView[] button = new ImageView[3];
    private ImageView[] types = new ImageView[4];
    private ImageView[] rightButtons = new ImageView[2];
    private ImageView volume = new ImageView();

    public static void setInstance(ShopView instance) {
        ShopView.instance = instance;
    }

    void setSelectedId(int selectedId) {
        this.selectedId = selectedId;
    }


    void setSelectedName(String selectedName) {
        this.selectedName = selectedName;
    }


    int getButtonSelect() {
        return buttonSelect;
    }


    void run() {
        View.getInstance().setScene(scene);
    }

    {
        try {
            volume.setImage(new Image(new FileInputStream("src\\assets\\volume.png")));
            volume.setScaleY(0.1);
            volume.setScaleX(0.1);
        } catch (IOException ex){
            View.printThrowable(ex);
        }
        volume.relocate(1000, 450);

        try {
            left = new ImageView(new Image(new FileInputStream
                    ("src\\assets\\cards\\left.png")));
        } catch (IOException ex) {
            View.printThrowable(ex);
        }

        scene.getStylesheets().add("view/stylesheets/shop_view.css");

        setBackground();

        draw();

        setOnActions();

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

    private void draw() {
        root.getChildren().addAll(volume);

        for (int i = 0; i < 2; i++) {
            try {
                rightButtons[i] = new ImageView(new Image(new FileInputStream
                        ("src\\assets\\cards\\button.png")));
                root.getChildren().add(rightButtons[i]);

                if (i == 0) {
                    rightButtons[i].relocate(930, 580);
                } else {
                    rightButtons[i].relocate(1130, 580);
                }
            } catch (IOException ex) {
                View.printThrowable(ex);
            }
        }

        left.setScaleY(20);
        left.setScaleX(2);
        left.relocate(-20, 0);


        back.setTextFill(Color.WHITE);
        myCollection.setTextFill(Color.WHITE);
        shop.setTextFill(Color.WHITE);

        back.relocate(50, 206);
        myCollection.relocate(50, 163);
        shop.relocate(50, 120);
        drake.relocate(50, 50);
        drake.setTextFill(Color.WHITE);
        drake.setScaleX(1.5);


        minion.relocate(50, 322);
        minion.setTextFill(Color.WHITE);
        hero.relocate(50, 365);
        hero.setTextFill(Color.WHITE);
        spell.relocate(50, 408);
        spell.setTextFill(Color.WHITE);
        item.relocate(50, 451);
        item.setTextFill(Color.WHITE);


        search.relocate(1050, 500);
        search.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY,
                Insets.EMPTY)));
        search.setStyle("-fx-text-inner-color: white;");
        buySell.relocate(1000, 600);
        searchCard.relocate(1150, 600);
        buySell.setTextFill(Color.WHITE);
        searchCard.setTextFill(Color.WHITE);

        showCards();

        root.getChildren().addAll(left, drake,
                search, buySell, searchCard, scrollPane);

        for (int i = 0; i < 3; i++) {
            try {
                button[i] = new ImageView(new Image(new FileInputStream
                        ("src\\assets\\cards\\button.png")));
                button[i].relocate(0, 100 + 43 * i);
                button[i].setScaleX(1.2);
                root.getChildren().add(button[i]);
            } catch (IOException ex) {
                View.printThrowable(ex);
            }
        }

        try {
            button[1].setImage(new Image(new FileInputStream("src\\assets\\cards\\onselect.png")));
            button[1].setScaleX(1.5);
            myCollection.setScaleX(1.5);
        } catch (IOException ex) {
            View.printThrowable(ex);
        }


        for (int i = 0; i < 4; i++) {
            try {
                types[i] = new ImageView(new Image(new FileInputStream
                        ("src\\assets\\cards\\button.png")));
                types[i].relocate(0, 300 + 43 * i);
                types[i].setScaleX(1.2);
                root.getChildren().add(types[i]);
            } catch (IOException ex) {
                View.printThrowable(ex);
            }
        }

        try {
            types[1].setImage(new Image(new FileInputStream("src\\assets\\cards\\onselect.png")));
            types[1].setScaleX(1.5);
            hero.setScaleX(1.5);
        } catch (IOException ex) {
            View.printThrowable(ex);
        }

        root.getChildren().addAll(myCollection, shop, back, spell, hero, minion, item);
    }

    private void showCards() {
        items.getChildren().clear();

        items.setHgap(20);
        items.setVgap(30);
        items.setPrefColumns(3);

        showByType();

        scrollPane.setContent(items);
        scrollPane.setPannable(true);
        scrollPane.relocate(220, 70);
        scrollPane.setMaxHeight(500);
        scrollPane.setMaxWidth(800);
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
                            CardView.showCard(minion, items, true);
                        }
                    }
                    break;
                case 2:
                    for (Hero hero : thisPlayer.getCollection().getHeroes()) {
                        if (hero != null) {
                            CardView.showCard(hero, items, true);
                        }
                    }
                    break;
                case 3:
                    for (Spell spell : thisPlayer.getCollection().getSpells()) {
                        if (spell != null) {
                            CardView.showCard(spell, items, true);
                        }
                    }
                    break;
                case 4:
                    for (Usable usable : thisPlayer.getCollection().getUsables()) {
                        if (usable != null) {
                            CardView.showCard(usable, items, true);
                        }
                    }
                    break;
            }
        } else {
            switch (cardType) {
                case 1:
                    for (Minion minion : Minion.getMinions()) {
                        if (minion != null) {
                            CardView.showCard(minion, items, true);
                        }
                    }
                    break;
                case 2:
                    for (Hero hero : Hero.getHeroes()) {
                        if (hero != null) {
                            CardView.showCard(hero, items, true);
                        }
                    }
                    break;
                case 3:
                    for (Spell spell : Spell.getSpells()) {
                        if (spell != null) {
                            CardView.showCard(spell, items, true);
                        }
                    }
                    break;
                case 4:
                    for (Usable usable : Usable.getUsables()) {
                        if (usable != null) {
                            CardView.showCard(usable, items, true);
                        }
                    }
                    break;
            }
        }
    }


    private void setOnActions() {
        button[2].setOnMouseClicked(event ->{
            VoicePlay.buttonPlay();
            View.getInstance().back();
            VoicePlay.setThisMenu("main menu");
        });

        back.setOnMouseClicked(event -> {
            VoicePlay.buttonPlay();
            View.getInstance().back();
            VoicePlay.setThisMenu("main menu");
        });


        minion.setOnMouseClicked(event -> {
            if (cardType != 1) {
                try {
                    setScale(cardType, true);

                    cardType = 1;
                    showCards();
                    VoicePlay.buttonPlay();

                    setScale(cardType, false);
                } catch (IOException ex) {
                    View.printThrowable(ex);
                }
            }
        });

        types[0].setOnMouseClicked(event -> {
            if (cardType != 1) {
                try {
                    setScale(cardType, true);

                    cardType = 1;
                    showCards();
                    VoicePlay.buttonPlay();

                    setScale(cardType, false);
                } catch (IOException ex) {
                    View.printThrowable(ex);
                }
            }
        });

        hero.setOnMouseClicked(event -> {
            if (cardType != 2) {
                try {
                    setScale(cardType, true);

                    cardType = 2;
                    showCards();
                    VoicePlay.buttonPlay();

                    setScale(cardType, false);
                } catch (IOException ex) {
                    View.printThrowable(ex);
                }
            }
        });

        types[1].setOnMouseClicked(event -> {
            if (cardType != 2) {
                try {
                    setScale(cardType, true);

                    cardType = 2;
                    showCards();
                    VoicePlay.buttonPlay();

                    setScale(cardType, false);
                } catch (IOException ex) {
                    View.printThrowable(ex);
                }
            }
        });

        spell.setOnMouseClicked(event -> {
            if (cardType != 3) {
                try {
                    setScale(cardType, true);

                    cardType = 3;
                    showCards();
                    VoicePlay.buttonPlay();

                    setScale(cardType, false);
                } catch (IOException ex) {
                    View.printThrowable(ex);
                }
            }
        });

        types[2].setOnMouseClicked(event -> {
            if (cardType != 3) {
                try {
                    setScale(cardType, true);

                    cardType = 3;
                    showCards();
                    VoicePlay.buttonPlay();

                    setScale(cardType, false);
                } catch (IOException ex) {
                    View.printThrowable(ex);
                }
            }
        });

        item.setOnMouseClicked(event -> {
            if (cardType != 4) {
                try {
                    setScale(cardType, true);

                    cardType = 4;
                    showCards();
                    VoicePlay.buttonPlay();

                    setScale(cardType, false);
                } catch (IOException ex) {
                    View.printThrowable(ex);
                }
            }
        });

        types[3].setOnMouseClicked(event -> {
            if (cardType != 4) {
                try {
                    setScale(cardType, true);

                    cardType = 4;
                    showCards();
                    VoicePlay.buttonPlay();

                    setScale(cardType, false);
                } catch (IOException ex) {
                    View.printThrowable(ex);
                }
            }
        });

        button[1].setOnMouseClicked(event -> {
            if (buttonSelect != 1) {
                buttonSelect = 1;
                buySell.setText("SELL");
                items.getChildren().clear();
                showCards();

                root.getChildren().remove(searchedCard);
                selectedName = null;
                selectedId = -1;
                searchedCard.getChildren().clear();
                VoicePlay.buttonPlay();
                try {
                    button[1].setImage(new Image(new FileInputStream("src\\assets\\cards\\onselect.png")));
                    button[1].setScaleX(1.5);
                    myCollection.setScaleX(1.5);

                    button[0].setScaleX(1.2);
                    button[0].setImage(new Image(new FileInputStream
                            ("src\\assets\\cards\\button.png")));
                    shop.setScaleX(1);
                } catch (IOException ex) {
                    View.printThrowable(ex);
                }
            }
        });

        myCollection.setOnMouseClicked(event -> {
            if (buttonSelect != 1) {
                buttonSelect = 1;
                buySell.setText("SELL");
                items.getChildren().clear();
                showCards();

                root.getChildren().remove(searchedCard);
                selectedName = null;
                selectedId = -1;
                searchedCard.getChildren().clear();
                VoicePlay.buttonPlay();

                try {
                    button[1].setImage(new Image(new FileInputStream("src\\assets\\cards\\onselect.png")));
                    button[1].setScaleX(1.5);
                    myCollection.setScaleX(1.5);

                    button[0].setScaleX(1.2);
                    button[0].setImage(new Image(new FileInputStream
                            ("src\\assets\\cards\\button.png")));
                    shop.setScaleX(1);
                } catch (IOException ex) {
                    View.printThrowable(ex);
                }
            }
        });

        button[0].setOnMouseClicked(event -> {

            if (buttonSelect != 2) {
                buttonSelect = 2;
                buySell.setText("BUY");
                items.getChildren().clear();

                showCards();

                root.getChildren().remove(searchedCard);
                selectedName = null;
                selectedId = -1;
                searchedCard.getChildren().clear();
                VoicePlay.buttonPlay();

                try {
                    button[0].setImage(new Image(new FileInputStream("src\\assets\\cards\\onselect.png")));
                    button[0].setScaleX(1.5);
                    shop.setScaleX(1.5);

                    button[1].setScaleX(1.2);
                    button[1].setImage(new Image(new FileInputStream
                            ("src\\assets\\cards\\button.png")));
                    myCollection.setScaleX(1);
                } catch (IOException ex) {
                    View.printThrowable(ex);
                }
            }
        });

        shop.setOnMouseClicked(event -> {

            if (buttonSelect != 2) {
                buttonSelect = 2;
                buySell.setText("BUY");
                items.getChildren().clear();

                showCards();

                root.getChildren().remove(searchedCard);
                selectedName = null;
                selectedId = -1;
                searchedCard.getChildren().clear();
                VoicePlay.buttonPlay();

                try {
                    button[0].setImage(new Image(new FileInputStream("src\\assets\\cards\\onselect.png")));
                    button[0].setScaleX(1.5);
                    shop.setScaleX(1.5);

                    button[1].setScaleX(1.2);
                    button[1].setImage(new Image(new FileInputStream
                            ("src\\assets\\cards\\button.png")));
                    myCollection.setScaleX(1);
                } catch (IOException ex) {
                    View.printThrowable(ex);
                }
            }
        });

        searchCard.setOnMouseClicked(event -> {
            String name = search.getCharacters().toString();
            VoicePlay.buttonPlay();

            if (buttonSelect == 2) {
                Card message = ShopMenu.search(name);
                if (message == null) {
                    View.getInstance().popup("No card with this name");
                } else {
                    root.getChildren().remove(searchedCard);

                    selectedName = name;
                    selectedId = -1;
                    searchedCard = CardView.shopCardGroup(message);
                    searchedCard.relocate(1000, 100);

                    root.getChildren().addAll(searchedCard);

                }
            } else {
                Card message = ShopMenu.searchCollection(name);
                if (message == null) {
                    View.getInstance().popup("You don't have this card.");
                } else {
                    root.getChildren().remove(searchedCard);

                    selectedId = message.getCollectionID();
                    selectedName = null;
                    searchedCard = CardView.shopCardGroup(message);

                    searchedCard.relocate(1000, 100);
                    root.getChildren().addAll(searchedCard);

                }
            }
        });

        rightButtons[1].setOnMouseClicked(event -> {
            VoicePlay.buttonPlay();

            String name = search.getCharacters().toString();
            if (buttonSelect == 2) {
                Card message = ShopMenu.search(name);
                if (message == null) {
                    View.getInstance().popup("No card with this name");
                } else {
                    root.getChildren().remove(searchedCard);

                    selectedName = name;
                    selectedId = -1;
                    searchedCard = CardView.shopCardGroup(message);
                    searchedCard.relocate(1000, 100);

                    root.getChildren().addAll(searchedCard);

                }
            } else {
                Card message = ShopMenu.searchCollection(name);
                if (message == null) {
                    View.getInstance().popup("You don't have this card.");
                } else {
                    root.getChildren().remove(searchedCard);

                    selectedId = message.getCollectionID();
                    selectedName = null;
                    searchedCard = CardView.shopCardGroup(message);

                    searchedCard.relocate(1000, 100);
                    root.getChildren().addAll(searchedCard);

                }
            }
        });

        buySell.setOnMouseClicked(event -> {
            VoicePlay.buttonPlay();

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
                    showCards();

                }
            }
            root.getChildren().remove(searchedCard);
            selectedName = null;
            selectedId = -1;
        });

        rightButtons[0].setOnMouseClicked(event -> {
            VoicePlay.buttonPlay();

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
                    showCards();

                }
            }
            root.getChildren().remove(searchedCard);
            selectedName = null;
            selectedId = -1;
        });

        volume.setOnMouseClicked(event -> VolumeController.getInstance().run());
    }


    void setSearchedCard(Group searchedCard) {
        this.searchedCard = searchedCard;
    }

    public Group getRoot() {
        return root;
    }

    public void setRoot(Group root) {
        this.root = root;
    }

    Group getSearchedCard() {
        return searchedCard;
    }

    private void setScale(int cardType, boolean direction) throws IOException {
        if (direction) {
            types[cardType - 1].setImage(new Image(new FileInputStream
                    ("src\\assets\\cards\\button.png")));
            types[cardType - 1].setScaleX(1.2);
            switch (cardType) {
                case 1:
                    minion.setScaleX(1);
                    break;
                case 2:
                    hero.setScaleX(1);
                    break;
                case 3:
                    spell.setScaleX(1);
                    break;
                case 4:
                    item.setScaleX(1);
            }
        } else {
            types[cardType - 1].setImage(new Image(new FileInputStream
                    ("src\\assets\\cards\\onselect.png")));
            types[cardType - 1].setScaleX(1.5);
            switch (cardType) {
                case 1:
                    minion.setScaleX(1.5);
                    break;
                case 2:
                    hero.setScaleX(1.5);
                    break;
                case 3:
                    spell.setScaleX(1.5);
                    break;
                case 4:
                    item.setScaleX(1.5);
            }
        }
    }


}
