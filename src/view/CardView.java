package view;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import models.card.Attacker;
import models.card.Card;
import models.card.Hero;

import java.io.FileInputStream;
import java.io.IOException;


class CardView {
    static void showCard(Card card, TilePane items) {
        Group ret = shopCardGroup(card);
        if (ret != null) {
            items.getChildren().add(ret);
            ret.setOnMouseClicked(event -> {
                selectCard(card);
            });
        } else {
            System.out.print(card.getName());
        }
    }


    static Group shopCardGroup(Card card) {
        if (card instanceof Attacker) {
            Group ret = new Group();
            try {
                ImageView imageView = new ImageView(new Image(new FileInputStream
                        ("src\\assets\\cards\\shop_background.png")));

                ImageView image;
                if (card instanceof Hero) {
                    image = new ImageView(new Image(new FileInputStream
                            ("src\\assets\\cards\\hero\\" + card.getName() + ".png")));
                } else {
                    image = new ImageView(new Image(new FileInputStream
                            ("src\\assets\\cards\\minion\\" + card.getName() + ".png")));
                }
                image.relocate(0, -50);

                ImageView mp = new ImageView(new Image(new FileInputStream
                        ("src\\assets\\cards\\mana.png")));
                Label MP = new Label(Integer.toString(card.getPrice()));
                MP.setFont(Font.font(14));
                MP.relocate(7, 22);

                Label hp = new Label("");
                hp.relocate(167, 170);
                hp.setTextFill(Color.WHITE);
                hp.setFont(Font.font(14));
                hp.setText(Integer.toString(((Attacker) card).getHP()));


                Label ap = new Label(Integer.toString(((Attacker) card).getAP()));
                ap.relocate(55, 170);
                ap.setTextFill(Color.WHITE);
                ap.setFont(Font.font(14));

                Image t = new Image(new FileInputStream("src\\assets\\cards\\name.png"));
                ImageView nameImage = new ImageView(t);
                nameImage.setScaleX(0.45);
                nameImage.relocate(28, 125);

                Label name = new Label(card.getName() + "\n  " + ((card instanceof Hero) ? "Hero" : "Minion"));
                name.relocate(87, 135);
                name.setTextFill(Color.WHITE);
                name.setFont(Font.font(14));

                ret.getChildren().addAll(imageView, image, nameImage, mp, MP, hp, ap, name);
                return ret;
            } catch (IOException ex) {
                View.printThrowable(ex);
                return null;
            }
        }
        return null;
    }

    private static void selectCard(Card card) {
        ShopView shopView = ShopView.getInstance();
        if (shopView.getButtonSelect() == 1) {
            shopView.setSelectedId(card.getCollectionID());
            shopView.setSelectedName(null);
        } else {
            shopView.setSelectedId(-1);
            shopView.setSelectedName(card.getName());
        }
        Group ret = shopCardGroup(card);
        ret.relocate(950, 100);

        shopView.getRoot().getChildren().remove(shopView.getSearchedCard());

        try {
            shopView.getButton()[10].setImage(new Image(new FileInputStream
                    ("C:\\project-21\\src\\assets\\button.png")));
        } catch (IOException ex){
            View.printThrowable(ex);
        }

        shopView.setSearchedCard(ret);
        shopView.getRoot().getChildren().addAll(shopView.getSearchedCard());
    }

}
