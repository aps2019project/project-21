package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.card.Buff;
import models.card.buffs.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CustomCardView {
    private static CustomCardView instance = new CustomCardView();

    public static CustomCardView getInstance() {
        return instance;
    }

    public void run() {
        View.getInstance().setScene(scene);
    }

    private Group root = new Group();
    private Scene scene = new Scene(root, 1536, 801.59);
    private Label back = new Label("BACK");
    private Label ok = new Label("OK");
    private Label name = new Label("Name");
    private TextField Name = new TextField();
    private Label target = new Label("Target(only spell)");
    private TextField Target = new TextField();
    private Label type = new Label("TYPE");
    private TextField Type = new TextField();
    private Label ap = new Label("attack point");
    private TextField Ap = new TextField();
    private Label hp = new Label("health point");
    private TextField Hp = new TextField();
    private Label attack = new Label("Attack range");
    private TextField Attack = new TextField();
    private Label attackRange = new Label("Attack Range");
    private TextField AttackRange = new TextField();
    private TilePane buttons = new TilePane();
    private ScrollPane items = new ScrollPane();
    private Label bufs = new Label("BUFFS");
    private TilePane buffs = new TilePane();
    private ScrollPane Buffs = new ScrollPane();
    private ArrayList<Buff> bfs = new ArrayList<>();
    private Label cost = new Label("Cost");
    private TextField Cost = new TextField();

    private ImageView volume = new ImageView();

    {
        try {
            volume.setImage(new Image(new FileInputStream("src/assets/volume.png")));
            volume.setScaleY(0.1);
            volume.setScaleX(0.1);
        } catch (IOException ex) {
            View.printThrowable(ex);
        }
        volume.relocate(1000, 450);
    }

    {
        scene.getStylesheets().add("view/stylesheets/shop_view.css");

        setBackground();

        draw();

        setOnAction();

    }

    private void draw() {
        buttons.relocate(100, 100);
        buttons.setPrefColumns(2);
        buttons.setHgap(20);
        buttons.setVgap(20);
        name.setTextFill(Color.BLUE);
        type.setTextFill(Color.BLUE);
        target.setTextFill(Color.BLUE);
        ap.setTextFill(Color.BLUE);
        hp.setTextFill(Color.BLUE);
        attack.setTextFill(Color.BLUE);
        attackRange.setTextFill(Color.BLUE);
        cost.setTextFill(Color.BLUE);
        buttons.getChildren().addAll(back, ok, name, Name, type, Type, target, Target, ap, Ap, hp, Hp, attack
                , Attack, attackRange, AttackRange, cost, Cost);

        buffs.setVgap(20);
        buffs.setPrefColumns(1);
        buffs.relocate(500, 100);
        buffs.getChildren().addAll(bufs);

        Buffs.setContent(buffs);
        Buffs.relocate(700, 100);
        Buffs.setMaxHeight(500);

        bufs.setOnMouseClicked(event -> createBuff());

        items.setContent(buttons);
        items.relocate(100, 100);
        root.getChildren().addAll(items, Buffs);
    }

    private void createBuff() {
        VBox r = new VBox();
        r.setSpacing(10);
        final Scene sc = new Scene(r, 1000, 500);
        final Stage s = new Stage();
        s.setScene(sc);
        Button ok = new Button("OK");
        Label m = new Label("Name");
        TextField M = new TextField();
        Label b = new Label("Buff TYPE");
        TextField B = new TextField();
        Label e = new Label("Effect value");
        TextField E = new TextField();
        Label d = new Label("delay");
        TextField D = new TextField();
        Label l = new Label("last");
        TextField L = new TextField();
        Label f = new Label("friend or enemy");
        TextField F = new TextField();

        r.getChildren().addAll(ok, m, M, b, B, e, E, d, D, l, L, f, F);

        s.show();

        ok.setOnAction(event -> {
            int duration = 0;
            int value = 0;
            try {
                duration = Integer.parseInt(D.getCharacters().toString());
                value = Integer.parseInt(E.getCharacters().toString());
            } catch (Exception ex) {
                View.getInstance().popup("Invalid fields");
                s.close();
            }
//                int friend = -1;
//                if (F.getCharacters().toString().equals("friend")) {
//                    friend = 0;
//                } else if (F.getCharacters().toString().equals("enemy")) {
//                    friend = 1;
//                } else {
//                    View.getInstance().popup("Invalid fields");
//                    s.close();
//                }
            if (s.isShowing()) {
                Buff bu;
                String nam = M.getCharacters().toString();
                String name = B.getCharacters().toString();
                if (name.equals("disarm")) {
                    bu = new Disarm(duration);
                    bfs.add(bu);
                    buffs.getChildren().addAll(new Label(nam));
                } else if (name.equals("flame")) {
                    bu = new Flame(duration);
                    bfs.add(bu);
                    buffs.getChildren().addAll(new Label(nam));
                } else if (name.equals("holy")) {
                    bu = new Holy(duration);
                    bfs.add(bu);
                    buffs.getChildren().addAll(new Label(nam));
                } else if (name.equals("increase mana")) {
                    bu = new IncreaseMana(duration, value);
                    bfs.add(bu);
                    buffs.getChildren().addAll(new Label(nam));
                } else if (name.equals("poison")){
                    bu = new Poison(duration);
                    bfs.add(bu);
                    buffs.getChildren().addAll(new Label(nam));
                } else if (name.equals("power ap")){
                    bu = new PowerAP(duration,value);
                    bfs.add(bu);
                    buffs.getChildren().addAll(new Label(nam));
                } else if (name.equals("power hp")){
                    bu = new PowerHP(duration, value);
                    bfs.add(bu);
                    buffs.getChildren().addAll(new Label(nam));
                } else if (name.equals("stun")){
                    bu = new Stun(duration);
                    bfs.add(bu);
                    buffs.getChildren().addAll(new Label(nam));
                } else if (name.equals("weakness ap")){
                    bu = new WeaknessAP(duration,value);
                    bfs.add(bu);
                    buffs.getChildren().addAll(new Label(nam));
                } else if (name.equals("weakness hp")){
                    bu = new WeaknessHP(duration,value);
                    bfs.add(bu);
                    buffs.getChildren().addAll(new Label(nam));
                } else {
                    View.getInstance().popup("Invalid type");
                }
                s.close();
            }
        });
    }

    private void setBackground() {
        try {
            ImageView background = new ImageView(new Image(new FileInputStream("src\\assets\\cards" +
                    "\\shop.jpg")));
            background.fitWidthProperty().bind(scene.widthProperty());
            background.fitHeightProperty().bind(scene.heightProperty());
            root.getChildren().add(background);
        } catch (Exception e) {
            View.printThrowable(e);
        }
    }

    private void setOnAction() {
        back.setOnMouseClicked(event -> {
            View.getInstance().back();
        });


    }
}
