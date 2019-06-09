package view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import models.card.Card;


public class CardView {
    public static void showCard(Card card, TilePane items) {
        StackPane stackPane = new StackPane();
        ImageView background = card.getCardBackground();
        stackPane.getChildren().add(background);
        items.getChildren().add(stackPane);
    }

}
