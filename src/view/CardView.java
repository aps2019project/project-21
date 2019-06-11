package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import models.card.Card;

import java.io.FileInputStream;
import java.io.IOException;


class CardView {
    static void showCard(Card card, TilePane items) {
        if (card.getImagePath() == null){
            card.setImagePath("src\\assets\\cards\\Jasose_Torani.png");
            //TODO after lying images in cards folder
        }

        StackPane stackPane = new StackPane();
        try {
            ImageView background = new ImageView(new Image(new FileInputStream(card.getImagePath())));
            stackPane.getChildren().add(background);
            items.getChildren().add(stackPane);
        } catch (IOException ex){
            View.printThrowable(ex);
        }

    }

}
