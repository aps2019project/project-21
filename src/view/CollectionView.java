package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import models.Collection;
import models.Deck;
import models.Player;
import models.card.Card;

public class CollectionView extends Application {
    private static CollectionView instance = new CollectionView();

    public static CollectionView getInstance() {
        return instance;
    }

    private CollectionView() {
    }




    private Group root = new Group();
    private Group deckGroup = new Group();
    private Scene scene = new Scene(root, 1500, 100);

    private Scene decksScene = new Scene(showDecks());

    private Button exitButton = new Button("EXIT");
    private Button deleteDeckButton = new Button("DELETE DECK");
    private Button helpButton = new Button("HELP");
    private Button selectDeckButton = new Button("SELECT DECK");
    private Button createDeckButton = new Button("CREATE DECK");
    private Button removeFromDeckButton = new Button("REMOVE FROM DECK");
    private Button addToDeckButton = new Button("ADD TO DECK");
    private Button showAllDecksButton = new Button("SHOW ALL DECKS");
    private TilePane cardsTilePane = new TilePane();
    private ScrollPane scrollPane = new ScrollPane();
    private TextField inputTextField = new TextField();



    {
        cardsTilePane.getChildren().clear();
        cardsTilePane.setHgap(10);
        cardsTilePane.setVgap(15);
        cardsTilePane.setPrefColumns(6);
        scrollPane.setContent(cardsTilePane);
        scrollPane.setPannable(true);
    }





    private Group showDecks(TilePane bloodyTilePane){
        Group root = new Group();
        cardsTilePane.getChildren().clear();
        for (Deck deck : Player.getCurrentPlayer().getCollection().getDecks()
             ) {
            for (Card card:deck.getAllCards()
                 ) {
                CardView.showCard(card, bloodyTilePane, false);
            }
        }
        return root;
    }

    //deleteDeckButton.set(new EventHandler<MouseEvent>( ) {
//        @Override
//        public void handle(MouseEvent event) {
//            clickedPlayer.play ();
//            clickedPlayer.seek(Duration.ZERO);
//            collectionMenuRoot.getChildren ().clear();
//            collectionMenuRoot.getChildren().add(backgroundImageView);
//            backgroundImageView.setEffect(new GaussianBlur());
//            TextField textField = new TextField();
//            textField.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent actionEvent) {
//                    createOrRemoveDeck("remove", textField.getText());
//                }
//            });
//        }
    //});


    void run() {
        View.getInstance().setScene(scene);
    }





    @Override
    public void start(Stage primaryStage) throws Exception {


        deleteDeckButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Collection.deleteDeck("ii");
                Player.getCurrentPlayer().getCollection().deleteDeck("h");
            }
        });



        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });



        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });


        createDeckButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Player.getCurrentPlayer().createDeck(inputTextField.getText());
            }
        });

        selectDeckButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Player.getCurrentPlayer().setMainDeck(thatBloodyDeck);
            }
        });


        removeFromDeckButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Player.getCurrentPlayer().getCollection().getDecks();
            }
        });

//        try {
//            com.sun.net.httpserver.HttpServer server = com.sun.jersey.api.container.httpserver.HttpServerFactory.create("http://localhost:9998/");
//            server.start();
//
//            System.out.println("Server running");
//            System.out.println("Visit: http://localhost:9998/h");
//            System.out.println("Hit return to stop...");
//            System.in.read();
//            System.out.println("Stopping server");
//            server.stop(0);
//            System.out.println("Server stopped");
//        } catch (java.io.IOException ioe) {
//            ioe.printStackTrace(System.err);
//        }






        root.getChildren().addAll(exitButton, deleteDeckButton, helpButton, selectDeckButton, createDeckButton, removeFromDeckButton, addToDeckButton, showAllDecksButton);

    }

}
