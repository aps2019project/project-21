package view;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import models.card.Card;
import models.card.Hero;
import models.card.Minion;
import models.card.Spell;

import java.io.FileInputStream;
import java.io.IOException;


class CardView {
    static void showCard(Card card, TilePane items) {
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(shopCardGroup(card));
        items.getChildren().add(stackPane);
    }


    static Group shopCardGroup(Card card) {
        Group ret = new Group();
        try {
            ImageView imageView = new ImageView(new Image(new FileInputStream
                    ("C:\\project-21\\src\\assets\\cards\\shop_background.png")));

            ImageView image;
            if (card instanceof Hero) {
                image = new ImageView(new Image(new FileInputStream
                        ("C:\\project-21\\src\\assets\\cards\\hero\\" + card.getName() + ".png")));
            } else if (card instanceof Minion) {
                image = new ImageView(new Image(new FileInputStream
                        ("C:\\project-21\\src\\assets\\cards\\minion\\" + card.getName() + ".png")));
            } else if (card instanceof Spell) {
                image = new ImageView(new Image(new FileInputStream
                        ("C:\\project-21\\src\\assets\\cards\\spell\\" + card.getName() + ".png")));
            } else {
                image = new ImageView(new Image(new FileInputStream
                        ("C:\\project-21\\src\\assets\\cards\\usable\\" + card.getName() + ".png")));
            }
            image.relocate(0, -50);

            ImageView mp = new ImageView(new Image(new FileInputStream
                    ("C:\\project-21\\src\\assets\\cards\\mana.png")));
            Label MP = new Label(Integer.toString(card.getPrice()));
            MP.setFont(Font.font(14));
            MP.relocate(7, 22);

            ret.getChildren().addAll(imageView, image, mp, MP);
            return ret;
        } catch (IOException ex) {
            return null;
        }
    }

}
