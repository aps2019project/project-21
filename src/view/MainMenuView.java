package view;

import com.sun.org.apache.bcel.internal.generic.NEW;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuView {

    private static MainMenuView instance = new MainMenuView();

    public static MainMenuView getInstance() {
        return instance;
    }

    private Group root = new Group();
    private Scene scene = new Scene(root, 1500, 1000);




    private Button collectionButton = new Button("COLLECTION");
    private Button shopButton = new Button("SHOP");
    private Button battleButton = new Button("BATTLE");
    private Button backButton = new Button("BACK");
    private Button exitButton = new Button("EXIT");


    public void run(Stage primaryStage) {
        primaryStage.setScene(scene);





        exitButton.setOnAction(event -> primaryStage.close());

        backButton.setOnAction(event -> {
        });

        battleButton.setOnAction(event -> {
        });

        collectionButton.setOnAction(event -> {
        });

        shopButton.setOnAction(event -> {
        });


        root.getChildren().addAll();


    }
}
