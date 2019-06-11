package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import models.card.Card;
import models.card.Hero;
import models.card.Minion;

import java.io.FileInputStream;
import java.io.IOException;


class CardView {
    static void showCard(Card card, TilePane items) {
        if (card.getImagePath() == null){
            if (card instanceof Hero) {
                card.setImagePath("src\\assets\\cards\\Jasose_Torani.jpg");
            } else if (card instanceof Minion){
                card.setImagePath("src\\assets\\cards\\Rakhsh.jpg");
            } else {
                card.setImagePath("src\\assets\\cards\\fuckingimage.png");
            }

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
