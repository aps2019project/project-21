package view;

import controller.menus.BattleMenu;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import models.Player;
import models.card.AttackMode;
import models.card.Attacker;
import models.card.Card;
import models.match.Match;
import models.match.PlayerMatchInfo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    private Button endTurn = new Button("END TURN");
    private Button pause = new Button("PAUSE");
    private Group hub = new Group();
    private Rectangle selectedRect;
    private Cell select = new Cell(-1, -1);
    private Container selectedInHand;
    private HBox hand = new HBox();
    private HBox manaBar = new HBox();
    private Button graveyard = new Button("GRAVEYARD");

    public void run() {
        View.getInstance().setScene(scene);
    }

    {
        scene.getStylesheets().add("view/stylesheets/battle_view.css");

        setBackground();

        drawTable();

        handleSelection();

        drawHub();

        setOnActions();

        root.getChildren().addAll(hub, table);
    }


    private void drawTable() {
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
                            int r = BattleMenu.getInstance().moveOrAttack(u, v);
                            if (r == 0) {
                                moveAnimation(u, v);
                                deselect();
                            } else if (r == 1) {
                                deselect();
                            } else if (r == 2) {
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
                            } else {
                                deselect();
                            }
                        } else if (selectedInHand != null) {
                            BattleMenu.getInstance().insertCardIn(selectedInHand.getCard().getName(), u, v);
                            deselect();
                        } else {
                            if (BattleMenu.getInstance().selectAttacker(getAttacker(u, v))) {
                                if (selectedRect != null)
                                    selectedRect.setStyle("-fx-fill: rgba(0, 0, 0, 0.1)");
                                rect.setStyle("-fx-fill: rgba(255, 255, 0, 0.2)");
                                selectedRect = rect;
                                select.setX(u);
                                select.setY(v);
                            }
                        }
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        deselect();
                    }
                });
            }
    }

    public void updateAttackers() {
        try {
            boolean one = match.getPlayersMatchInfo()[0].getHero().getHP() < 1;
        } catch (Exception ex) {
            View.getInstance().popup(match.getPlayers()[1].getUsername() + " win!");
        }
        try {
            boolean two = match.getPlayersMatchInfo()[1].getHero().getHP() < 1;
        } catch (Exception ex) {
            View.getInstance().popup(match.getPlayers()[0].getUsername() + " win!");
        } finally {
            hp1.setText(Integer.toString(match.getPlayersMatchInfo()[0].getHero().getHP()));
            hp2.setText(Integer.toString(match.getPlayersMatchInfo()[1].getHero().getHP()));
            for (Attacker attacker : attackers.keySet()) {
                Container container = attackers.get(attacker);
                if (attacker.getHP() < 1) {
                    groundedAttackers.getChildren().removeAll(container.getGroup());
                }
                container.getAp().setText(Integer.toString(attacker.getAP()));
                container.getHp().setText(Integer.toString(attacker.getHP()));
                System.out.println(attacker.getName() + " " + attacker.getHP());
                System.out.println("\n");
            }
        }

    }

    public TranslateTransition moveAnimation(int u, int v) {
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
            return t;
        }
        return null;
    }

    private void deselect() {
        if (selectedRect != null)
            selectedRect.setStyle("-fx-fill: rgba(0, 0, 0, 0.1)");
        if (selectedInHand != null)
            selectedInHand.getRect().setStyle("-fx-fill: rgba(0,0,0,0.35);");
        selectedRect = null;
        select.setX(-1);
        selectedInHand = null;
        match.deselect();
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

    private void attackAnimation(int u, int v) {
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
            c.getGroup().setOnMouseClicked(event -> {
//                    if (event.getButton() == MouseButton.PRIMARY) {
//                        match.setSelectedCard(a);
//                        c.currentRectangle.setStyle("-fx-fill: rgba(255, 255, 0, 0.2)");
//                    } else if (event.getButton() == MouseButton.SECONDARY) {
//                        match.setSelectedCard(null);
//                        c.currentRectangle.setStyle("-fx-fill: rgba(0, 0, 0, 0.1)");
//                    }
            });
            groundedAttackers.getChildren().add(c.getGroup());
            attackers.put(a, c);
        }
    }

    private void drawHub() {
        graveyard.relocate(1300, 600);
        endTurn.setPadding(new Insets(10, 10, 10, 10));

        endTurn.relocate(1300, 700);
        endTurn.setPadding(new Insets(10, 10, 10, 10));
        try {
            BackgroundImage backgroundImage = new BackgroundImage(new Image(new FileInputStream("src/assets/resources/ui/button_end_turn_mine_glow@2x.png")),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, false, false, true, true));
            endTurn.setBackground(new Background(backgroundImage));

            BackgroundImage backgroundImag = new BackgroundImage(new Image(new FileInputStream("src/assets/resources/ui/button_end_turn_mine_glow@2x.png")),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, false, false, true, true));
            graveyard.setBackground(new Background(backgroundImag));
        } catch (Exception e) {
            View.printThrowable(e);
        }
        pause.relocate(1300, 750);
        drawHand();
        drawInfoBars();
        hub.getChildren().addAll(graveyard, endTurn, pause, hand, manaBar);
    }

    private void drawInfoBars() {
        drawMana();
        drawGeneralsIcons();

        Label you = new Label("YOU");
        you.setTextFill(Color.WHITE);
        you.setFont(Font.font(20));
        you.relocate(290, 85);
        Label opponent = new Label("OPPONENT");
        opponent.setTextFill(Color.WHITE);
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
            ImageView icon1 = new ImageView(new Image(new FileInputStream("src/assets/generals/general_portrait_image_hex_f1.png")));
            ImageView icon2 = new ImageView(new Image(new FileInputStream("src/assets/generals/general_portrait_image_hex_f4-alt.png")));
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

            hp1.setText(Integer.toString(match.getPlayersMatchInfo()[0].getHero().getHP()));
            hp1.setTextFill(Color.WHITE);
            hp1.setFont(Font.font(20));
            hp1.relocate(206, 147);
            hp2.setText(Integer.toString(match.getPlayersMatchInfo()[1].getHero().getHP()));
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
        hand.relocate(400, 600);
        hand.setSpacing(10);

        PlayerMatchInfo info = match.getInfo(Player.getCurrentPlayer());
        for (Card c : info.getHand().getCards()) {
            try {
                Container co = new Container();
                co.setCard(c);
                Rectangle r = new Rectangle(100, 100);
                r.relocate(0, 25);
                r.setStyle("-fx-fill:  rgba(0,0,0,0.35);");
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

    private void setOnActions() {
        endTurn.setOnAction(event -> match.endTurn());
        pause.setOnAction(event -> pause());
        graveyard.setOnAction(event -> graveyard());
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                pause();
        });
    }

    private void pause() {
        final Stage pause = new Stage();
        pause.initModality(Modality.APPLICATION_MODAL);
        pause.initStyle(StageStyle.TRANSPARENT);
        pause.initOwner(View.getInstance().getPrimaryStage());
        pause.setMaximized(true);
        pause.setResizable(false);
        Button resume = new Button("RESUME");
        resume.setOnAction(event -> pause.close());
        Button back = new Button("BACK");
        back.setOnAction(event -> {
            pause.close();
            View.getInstance().back();
        });
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);
        vBox.setStyle("-fx-background-color: transparent");
        vBox.getChildren().addAll(resume, back);
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
        back.setOnAction(event -> {
            View.getInstance().setScene(scene);
        });
        Group root = new Group();
        ScrollPane on = new ScrollPane();
        TilePane t1 = new TilePane();
        on.setContent(t1);
        Label one = new Label("NAME : " + match.getPlayers()[0].getUsername());
        on.setMaxHeight(500);
        t1.getChildren().addAll(one);
        t1.setPrefColumns(1);
        t1.setStyle("-fx-background-color: transparent");
        for (Card card : match.getPlayersMatchInfo()[0].getGraveyard()){
            t1.getChildren().add(CardView.shopCardGroup(card));
        }
        on.relocate(400,100);

        ScrollPane tw = new ScrollPane();
        TilePane t2 = new TilePane();
        tw.setContent(t2);
        tw.setMaxHeight(500);
        Label two = new Label("NAME : " + match.getPlayers()[1].getUsername());
        t2.getChildren().addAll(two);
        t2.setPrefColumns(1);
        t2.setStyle("-fx-background-color: transparent");
        for (Card card : match.getPlayersMatchInfo()[1].getGraveyard()){
            t2.getChildren().add(CardView.shopCardGroup(card));
        }
        tw.relocate(900,100);


        root.getChildren().addAll(back, on, tw);

        Scene scene1 = new Scene(root,1536, 801.59);
        scene1.getStylesheets().add("view/stylesheets/pause_menu.css");
        scene1.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                View.getInstance().setScene(scene);
        });
        View.getInstance().setScene(scene1);
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
}