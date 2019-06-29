package view;

import controller.menus.BattleMenu;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
import java.util.HashMap;
import java.util.Map;

public class BattleView {
    private static final int TILE_SIZE = 70;
    private static final int VALLEY_SIZE = 4;

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

    private void updateAttackers() {
        for (Attacker attacker : attackers.keySet()) {
            Container container = attackers.get(attacker);
            if (attacker.getHP() < 1){
                container.setAsDeath();
            }
            container.getAp().setText(Integer.toString(attacker.getAP()));
            container.getHp().setText(Integer.toString(attacker.getHP()));
            System.out.println(attacker.getName() + " " + attacker.getHP());
        }
        System.out.println("\n");
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
        }
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
            if (select.getY() > v){
                thi.attackReverse();
            }
            Duration duration = Duration.millis(1000);
            TranslateTransition t = new TranslateTransition(duration, thi.getGroup());
            t.setByY(0);
            t.setByY(0);
            t.play();
            if (select.getY() > v){
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
        endTurn.relocate(1300, 700);
        pause.relocate(1300, 750);
        drawHand();
        drawGeneralsIcon();
        hub.getChildren().addAll(endTurn, pause, hand);

    }

    private void drawGeneralsIcon() {
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

    private void setBackground() {
        try {
            ImageView background = new ImageView(new Image(new FileInputStream("src/assets/resources/maps/vanar/background.jpg")));
            background.fitWidthProperty().bind(scene.widthProperty());
            background.fitHeightProperty().bind(scene.heightProperty());
            root.getChildren().add(background);
        } catch (Exception e) {
            View.printThrowable(e);
        }
        try {
            ImageView midground = new ImageView(new Image(new FileInputStream("src/assets/resources/maps/vanar/midground@2x.png")));
            midground.fitWidthProperty().bind(scene.widthProperty());
            midground.fitHeightProperty().bind(scene.heightProperty());
            root.getChildren().add(midground);
        } catch (Exception e) {
            View.printThrowable(e);
        }
    }
}