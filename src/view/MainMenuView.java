package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainMenuView {

    private static MainMenuView instance = new MainMenuView();

    public static MainMenuView getInstance() {
        return instance;
    }

    private Group root = new Group();
    private Scene scene = new Scene(root, 1500, 1000);

    private VBox options = new VBox();
    private Button collectionButton = new Button("COLLECTION");
    private Button shopButton = new Button("SHOP");
    private Button battleButton = new Button("BATTLE");
    private Button backButton = new Button("BACK");
    private Button exitButton = new Button("EXIT");

    public void run() {
        View.getInstance().setScene(scene);

        options.relocate(100, 100);

        exitButton.setOnAction(event -> View.getInstance().exit());

        backButton.setOnAction(event -> View.getInstance().back());

        battleButton.setOnAction(event -> {

        });

        collectionButton.setOnAction(event -> {

        });

        shopButton.setOnAction(event -> {

        });

        options.getChildren().addAll(backButton, collectionButton, shopButton, battleButton, exitButton);
        options.setSpacing(10);

        hover();


        root.getChildren().add(options);
    }

    private void hover() {
    }
}
