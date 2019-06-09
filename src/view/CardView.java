package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import models.card.Card;

import java.io.FileInputStream;
import java.io.IOException;


public class CardView {
    public static void showCard(Card card, TilePane items)  throws IOException {
        StackPane stackPane = new StackPane();
        ImageView background = new ImageView(new Image(new FileInputStream("src\\assets\\cards\\" + card.getName() +".png")));
        stackPane.getChildren().add(background);
        items.getChildren().add(stackPane);
    }

}
