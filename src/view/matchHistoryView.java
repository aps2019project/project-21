package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

class matchHistoryView {
    private static matchHistoryView instance = new matchHistoryView();

    private Group root = new Group();
    Scene scene = new Scene(root, 1536, 801.59);

    public static matchHistoryView getInstance() {
        return instance;
    }

    public static void setInstance(matchHistoryView instance) {
        matchHistoryView.instance = instance;
    }

    void run(){
        View.getInstance().setScene(scene);
    }

    {
        setBackground();

        run();
    }

    private void setBackground() {
        try {
            ImageView background = new ImageView(new Image(new FileInputStream("src\\assets\\mainmenu.jpg")));
            background.fitWidthProperty().bind(scene.widthProperty());
            background.fitHeightProperty().bind(scene.heightProperty());
            root.getChildren().add(background);
        } catch (Exception e) {
            View.printThrowable(e);
        }
    }

}
