package view;

import controller.menus.CollectionMenu;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import models.Collection;
import models.Deck;
import models.Player;
import models.card.Card;

import java.io.FileInputStream;
import java.io.IOException;

public class CollectionView {
    private static CollectionView instance = new CollectionView();

    public static CollectionView getInstance() {
        return instance;
    }

    private CollectionView() {
    }

    private Collection collection;
    private Group root = new Group();
    private Scene scene = new Scene(root, 1536, 801.59);
    private TilePane tilePane = new TilePane();
    private TilePane selectedCardTilePane = new TilePane();
    private ScrollPane scrollPane = new ScrollPane();
    private VBox decks = new VBox();
    private StackPane selectedDeck;
    private Card selectedCard;
    private ImageView back = new ImageView();
    private Button add = new Button();
    private Button remove = new Button();
    private Button deleteDeck = new Button();
    private Button createDeck = new Button();
    private Button setAsMain = new Button();
    private TextField deckName = new TextField();
    private Button importDeck = new Button();
    private Button exportDeck = new Button();
    private TextField search = new TextField();


    void run() {
        View.setScene(scene);
    }

    {
        collection = Player.getCurrentPlayer().getCollection();

        scene.getStylesheets().add("view/stylesheets/collection_view.css");

        setBackground();

        draw();

        setOnActions();
    }

    private void setBackground() {
        try {
            ImageView background = new ImageView(new Image(new FileInputStream("src/assets/resources/maps/redrock/background@2x.jpg")));
            background.fitWidthProperty().bind(scene.widthProperty());
            background.fitHeightProperty().bind(scene.heightProperty());
            root.getChildren().add(background);
        } catch (Exception e) {
            View.printThrowable(e);
        }
    }

    private void draw() {
        drawButtons();

        drawBack();

        drawDecks();

        drawScrollbar();

        drawSelectedCard();

        root.getChildren().addAll(scrollPane, back, decks);
    }

    private void drawButtons() {
        add.relocate(10, 400);
        add.setText("ADD CARD");
        remove.relocate(10, 480);
        remove.setText("REMOVE CARD");
        deleteDeck.relocate(10, 560);
        deleteDeck.setText("DELETE DECK");
        createDeck.relocate(10, 640);
        createDeck.setText("CREATE DECK");
        deckName.relocate(130, 640);
        deckName.setPromptText("DECK NAME");
        setAsMain.relocate(290, 640);
        setAsMain.setText("SET AS MAIN DECK");
        importDeck.relocate(400, 640);
        importDeck.setText("IMPORT DECK");
        exportDeck.relocate(500, 640);
        exportDeck.setText("EXPORT DECK");
        search.relocate(600, 640);
        search.setPromptText("SEARCH");
        root.getChildren().addAll(add, remove, deleteDeck, createDeck, deckName, setAsMain, importDeck, exportDeck, search);
    }

    private void drawBack() {
        try {
            back = new ImageView(new Image(new FileInputStream
                    ("src/assets/resources/ui/button_back_corner@2x.png")));
            back.setFitWidth(90);
            back.setFitHeight(95);
        } catch (IOException e) {
            View.printThrowable(e);
        }
    }

    private void drawScrollbar() {
        tilePane.getChildren().clear();

        tilePane.setHgap(20);
        tilePane.setVgap(30);
        tilePane.setPrefColumns(3);

        drawCards();

        scrollPane.setContent(tilePane);
        scrollPane.setPannable(true);
        scrollPane.relocate(100, 100);
        scrollPane.setMaxHeight(500);
        scrollPane.setMaxWidth(800);
        scrollPane.setStyle("-fx-background-color: transparent");
        scrollPane.setStyle("}.scroll-pane > .viewport {\n" +
                "   -fx-background-color: transparent;}\n" +
                "{");
        tilePane.setStyle("-fx-background-color: transparent");
    }

    private void drawCards() {
        tilePane.getChildren().clear();
        for (Card c : collection.getCards())
            CardView.showCard(c, tilePane, true, false);
    }

    private void drawCards(Deck deck) {
        tilePane.getChildren().clear();
        for (Card c : deck.getAllCards())
            CardView.showCard(c, tilePane, true, false);
    }

    private void drawDecks() {
        decks.getChildren().clear();
        decks.relocate(1250, 200);
        StackPane s = new StackPane();
        Rectangle r = new Rectangle(200, 50);
        r.setStyle("-fx-fill: rgba(0, 0, 0, 0.7)");
        Label label = new Label("ALL CARDS");
        label.setFont(Font.font(12));
        label.setStyle("-fx-text-fill: rgba(255, 255, 255, 0.85)");
        s.getChildren().addAll(r, label);
        s.setOnMouseClicked(event -> drawCards());
        decks.getChildren().add(s);
        for (Deck deck : collection.getDecks()) {
            StackPane stackPane = new StackPane();
            r = new Rectangle(200, 50);
            r.setStyle("-fx-fill: rgba(0, 0, 0, 0.3)");
            Label deckName = new Label(deck.getName());
            deckName.setFont(Font.font(20));
            deckName.setStyle("-fx-text-fill: rgba(255, 255, 255, 0.85)");
            stackPane.getChildren().addAll(r, deckName);
            stackPane.setOnMouseClicked(event -> {
                deselectDeck();
                if (event.getButton() == MouseButton.PRIMARY) {
                    select(stackPane);
                }
            });
            decks.getChildren().add(stackPane);
        }
    }

    private void select(StackPane s) {
        selectedDeck = s;
        s.getChildren().get(1).setStyle("-fx-text-fill: orange");
        drawCards(collection.getDeck(((Label) s.getChildren().get(1)).getText()));
    }

    private void deselectDeck() {
        if (selectedDeck != null) {
            selectedDeck.getChildren().get(1).setStyle("-fx-text-fill: rgba(255, 255, 255, 0.85)");
            selectedDeck = null;
        }
    }

    private void setOnActions() {
        back.setOnMouseClicked(event -> View.back());
        add.setOnMouseClicked(event -> {
            if (selectedDeck == null || selectedCard == null)
                return;
            CollectionMenu.getInstance().addCardToDeck(selectedCard.getCollectionID()
                    , ((Label) selectedDeck.getChildren().get(1)).getText());
        });
        remove.setOnMouseClicked(event -> {
            if (selectedCard == null || selectedDeck == null)
                return;
            CollectionMenu.getInstance().removeCardFromDeck(selectedCard.getCollectionID()
                    , ((Label) selectedDeck.getChildren().get(1)).getText());
        });
        deleteDeck.setOnMouseClicked(event -> {
            if (selectedDeck != null)
                CollectionMenu.getInstance().deleteDeck(((Label) selectedDeck.getChildren().get(1)).getText());
            drawDecks();
        });
        createDeck.setOnMouseClicked(event -> {
            CollectionMenu.getInstance().createDeck(deckName.getText());
            drawDecks();
        });
        setAsMain.setOnMouseClicked(event -> {
            if (selectedDeck != null) {
                CollectionMenu.getInstance().selectDeck(((Label) selectedDeck.getChildren().get(1)).getText());
            }
        });
        importDeck.setOnMouseClicked(event -> CollectionMenu.getInstance().importDeck(deckName.getText()));
        exportDeck.setOnMouseClicked(event -> {
            if (selectedDeck != null) {
                CollectionMenu.getInstance().exportDeck(((Label) selectedDeck.getChildren().get(1)).getText());
            }
        });
        search.setOnAction(event -> {
            for (Card c : collection.getCards())
                if (c.getName().equalsIgnoreCase(search.getText())) {
                    selectedCard = c;
                    return;
                }
        });
    }

    private void drawSelectedCard() {
        selectedCardTilePane.relocate(900, 300);
        root.getChildren().add(selectedCardTilePane);
        new AnimationTimer() {
            long last;

            @Override
            public void handle(long now) {
                if (now - last > 100) {
                    if (selectedCard != null) {
                        selectedCardTilePane.getChildren().clear();
                        CardView.showCard(selectedCard, selectedCardTilePane, true, false);
                    }
                    last = now;
                }
            }
        }.start();
    }

    void setSelectedCard(Card card) {
        selectedCard = card;
    }
}
