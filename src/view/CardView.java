package view;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import models.card.Attacker;
import models.card.Card;
import models.card.Hero;
import models.card.Spell;

import java.io.FileInputStream;
import java.io.IOException;


class CardView {
    static void showCard(Card card, TilePane items, boolean shop) {
        Group ret;
        if (shop) {
            ret = shopCardGroup(card);
        } else {
            ret = collectionCardGroup(card);
        }
        if (ret != null) {
            items.getChildren().add(ret);
            ret.setOnMouseClicked(event -> {
                if (shop) {
                    selectCard(card);
                    VoicePlay.buttonPlay();
                    CollectionView.getInstance().setSelectedCard(card);
                } else {
                }
            });
        } else {
            System.out.print(card.getName());
        }
    }

    private static Group collectionCardGroup(Card card) {
        if (card instanceof Attacker) {
            Group ret = new Group();
            try {
                ImageView imageView = new ImageView(new Image(new FileInputStream
                        ("src\\assets\\cards\\shop_background.png")));
                ImageView image = new ImageView();
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
                Label MP = new Label(Integer.toString(card.getManaCost()));
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

                Label name = new Label(card.getName() + "\n" + ((card instanceof Hero) ? "Hero" : "Minion"));
                name.setTextAlignment(TextAlignment.CENTER);
                name.relocate(87, 135);
                name.setTextFill(Color.WHITE);
                name.setFont(Font.font(14));

                Label desc = new Label(smallDesc(card.getDesc()));
                desc.setFont(Font.font(15));
                desc.relocate(20, 210);
                desc.setTextFill(Color.WHITE);
                desc.setTextAlignment(TextAlignment.CENTER);

                ret.getChildren().addAll(imageView, desc, image, nameImage, mp, MP, hp, ap, name);
                return ret;
            } catch (IOException ex) {
                View.printThrowable(ex);
                return null;
            }
        } else {
            Group ret = new Group();
            try {
                ImageView imageView = new ImageView(new Image(new FileInputStream
                        ("src\\assets\\cards\\spell_background.png")));


                ImageView mp = new ImageView(new Image(new FileInputStream
                        ("src\\assets\\cards\\mana.png")));
                Label MP = new Label(Integer.toString(card.getManaCost()));
                MP.setFont(Font.font(14));
                MP.relocate(7, 22);

                Label name = new Label(card.getName() + "\n" + ((card instanceof Spell) ? "Spell" : "Usable"));
                name.setTextAlignment(TextAlignment.CENTER);
                name.relocate(80, 100);
                name.setTextFill(Color.WHITE);
                name.setFont(Font.font(14));

                Image t = new Image(new FileInputStream("src\\assets\\cards\\name.png"));
                ImageView nameImage = new ImageView(t);
                nameImage.relocate(20, 90);

                Label desc = new Label(smallDesc(card.getDesc()));
                desc.setFont(Font.font(15));
                desc.relocate(20, 200);
                desc.setTextFill(Color.WHITE);
                desc.setTextAlignment(TextAlignment.CENTER);

                ret.getChildren().addAll(imageView, desc, nameImage, mp, MP, name);
                return ret;
            } catch (IOException ex) {
                View.printThrowable(ex);
                return null;
            }
        }
    }


    static Group shopCardGroup(Card card) {
        if (card instanceof Attacker) {
            Group ret = new Group();
            try {
                ImageView imageView = new ImageView(new Image(new FileInputStream
                        ("src\\assets\\cards\\shop_background.png")));

                ImageView image = new ImageView();
                if (card instanceof Hero) {
                    try {
                        image = new ImageView(new Image(new FileInputStream
                                ("src\\assets\\cards\\hero\\" + card.getName() + ".png")));
                    } catch (IOException ex) {

                    }
                } else {
                    try {
                        image = new ImageView(new Image(new FileInputStream
                                ("src\\assets\\cards\\minion\\" + card.getName() + ".png")));
                    } catch (IOException ex) {

                    }
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

                Label name = new Label(card.getName() + "\n" + ((card instanceof Hero) ? "Hero" : "Minion"));
                name.setTextAlignment(TextAlignment.CENTER);
                name.relocate(87, 135);
                name.setTextFill(Color.WHITE);
                name.setFont(Font.font(14));

                Label desc = new Label(smallDesc(card.getDesc()));
                desc.setFont(Font.font(15));
                desc.relocate(20, 210);
                desc.setTextFill(Color.WHITE);
                desc.setTextAlignment(TextAlignment.CENTER);

                ret.getChildren().addAll(imageView, desc, image, nameImage, mp, MP, hp, ap, name);
                return ret;
            } catch (IOException ex) {
                View.printThrowable(ex);
                return null;
            }
        } else {
            Group ret = new Group();
            try {
                ImageView imageView = new ImageView(new Image(new FileInputStream
                        ("src\\assets\\cards\\spell_background.png")));


                ImageView mp = new ImageView(new Image(new FileInputStream
                        ("src\\assets\\cards\\mana.png")));
                Label MP = new Label(Integer.toString(card.getPrice()));
                MP.setFont(Font.font(14));
                MP.relocate(7, 22);

                Label name = new Label(card.getName() + "\n" + ((card instanceof Spell) ? "Spell" : "Usable"));
                name.setTextAlignment(TextAlignment.CENTER);
                name.relocate(80, 100);
                name.setTextFill(Color.WHITE);
                name.setFont(Font.font(14));

                Image t = new Image(new FileInputStream("src\\assets\\cards\\name.png"));
                ImageView nameImage = new ImageView(t);
                nameImage.relocate(20, 90);

                Label desc = new Label(smallDesc(card.getDesc()));
                desc.setFont(Font.font(15));
                desc.relocate(20, 200);
                desc.setTextFill(Color.WHITE);
                desc.setTextAlignment(TextAlignment.CENTER);

                ret.getChildren().addAll(imageView, desc, nameImage, mp, MP, name);
                return ret;
            } catch (IOException ex) {
                View.printThrowable(ex);
                return null;
            }
        }

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
        ret.relocate(1000, 100);

        shopView.getRoot().getChildren().remove(shopView.getSearchedCard());


        shopView.setSearchedCard(ret);
        shopView.getRoot().getChildren().addAll(shopView.getSearchedCard());
    }

    static private String smallDesc(String desc) {
        String[] space = desc.split(" ");
        StringBuilder ret = new StringBuilder();
        int thisLine = 0;
        int num = 0;
        while (num != space.length) {
            if (space[num].length() + thisLine < 30) {
                ret.append(" ");
                ret.append(space[num]);
                thisLine += (1 + space[num].length());
            } else {
                ret.append("\n");
                ret.append(space[num]);
                thisLine = space[num].length();
            }
            num++;
        }
        return ret.toString();
    }

}
