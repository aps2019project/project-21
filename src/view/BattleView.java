package view;

import controller.menus.BattleMenu;
import controller.menus.MultiPlayerBattleMenu;
import controller.menus.SinglePlayerBattleMenu;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import models.BattleAction;
import models.Item.Collectable;
import models.Item.Flag;
import models.Player;
import models.card.*;
import models.match.GameMode;
import models.match.Match;
import models.match.PlayerMatchInfo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

public class BattleView {
    private static final int TILE_SIZE = 70;
    private static final int VALLEY_SIZE = 4;

    private Label hp1 = new Label();
    private Label hp2 = new Label();
    private Match match = Match.getCurrentMatch();
    private Group root = new Group();
    private Scene scene = new Scene(root, 1536, 801.59);
    private Group table = new Group();
    private Rectangle[][] cells = new Rectangle[5][9];
    private Map<Attacker, Container> attackers = new HashMap<>();
    private Group groundedAttackers = new Group();
    private StackPane endTurn = new StackPane();
    private Group hub = new Group();
    private Rectangle selectedRect;
    private Cell select = new Cell(-1, -1);
    private Container selectedInHand;
    private HBox hand = new HBox();
    private HBox manaBar = new HBox();
    private StackPane graveyard = new StackPane();
    private Label cooldown = new Label();
    private Group collectables = new Group();
    private Group flags = new Group();
    private ArrayBlockingQueue<BattleAction> battleActions = new ArrayBlockingQueue<>(1000);
    private Rectangle invisible = new Rectangle(scene.getWidth(), scene.getHeight());
    private ImageView endTurnImage;
    private ImageView endTurnGlow;
    private BattleMenu battleMenu;

    public void run() {
        View.setScene(scene);

        scene.getStylesheets().add("view/stylesheets/battle_view.css");

        initBattleMenu();

        setBackground();

        drawTable();

        handleSelection();

        handleBattleActions();

        drawHub();

        setOnActions();

        drawInvisible();

        root.getChildren().addAll(hub, table);

        turnChange();
    }

    private void initBattleMenu() {
        if (match.getGameMode() == GameMode.SINGLE_PLAYER)
            battleMenu = new SinglePlayerBattleMenu();
        else
            battleMenu = new MultiPlayerBattleMenu();
    }

    private void drawTable() {
        table.getChildren().addAll(collectables, flags);
        table.relocate(450, 240);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                Rectangle rect = new Rectangle(TILE_SIZE, TILE_SIZE);
                rect.relocate(j * (TILE_SIZE + VALLEY_SIZE), i * (TILE_SIZE + VALLEY_SIZE));
                rect.getStyleClass().add("rectangle");
                cells[i][j] = rect;
                table.getChildren().add(rect);
            }
        }
        drawAttackers();
        drawCollectables();
        drawFlags();
        table.getChildren().add(groundedAttackers);
    }

    private void handleSelection() {
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 9; j++) {
                Rectangle rect = cells[i][j];
                int u = i;
                int v = j;
                rect.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        if (match.isAnyCardSelected()) {
//                            int r =
                            battleMenu.moveOrAttack(u, v);
//                            if (r == 0) {
//
//                            } else if (r == 1) {
//
//                            } else if (r == 2) {
//
//                            } else {
//
//                            }
                        } else if (selectedInHand != null) {
                            if (selectedInHand.getCard() instanceof Attacker) {
                                battleMenu.insertCardIn(selectedInHand.getCard().getName(), u, v);
                                drawAttackers();
                            } else if (selectedInHand.getCard() instanceof Spell) {
                                if (selectedInHand.getCard().getName().equalsIgnoreCase(match.getPlayersMatchInfo()[0].getHero().getSpecialPower().getName())) {
                                    battleMenu.useSpecialPower(u, v);
                                } else
                                    battleMenu.useSpell(selectedInHand.getCard().getName(), u, v);
                            }
                            deselect();
                        } else {
                            if (getAttacker(u, v) != null)
                                battleMenu.selectAttacker(getAttacker(u, v).getCardIDInGame(), u, v);
                        }
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        deselect();
                    }
                });
            }
    }

    private void drawInvisible() {
        invisible.setStyle("-fx-fill: rgba(255,255,255,0.15)");
    }

    public void selectAttackerBoolean(int u, int v) {
        if (selectedRect != null)
            selectedRect.setStyle("-fx-fill: rgba(0, 0, 0, 0.1)");
        cells[u][v].setStyle("-fx-fill: rgba(255, 255, 0, 0.2)");
        selectedRect = cells[u][v];
        select.setX(u);
        select.setY(v);
    }

    public void moveOrAttack0(int u, int v) {
        moveAnimation(u, v);
        deselect();
    }

    public void moveOrAttack1(int u, int v) {
        deselect();
    }

    public void moveOrAttack2(int u, int v) {
        attackAnimation(u, v);
        Attacker attacker = getAttacker(u, v);
        if (attacker.getAttackMode() == AttackMode.MELEE &&
                (u - select.getX()) < 2 && (u - select.getX()) > -2 &&
                (v - select.getY()) < 2 && (v - select.getY()) > -2) {
            int o = select.getX(), t = select.getY();
            select = new Cell(u, v);
            attackAnimation(o, t);
        } else if (attacker.getAttackMode() == AttackMode.RANGED &&
                ((u - select.getX()) > 1 || (u - select.getX()) < -1 ||
                        (v - select.getY()) > 1 || (v - select.getY()) < -1)) {
            int o = select.getX(), t = select.getY();
            select = new Cell(u, v);
            attackAnimation(o, t);
        } else if (attacker.getAttackMode() == AttackMode.HYBRID) {
            int o = select.getX(), t = select.getY();
            select = new Cell(u, v);
            attackAnimation(o, t);
        }
        updateAttackers();
        deselect();
    }

    public void moveOrAttack3(int u, int v) {
        deselect();
    }

    public void updateAttackers() {
        hp1.setText(Integer.toString(match.getPlayersMatchInfo()[0].getHero().getHP()));
        hp2.setText(Integer.toString(match.getPlayersMatchInfo()[1].getHero().getHP()));
        Set<Attacker> copy = new HashSet<>(attackers.keySet());
        for (Attacker attacker : copy) {
            Container container = attackers.get(attacker);
            if (attacker.getHP() < 1) {
                groundedAttackers.getChildren().removeAll(container.getGroup());
                attackers.remove(attacker);
            }
            container.getAp().setText(Integer.toString(attacker.getAP()));
            container.getHp().setText(Integer.toString(attacker.getHP()));
            System.out.println(attacker.getName() + " " + attacker.getHP());
            System.out.println("\n");
        }
    }

    public void drawFlags() {
        flags.getChildren().clear();
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 9; j++) {
                Flag f = match.getBattlefield().getCell(i, j).getFlag();
                if (f != null)
                    drawFlag(i, j);
            }
    }

    private void drawFlag(int x, int y) {
        Rectangle rectangle = cells[x][y];
        try {
            ImageView flag = new ImageView(new Image(new FileInputStream("src/assets/gifs/flag.gif")));
            flag.relocate(rectangle.getLayoutX() + 10, rectangle.getLayoutY());
            flags.getChildren().add(flag);
        } catch (IOException e) {
            View.printThrowable(e);
        }
    }

    public void drawCollectables() {
        collectables.getChildren().clear();
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 9; j++) {
                Collectable c = match.getBattlefield().getCell(i, j).getCollectable();
                if (c != null)
                    drawCollectable(i, j);
            }
    }

    private void drawCollectable(int x, int y) {
        Rectangle rectangle = cells[x][y];
        try {
            ImageView collectable = new ImageView(new Image(new FileInputStream("src/assets/gifs/collectable.gif")));
            collectable.relocate(rectangle.getLayoutX() + 10, rectangle.getLayoutY());
            collectables.getChildren().add(collectable);
        } catch (IOException e) {
            View.printThrowable(e);
        }
    }

    private void moveAnimation(int u, int v) {
        Container thi = getContainer(u, v);
        if (thi != null) {
            thi.setAsRun();
            if (v - select.getY() < 0)
                thi.reverseRun();
            Duration duration = Duration.millis(2500);
            TranslateTransition t = new TranslateTransition(duration, thi.getGroup());
            t.setByY((TILE_SIZE + VALLEY_SIZE) * (u - select.getX()));
            t.setByX((TILE_SIZE + VALLEY_SIZE) * (v - select.getY()));
            t.play();
            t.setOnFinished(event -> thi.setAsGif());
            if (v - select.getY() < 0)
                thi.reverseRun();
            return;
        }
    }

    private void deselect() {
        if (selectedRect != null)
            selectedRect.setStyle("-fx-fill: rgba(0, 0, 0, 0.1)");
        if (selectedInHand != null)
            selectedInHand.getRect().setStyle("-fx-fill: rgba(0,0,0,0.1);");
        selectedRect = null;
        select.setX(-1);
        selectedInHand = null;
        battleMenu.deselect();
    }

    private Attacker getAttacker(int r, int c) {
        for (Map.Entry<Attacker, Container> entry : attackers.entrySet())
            if (entry.getKey().getCurrentCell().getX() == r
                    && entry.getKey().getCurrentCell().getY() == c)
                return entry.getKey();
        return null;
    }

    private Container getContainer(int r, int c) {
        for (Map.Entry<Attacker, Container> entry : attackers.entrySet())
            if (entry.getKey().getCurrentCell().getX() == r
                    && entry.getKey().getCurrentCell().getY() == c)
                return entry.getValue();
        return null;
    }

    public void attackAnimation(int u, int v) {
        Container thi = getContainer(select.getX(), select.getY());
        if (thi != null) {
            thi.setAsAttack();
            if (select.getY() > v) {
                thi.attackReverse();
            }
            Duration duration = Duration.millis(1000);
            TranslateTransition t = new TranslateTransition(duration, thi.getGroup());
            t.setByY(0);
            t.setByY(0);
            t.play();
            if (select.getY() > v) {
                thi.attackReverse();
            }
            t.setOnFinished(event -> thi.setAsGif());
        }
    }

    public void drawAttackers() {
        groundedAttackers.getChildren().clear();
        attackers = new HashMap<>();
        for (Attacker a : match.getBothGroundedAttackers()) {
            match.showBattleField();
            Container c = new Container();
            c.setCard(a);
            c.setImages();
            System.out.println(a.getCurrentCell().getX());
            System.out.println(a.getCurrentCell().getY());
            Rectangle r = cells[a.getCurrentCell().getX()][a.getCurrentCell().getY()];
            c.getGroup().relocate(r.getLayoutX() - 28, r.getLayoutY() - 60);
            groundedAttackers.getChildren().add(c.getGroup());
            attackers.put(a, c);
        }
    }

    private void drawHub() {
        endTurn.relocate(1200, 500);
        graveyard.relocate(1200, 700);
        endTurn.relocate(1200, 620);
        try {
            endTurnImage = new ImageView(new Image(new FileInputStream("src/assets/resources/ui/button_end_turn_mine.png")));
            endTurnImage.setFitWidth(200);
            endTurnImage.setFitHeight(80);
            endTurnGlow = new ImageView(new Image(new FileInputStream("src/assets/resources/ui/button_end_turn_mine_glow.png")));
            endTurnGlow.setFitWidth(200);
            endTurnGlow.setFitHeight(80);
            Label label = new Label("END TURN");
            label.setTextFill(Color.WHITE);
            label.setFont(Font.font(20));
            endTurn.getChildren().addAll(endTurnImage, label);
        } catch (Exception e) {
            View.printThrowable(e);
        }
        try {
            ImageView graveyardImage = new ImageView(new Image(new FileInputStream("src/assets/resources/ui/button_end_turn_enemy.png")));
            graveyardImage.setFitWidth(200);
            graveyardImage.setFitHeight(80);
            Label label = new Label("GRAVEYARD");
            label.setTextFill(Color.WHITE);
            label.setFont(Font.font(20));
            graveyard.getChildren().addAll(graveyardImage, label);
        } catch (Exception e) {
            View.printThrowable(e);
        }

        drawHand();
        drawSpecialPower();
        handleChanges();
        drawInfoBars();
        hub.getChildren().addAll(endTurn, hand, manaBar, graveyard);
    }

    private void drawInfoBars() {
        drawMana();
        drawGeneralsIcons();

        Label you = new Label("YOU");
        you.setTextFill(Color.WHITE);
        you.setFont(Font.font(20));
        you.relocate(290, 85);
        Label opponent = new Label("OPPONENT");
        opponent.setTextFill(Color.BLACK);
        opponent.setFont(Font.font(20));
        opponent.relocate(1130, 85);
        hub.getChildren().addAll(you, opponent);
    }

    public void drawMana() {
        manaBar.getChildren().clear();
        manaBar.relocate(285, 115);
        try {
            int mana = match.getInfo(Player.getCurrentPlayer()).getMp();
            for (int i = 0; i < mana; i++) {
                ImageView activeMana = new ImageView(new Image(new FileInputStream("src/assets/resources/ui/icon_mana@2x.png")));
                activeMana.setFitWidth(31);
                activeMana.setFitHeight(34.5);
                manaBar.getChildren().add(activeMana);
            }
            for (int i = mana; i < 9; i++) {
                ImageView inactiveMana = new ImageView(new Image(new FileInputStream("src/assets/resources/ui/icon_mana_inactive@2x.png")));
                inactiveMana.setFitWidth(31);
                inactiveMana.setFitHeight(34.5);
                manaBar.getChildren().add(inactiveMana);
            }
        } catch (IOException e) {
            View.printThrowable(e);
        }
    }

    private void drawGeneralsIcons() {
        try {
            String n = Integer.toString(Math.abs(match.getPlayers()[0].getUsername().hashCode()) % 7 + 1);
            String m = Integer.toString(Math.abs(match.getPlayers()[1].getUsername().hashCode()) % 7 + 1);

            ImageView icon1 = new ImageView(new Image(new FileInputStream("src/assets/generals/general_" + n + ".png")));
            ImageView icon2 = new ImageView(new Image(new FileInputStream("src/assets/generals/general_" + m + ".png")));
            icon1.setScaleX(0.4);
            icon1.setScaleY(icon1.getScaleX());
            icon2.setScaleX(icon1.getScaleY());
            icon2.setScaleY(icon2.getScaleX());
            icon1.relocate(0, -150);
            icon2.relocate(1100, -150);
            hub.getChildren().addAll(icon1, icon2);

            ImageView hpIcon1 = new ImageView(new Image(new FileInputStream("src/assets/resources/ui/icon_general_hp@2x.png")));
            hpIcon1.relocate(186, 128);
            hpIcon1.setFitWidth(62);
            hpIcon1.setFitHeight(68);
            ImageView hpIcon2 = new ImageView(new Image(new FileInputStream("src/assets/resources/ui/icon_general_hp@2x.png")));
            hpIcon2.relocate(1285.8, 128);
            hpIcon2.setFitWidth(62);
            hpIcon2.setFitHeight(68);
            hub.getChildren().addAll(hpIcon1, hpIcon2);

            hp1 = new Label(Integer.toString(match.getPlayersMatchInfo()[0].getHero().getHP()));
            hp1.setTextFill(Color.WHITE);
            hp1.setFont(Font.font(20));
            hp1.relocate(206, 147);
            hp2 = new Label(Integer.toString(match.getPlayersMatchInfo()[1].getHero().getHP()));
            hp2.setTextFill(Color.WHITE);
            hp2.setFont(Font.font(20));
            hp2.relocate(1305, 147);
            hub.getChildren().addAll(hp1, hp2);
        } catch (Exception ex) {
            View.printThrowable(ex);
        }
    }

    public void drawHand() {
        hand.getChildren().clear();
        hand.relocate(300, 625);
        hand.setSpacing(10);

        try {
            StackPane s = new StackPane();
            ImageView replaceBackground = new ImageView(new Image(new FileInputStream("src/assets/resources/ui/replace_background@2x.png")));
            ImageView outerRing = new ImageView(new Image(new FileInputStream("src/assets/resources/ui/replace_outer_ring_smoke@2x.png")));
            replaceBackground.setFitWidth(150);
            replaceBackground.setFitHeight(replaceBackground.getFitWidth());
            outerRing.setFitWidth(replaceBackground.getFitWidth());
            outerRing.setFitHeight(replaceBackground.getFitWidth());
            Card nextInHand = match.getInfo(Player.getCurrentPlayer()).getDeck().getCards().get(0);
            Container co = new Container();
            co.setCard(nextInHand);
            co.setImages();

            s.getChildren().addAll(replaceBackground, outerRing, co.getGroup());
            hand.getChildren().add(s);
        } catch (IOException e) {
            View.printThrowable(e);
        }

        PlayerMatchInfo info = match.getInfo(Player.getCurrentPlayer());
        for (Card c : info.getHand().getCards()) {
            try {
                Container co = new Container();
                co.setCard(c);
                Rectangle r = new Rectangle(100, 100);
                r.getStyleClass().add("rectangle");
                r.relocate(0, 25);
                co.setRect(r);
                co.setImages();
                Label name = new Label(c.getName());
                name.setTextFill(Color.WHITE);
                name.relocate(10, 53);
                hand.getChildren().add(co.getGroup());
                co.getGroup().setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        deselect();
                        selectedInHand = co;
                        co.getRect().setStyle("-fx-fill: rgba(255,255,0,0.35);");
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        deselect();
                    }
                });
            } catch (Exception e) {
                View.printThrowable(e);
            }
        }
    }

    private void drawSpecialPower() {
        try {
            Card c = Match.getCurrentMatch().getPlayersMatchInfo()[0].getHero().getSpecialPower();
            Container co = new Container();
            co.setCard(c);
            Rectangle r = new Rectangle(100, 100);
            r.getStyleClass().add("rectangle");
            co.getGroup().relocate(180, 300);
            co.setRect(r);
            co.setImages();
            Label name = new Label(c.getName());
            name.setTextFill(Color.WHITE);
            root.getChildren().add(co.getGroup());
            name.relocate(10, 53);
            co.getGroup().setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    deselect();
                    selectedInHand = co;
                    co.getRect().setStyle("-fx-fill: rgba(255,255,0,0.35);");
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    deselect();
                }
            });
        } catch (Exception e) {
            View.printThrowable(e);
        }

        cooldown.relocate(200, 350);
        cooldown.setFont(Font.font(30));
        cooldown.setTextFill(Color.PURPLE);
        root.getChildren().add(cooldown);
    }

    private void handleChanges() {
        new AnimationTimer() {
            long last;

            @Override
            public void handle(long now) {
                if (now - last > 2000) {
                    Hero hero = Match.getCurrentMatch().getPlayersMatchInfo()[0].getHero();
                    if (hero != null) {
                        cooldown.setText(Integer.toString(hero.getCooldown()));
                        hp1.setText(Integer.toString(hero.getHP()));
                    }
                    hero = Match.getCurrentMatch().getPlayersMatchInfo()[1].getHero();

                    if (hero != null) {
                        hp2.setText(Integer.toString(hero.getHP()));
                    }
                    last = now;
                }
            }
        }.start();
    }

    private void setOnActions() {
        endTurn.setOnMousePressed(event -> {
            battleMenu.endTurn();
//            endTurn.getChildren().remove(endTurnImage);
//            if (!endTurn.getChildren().contains(endTurnGlow))
//                endTurn.getChildren().add(endTurnGlow);
        });
        endTurn.setOnMouseReleased(event -> {
//            endTurn.getChildren().remove(endTurnGlow);
//            if (!endTurn.getChildren().contains(endTurnImage))
//                endTurn.getChildren().add(endTurnImage);
        });
        graveyard.setOnMouseClicked(event -> graveyard());
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                pause();
        });
    }

    private void pause() {
        final Stage pause = new Stage();
        pause.initModality(Modality.APPLICATION_MODAL);
        pause.initStyle(StageStyle.TRANSPARENT);
        pause.initOwner(View.getPrimaryStage());
        pause.setMaximized(true);
        pause.setResizable(false);
        Button resume = new Button("RESUME");
        resume.setOnAction(event -> pause.close());
        Button gameInfo = new Button("GAME INFO");
        gameInfo.setOnAction(event -> battleMenu.showGameInfo());
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);
        vBox.setStyle("-fx-background-color: transparent");
        vBox.getChildren().addAll(resume, gameInfo);
        if (match.getGameMode() == GameMode.SINGLE_PLAYER) {
            Button back = new Button("SAVE AND BACK");
            back.setOnAction(event -> {
                pause.close();
                match.saveAndExit();
                View.back();
            });
            vBox.getChildren().addAll(back);
        }
        Scene scene = new Scene(vBox, 303, 150);
        scene.getStylesheets().add("view/stylesheets/pause_menu.css");
        scene.setFill(new Color(0, 0, 0, 0.6));
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                pause.close();
        });
        pause.setScene(scene);
        pause.show();
    }

    private void graveyard() {
        Button back = new Button("BACK");
        back.setTextFill(Color.BLACK);
        back.setOnAction(event -> View.back());
        Group root = new Group();
        ScrollPane on = new ScrollPane();
        TilePane t1 = new TilePane();
        on.setContent(t1);
        Label one = new Label("NAME : " + match.getPlayers()[0].getUsername());
        on.setMaxHeight(500);
        t1.getChildren().addAll(one);
        t1.setPrefColumns(1);
        t1.setStyle("-fx-background-color: transparent");
        for (Card card : match.getPlayersMatchInfo()[0].getGraveyard()) {
            t1.getChildren().add(CardView.shopCardGroup(card, false));
        }
        on.relocate(400, 100);

        ScrollPane tw = new ScrollPane();
        TilePane t2 = new TilePane();
        tw.setContent(t2);
        tw.setMaxHeight(500);
        Label two = new Label("NAME : " + match.getPlayers()[1].getUsername());
        t2.getChildren().addAll(two);
        t2.setPrefColumns(1);
        t2.setStyle("-fx-background-color: transparent");
        for (Card card : match.getPlayersMatchInfo()[1].getGraveyard()) {
            t2.getChildren().add(CardView.shopCardGroup(card, false));
        }
        tw.relocate(900, 100);


        root.getChildren().addAll(back, on, tw);

        Scene scene1 = new Scene(root, 1536, 801.59);
        scene1.getStylesheets().add("view/stylesheets/pause_menu.css");
        scene1.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                View.setScene(scene);
        });
        View.setScene(scene1);
    }

    private void setBackground() {
        try {
            ImageView background = new ImageView(new Image(new FileInputStream("src/assets/resources/maps/vanar/background.jpg")));
            background.fitWidthProperty().bind(scene.widthProperty());
            background.fitHeightProperty().bind(scene.heightProperty());
            ImageView midground = new ImageView(new Image(new FileInputStream("src/assets/resources/maps/vanar/midground@2x.png")));
            midground.fitWidthProperty().bind(scene.widthProperty());
            midground.fitHeightProperty().bind(scene.heightProperty());
            root.getChildren().addAll(background, midground);
        } catch (Exception e) {
            View.printThrowable(e);
        }
    }

    public Cell getSelect() {
        return select;
    }

    public void drawSpellEffect(Spell spell, int x, int y) {
        Rectangle rectangle = cells[x][y];
        String n = Integer.toString(Math.abs(spell.getName().hashCode()) % 4 + 1);
        try {
            ImageView effect = new ImageView(new Image(new FileInputStream("src/assets/gifs/" + "spell" + "_effect_" + n + ".gif")));
            effect.relocate(rectangle.getLayoutX() - 50, rectangle.getLayoutY() - 90);
            table.getChildren().add(effect);
            new AnimationTimer() {
                long time = System.currentTimeMillis();

                @Override
                public void handle(long now) {
                    if (System.currentTimeMillis() - time > 1000) {
                        table.getChildren().remove(effect);
                        this.stop();
                    }
                }
            }.start();
        } catch (IOException e) {
            View.printThrowable(e);
        }
    }

    public void setSelect(Cell select) {
        this.select = select;
    }

    private void handleBattleActions() {
        new AnimationTimer() {
            long last;

            @Override
            public void handle(long now) {
                if (now - last > 100) {
                    if (!battleActions.isEmpty()) {
                        BattleAction battleAction = battleActions.poll();
                        match.action(battleAction);
                    }
                    last = now;
                }
            }
        }.start();
    }

    public void addBattleAction(BattleAction battleAction) {
        this.battleActions.add(battleAction);
    }

    public void turnChange() {
        if (match.getThisTurnsPlayer().getUsername().equals(Player.getCurrentPlayer().getUsername())) {
            root.getChildren().remove(invisible);
        } else {
            if (!root.getChildren().contains(invisible))
                root.getChildren().add(invisible);
        }
    }
}
