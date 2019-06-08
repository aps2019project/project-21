package view;

import javafx.scene.Group;
import javafx.scene.Scene;

public class CollectionView {
    private static CollectionView instance = new CollectionView();

    public static CollectionView getInstance() {
        return instance;
    }

    private CollectionView() {
    }

    private Group root = new Group();
    private Scene scene = new Scene(root, 1500, 100);

    void run() {
        View.getInstance().setScene(scene);
    }

    {


    }
}
