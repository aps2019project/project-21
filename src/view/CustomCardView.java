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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import json.CardMaker;
import models.card.*;
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
        View.setScene(scene);
    }

    private boolean tr = true;
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
    private Label attack = new Label("Attack mode");
    private TextField Attack = new TextField();
    private Label attackRange = new Label("Attack Range");
    private TextField AttackRange = new TextField();
    private TilePane buttons = new TilePane();
    private ScrollPane items = new ScrollPane();
    private Label bufs = new Label("BUFFS(for spell)");
    private TilePane buffs = new TilePane();
    private ScrollPane Buffs = new ScrollPane();
    private ArrayList<Buff> bfs = new ArrayList<>();
    private Label cost = new Label("Cost");
    private TextField Cost = new TextField();
    private Label specialCooldoqn = new Label("Spc cooldown");
    private TextField spcCdown = new TextField();
    private Label spcActive = new Label("spc activation");
    private TextField SpcActive = new TextField();
    private Label spcPower = new Label("Special power");
    private Label SpcPower = new Label();
    private static ArrayList<Spell> spc = new ArrayList<>();

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

    private CustomCardView() {
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
        spcActive.setTextFill(Color.BLUE);
        specialCooldoqn.setTextFill(Color.BLUE);
        spcPower.setTextFill(Color.BLUE);
        buttons.getChildren().addAll(back, ok, name, Name, type, Type, target, Target, ap, Ap, hp, Hp, attack
                , Attack, attackRange, AttackRange, cost, Cost, specialCooldoqn, spcCdown, spcActive, SpcActive
                , spcPower, SpcPower);

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
                View.popup("Invalid fields");
                s.close();
            }
//                int friend = -1;
//                if (F.getCharacters().toString().equals("friend")) {
//                    friend = 0;
//                } else if (F.getCharacters().toString().equals("enemy")) {
//                    friend = 1;
//                } else {
//                    View.popup("Invalid fields");
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
                } else if (name.equals("poison")) {
                    bu = new Poison(duration);
                    bfs.add(bu);
                    buffs.getChildren().addAll(new Label(nam));
                } else if (name.equals("power ap")) {
                    bu = new PowerAP(duration, value);
                    bfs.add(bu);
                    buffs.getChildren().addAll(new Label(nam));
                } else if (name.equals("power hp")) {
                    bu = new PowerHP(duration, value);
                    bfs.add(bu);
                    buffs.getChildren().addAll(new Label(nam));
                } else if (name.equals("stun")) {
                    bu = new Stun(duration);
                    bfs.add(bu);
                    buffs.getChildren().addAll(new Label(nam));
                } else if (name.equals("weakness ap")) {
                    bu = new WeaknessAP(duration, value);
                    bfs.add(bu);
                    buffs.getChildren().addAll(new Label(nam));
                } else if (name.equals("weakness hp")) {
                    bu = new WeaknessHP(duration, value);
                    bfs.add(bu);
                    buffs.getChildren().addAll(new Label(nam));
                } else {
                    View.popup("Invalid type");
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
            View.back();
        });

        ok.setOnMouseClicked(event -> {
            Card c = create(spc.size() > 0 ? spc.get(0) : null);
            if (tr) {
                if (c != null) {
                    CardMaker.saveToFile(c);
//                if (c instanceof Hero) {
//                    Player.getCurrentPlayer().getCollection().getHeroes().add((Hero) c);
//                    Player.getCurrentPlayer().getCollection().getCards().add(c);
//                } else if (c instanceof Spell) {
//                    Player.getCurrentPlayer().getCollection().getSpells().add((Spell) c);
//                    Player.getCurrentPlayer().getCollection().getCards().add(c);
//                } else if (c instanceof Minion) {
//                    Player.getCurrentPlayer().getCollection().getMinions().add((Minion) c);
//                    Player.getCurrentPlayer().getCollection().getCards().add(c);
//                }
                } else {
                    View.popup("An error occurred");
                    View.back();
                }
            } else {
                spc.add((Spell) c);
            }
        });

        spcPower.setOnMouseClicked(event -> {
            CustomCardView c = new CustomCardView();
            c.tr = false;
            c.run();
            Label spel = new Label("Special power spell");
            spel.relocate(100, 50);
            spel.setFont(Font.font(18));
            spel.setTextFill(Color.RED);
            c.root.getChildren().addAll(spel);
        });

    }

    private Card create(Spell spc) {
        if (Name.getCharacters().toString().length() == 0) {
            View.popup("Name is null");
            return null;
        } else {
            String typ = Type.getCharacters().toString();
            if (typ.equals("minion")) {
                return minion(spc);
            } else if (typ.equals("hero")) {
                return hero(spc);
            } else if (typ.equals("spell")) {
                return spell();
            } else {
                return null;
            }
        }
    }

    private Minion minion(Spell spc) {
        try {
            String name = Name.getCharacters().toString();
            int ap = Integer.parseInt(Ap.getCharacters().toString());
            int hp = Integer.parseInt(Hp.getCharacters().toString());
            int attackRange = Integer.parseInt(AttackRange.getCharacters().toString());
            String t = Attack.getCharacters().toString();
            AttackMode attackMode;
            if (t.equals("hybrid")) {
                attackMode = AttackMode.HYBRID;
            } else if (t.equals("melee")) {
                attackMode = AttackMode.MELEE;
            } else if (t.equals("ranged")) {
                attackMode = AttackMode.RANGED;
            } else {
                return null;
            }
            int mana = Integer.parseInt(Cost.getCharacters().toString());
            int price = 10000;
            int spcActive = Integer.parseInt(SpcActive.getCharacters().toString());
            return new Minion(name, price, mana, hp, ap, attackRange, attackMode, spc);
        } catch (Exception ex) {
            View.printThrowable(ex);
            return null;
        }
    }

    private Spell spell() {
        try {
            ApplyType applyType;
            int mana;
            String name = Name.getCharacters().toString();
            String targt = Target.getCharacters().toString();
            if (targt.equals("on cell")) {
                applyType = ApplyType.ON_CELL;
            } else if (targt.equals("on attacker")) {
                applyType = ApplyType.ON_ATTACKER;
            } else if (targt.equals("on player info")) {
                applyType = ApplyType.ON_PLAYER_INFO;
            } else {
                applyType = ApplyType.NONE;
            }
            mana = Integer.parseInt(Cost.getCharacters().toString());
            return new Spell(name, 10000, mana, new TargetType(), new ArrayList<Effect>(bfs), applyType, "");
        } catch (Exception ex) {
            View.printThrowable(ex);
            return null;
        }
    }

    private Hero hero(Spell spc) {
        try {
            String name = Name.getCharacters().toString();
            int ap = Integer.parseInt(Ap.getCharacters().toString());
            int hp = Integer.parseInt(Hp.getCharacters().toString());
            int attackRange = Integer.parseInt(AttackRange.getCharacters().toString());
            String t = Attack.getCharacters().toString();
            AttackMode attackMode;
            if (t.equals("hybrid")) {
                attackMode = AttackMode.HYBRID;
            } else if (t.equals("melee")) {
                attackMode = AttackMode.MELEE;
            } else if (t.equals("ranged")) {
                attackMode = AttackMode.RANGED;
            } else {
                return null;
            }
            int price = 10000;
            int spcCooldown = Integer.parseInt(spcCdown.getCharacters().toString());
            return new Hero(name,price,hp,ap,attackRange,attackMode,spc,spcCooldown);
        } catch (Exception ex) {
            View.printThrowable(ex);
            return null;
        }
    }
}
