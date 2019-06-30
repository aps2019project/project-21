//package view;
//
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.control.TextField;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.MouseButton;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.TilePane;
//import javafx.scene.layout.VBox;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.text.Font;
//import models.Collection;
//import models.Deck;
//import models.Player;
//import models.card.Card;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class CollectionView {
//    private static CollectionView instance = new CollectionView();
//
//    public static CollectionView getInstance() {
//        return instance;
//    }
//
//    private CollectionView() {
//    }
//
//    private Collection collection;
//    private Group root = new Group();
//    private Scene scene = new Scene(root, 1536, 801.59);
//    private VBox decks = new VBox();
//    private StackPane selectedDeck;
//    private ImageView back = new ImageView();
//    private ScrollPane scrollPane = new ScrollPane();
//    private TilePane allCards = new TilePane();
//
//    private Button deleteDeckButton = new Button("DELETE DECK");
//    private Button createDeckButton = new Button("CREATE DECK");
//    private Button removeFromDeckButton = new Button("REMOVE");
//    private Button addToDeckButton = new Button("ADD");
//    private ArrayList<Button> selectDeckButtons = new ArrayList<>();
//    private TilePane cardsTilePane = new TilePane();
//    private TextField inputTextField = new TextField();
//
//    void run() {
//        View.getInstance().setScene(scene);
//    }
//
//    {
//        collection = Player.getCurrentPlayer().getCollection();
//
//        scene.getStylesheets().add("view/stylesheets/collection_view.css");
//
//        draw();
//    }
//    private void draw() {
//        try {
//            back = new ImageView(new Image(new FileInputStream
//                    ("src/assets/resources/ui/button_back_corner@2x.png")));
//            back.setFitWidth(90);
//            back.setFitHeight(95);
//        } catch (IOException e) {
//            View.printThrowable(e);
//        }
//
//        showCards();
////        drawScrollPane();
////
////        drawAllCards();
//
//        root.getChildren().addAll(back, decks, scrollPane);
//    }
//
//    private void showCards() {
//        allCards.getChildren().clear();
//
//        allCards.setHgap(20);
//        allCards.setVgap(30);
//        allCards.setPrefColumns(3);
//
//        scrollPane.setContent(allCards);
//        scrollPane.setPannable(true);
//        scrollPane.relocate(220, 70);
//        scrollPane.setMaxHeight(500);
//        scrollPane.setMaxWidth(800);
//        scrollPane.setStyle("-fx-background-color: transparent");
//        scrollPane.setStyle("}.scroll-pane > .viewport {\n" +
//                "   -fx-background-color: transparent;}\n" +
//                "{");
//        allCards.setStyle("-fx-background-color: transparent");
//
//    }
//
//    private void setBackground() {
//        try {
//            ImageView background = new ImageView(new Image(new FileInputStream("src/assets/resources/maps/redrock/background@2x.jpg")));
//            background.fitWidthProperty().bind(scene.widthProperty());
//            background.fitHeightProperty().bind(scene.heightProperty());
//            root.getChildren().add(background);
//        } catch (Exception e) {
//            View.printThrowable(e);
//        }
//    }
//
//    {
//        int i = 0;
//        for (Deck deck : Player.getCurrentPlayer().getCollection().getDecks()) {
////            selectDeckButtons[i] = new Button("SELECT");
//            selectDeckButtons.add(new Button("SELECT"));
//            selectDeckButtons.get(i).setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
////                    selectedDeck = deck;
//                }
//            });
//            i++;
//        }
//    }
//
//
//    {
//        cardsTilePane.getChildren().clear();
//        cardsTilePane.setHgap(10);
//        cardsTilePane.setVgap(15);
//        cardsTilePane.setPrefColumns(6);
//        scrollPane.setContent(cardsTilePane);
//        scrollPane.setPannable(true);
//    }
//
//    private Group showDecks() {
//        Group root = new Group();
//        VBox vBox = new VBox();
//        //cardsTilePane.getChildren().clear();
//        for (Deck deck : Player.getCurrentPlayer().getCollection().getDecks()
//        ) {
//            TilePane bloodyTilePane = new TilePane();
//            for (Card card : deck.getAllCards()
//            ) {
//                CardView.showCard(card, bloodyTilePane, false);
//            }
//            vBox.getChildren().addAll(bloodyTilePane, new Label(deck.getName()), new Button("SELECT"));
//
//
//        }
//        root.getChildren().add(vBox);
//        return root;
//    }
//
//    private Group showCollectionCards() {
//        Group root = new Group();
//        for (Card card : Player.getCurrentPlayer().getCollection().getCards()
//        ) {
//
//        }
//        return root;
//    }
//
//    {
//        deleteDeckButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                //Collection.deleteDeck("ii");
////                Player.getCurrentPlayer().getCollection().deleteDeck(selectedDeck.getName());
//            }
//        });
//
//        createDeckButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                Player.getCurrentPlayer().createDeck(inputTextField.getText());
//            }
//        });
//
//        removeFromDeckButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                //Player.getCurrentPlayer().getCollection().getDecks();
//            }
//        });
//
//    }
//}
