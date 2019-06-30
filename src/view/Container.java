package view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.card.Attacker;
import models.card.Card;

import java.io.FileInputStream;

public class Container {
    private static ImageView death;
    private Rectangle rect;
    private Card card;
    private ImageView gif;
    private ImageView idle;
    private ImageView run;
    private ImageView attack;
    private Label name = new Label();
    private Label hp = new Label();
    private Label ap = new Label();
    private Label mp = new Label();
    private Label cardIDInGame = new Label();
    private Group hover = new Group();
    private Group group = new Group();

    {
        name.relocate(30, 15);
        name.setTextFill(Color.RED);
        hp.relocate(60, 100);
        hp.setTextFill(Color.RED);
        ap.relocate(20, 100);
        ap.setTextFill(Color.RED);
        mp.relocate(40, 110);
        mp.setTextFill(Color.BLUE);
        cardIDInGame.relocate(20, 0);
        cardIDInGame.setTextFill(Color.RED);
        hover.getChildren().addAll(name, hp, ap, mp, cardIDInGame);
        group.setOnMouseEntered(event -> {
            if (!group.getChildren().contains(hover))
                group.getChildren().add(hover);
        });
        group.setOnMouseExited(event -> group.getChildren().remove(hover));
    }

    void setImages() {
        try {
            String type = card.getClass().getSimpleName();
            System.out.println(type);
            String n = Integer.toString(Math.abs(card.getName().hashCode()) % 5 + 1);

            if (card instanceof Attacker) {
                setIdle(new ImageView(new Image(new FileInputStream("src/assets/gifs/" + type + "_idle_" + n + ".gif"))));
                setRun(new ImageView(new Image(new FileInputStream("src/assets/gifs/" + type + "_run_" + n + ".gif"))));
                setAttack(new ImageView(new Image(new FileInputStream("src/assets/gifs/" + type + "_attack_" + n + ".gif"))));
            } else {
                setIdle(new ImageView(new Image(new FileInputStream("src/assets/gifs/" + type + "_idle_" + n + ".gif"))));
                setAttack(new ImageView(new Image(new FileInputStream("src/assets/gifs/" + type + "_active_" + n + ".gif"))));
            }
            idle();
        } catch (Exception e) {
            System.err.println(card.getName());
            View.printThrowable(e);
        }
    }

    private void add(Node node) {
        if (node instanceof ImageView) {
            group.getChildren().remove(gif);
            gif = (ImageView) node;
            gif.setScaleX(1.5);
            gif.setScaleY(gif.getScaleX());
            group.getChildren().add(gif);
        } else
            group.getChildren().add(node);
    }

    private void idle() {
        group.getChildren().remove(gif);
        gif = idle;
        gif.setScaleX(1.5);
        gif.setScaleY(gif.getScaleX());
        group.getChildren().add(gif);
    }

    Rectangle getRect() {
        return rect;
    }

    void setRect(Rectangle rect) {
        group.getChildren().remove(this.rect);
        this.rect = rect;
        add(rect);
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
        name.setText(card.getName());
        if (card instanceof Attacker) {
            Attacker a = (Attacker) card;
            hp.setText(Integer.toString(a.getHP()));
            ap.setText(Integer.toString(a.getAP()));
        }
        mp.setText(Integer.toString(card.getManaCost()));
        cardIDInGame.setText(card.getCardIDInGame());
    }

    public ImageView getIdle() {
        return idle;
    }

    private void setIdle(ImageView idle) {
        group.getChildren().remove(this.idle);
        this.idle = idle;
        add(idle);
    }

    public ImageView getRun() {
        return run;
    }


    public void setRun(ImageView run) {
        group.getChildren().remove(this.run);
        this.run = run;
        add(run);
    }

    void setAsRun() {
        group.getChildren().removeAll(run, idle, gif, death, attack);
        group.getChildren().addAll(run);
    }

    void setAsIdle() {
        group.getChildren().removeAll(run, idle, gif, death, attack);
        group.getChildren().addAll(idle);
    }

    void setAsAttack() {
        group.getChildren().removeAll(run, idle, gif, death, attack);
        group.getChildren().addAll(attack);
    }

    void reverseRun() {
        run.setScaleX(-1);
    }

    void attackReverse() {
        attack.setScaleX(-1);
    }

    void setAsGif() {
        group.getChildren().removeAll(run, idle, gif, death, attack);
        group.getChildren().addAll(gif);
    }

    void setAsDeath() {
        group.getChildren().removeAll(run, idle, gif, death, attack);
        group.getChildren().addAll(death);
    }

    public ImageView getAttack() {
        return attack;
    }

    public void setAttack(ImageView attack) {
        group.getChildren().remove(this.attack);
        this.attack = attack;
        add(attack);
    }

    public ImageView getDeath() {
        return death;
    }

    public void setDeath(ImageView death) {
        group.getChildren().remove(this.death);
        this.death = death;
        add(death);
    }

    public Label getName() {
        return name;
    }

    public Label getHp() {
        return hp;
    }

    public Label getAp() {
        return ap;
    }

    public Label getMp() {
        return mp;
    }

    public Label getCardIDInGame() {
        return cardIDInGame;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
