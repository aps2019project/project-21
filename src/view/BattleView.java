package view;

import controller.menus.BattleMenu;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Rectangle;
import models.card.Attacker;
import models.match.Match;

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
    private Map<Attacker, Container> map = new HashMap<>();
    private Group groundedAttackers = new Group();
    private Button endTurn = new Button("END TURN");
    private Button pause = new Button("PAUSE");
    private Group hub = new Group();
    private Rectangle selectedRect;

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

        try {
            for (Attacker a : match.getBothGroundedAttackers()) {
                match.showBattleField();
                Container c = new Container();
                c.attacker = a;
                c.idle = new ImageView(new Image(new FileInputStream("src/assets/gifs/hero_idle.gif")));
                c.run = new ImageView(new Image(new FileInputStream("src/assets/gifs/hero_run.gif")));
                c.attack = new ImageView(new Image(new FileInputStream("src/assets/gifs/hero_attack.gif")));
                System.out.println(a.getCurrentCell().getX());
                System.out.println(a.getCurrentCell().getY());
                Rectangle r = cells[a.getCurrentCell().getX()][a.getCurrentCell().getY()];
                c.currentRectangle = r;
                c.g.relocate(r.getLayoutX(), r.getLayoutY() - 40);
                c.g.getChildren().add(c.idle);
                c.g.setOnMouseClicked(event -> {
//                    if (event.getButton() == MouseButton.PRIMARY) {
//                        match.setSelectedCard(a);
//                        c.currentRectangle.setStyle("-fx-fill: rgba(255, 255, 0, 0.2)");
//                    } else if (event.getButton() == MouseButton.SECONDARY) {
//                        match.setSelectedCard(null);
//                        c.currentRectangle.setStyle("-fx-fill: rgba(0, 0, 0, 0.1)");
//                    }
                });
                groundedAttackers.getChildren().add(c.g);
                map.put(a, c);
            }

        } catch (Exception e) {
            View.printThrowable(e);
        }
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
                            BattleMenu.getInstance().moveOrAttack(u, v);
                            deselect();
                        } else {
                            if (BattleMenu.getInstance().selectAttacker(getAttacker(u, v))) {
                                if (selectedRect != null)
                                    selectedRect.setStyle("-fx-fill: rgba(0, 0, 0, 0.1)");
                                rect.setStyle("-fx-fill: rgba(255, 255, 0, 0.2)");
                                selectedRect = rect;
                            }
                        }
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        deselect();
                    }
                });
            }
    }

    private void deselect() {
        if (selectedRect != null)
            selectedRect.setStyle("-fx-fill: rgba(0, 0, 0, 0.1)");
        match.deselect();
    }

    private Attacker getAttacker(int r, int c) {
        for (Map.Entry<Attacker, Container> entry : map.entrySet())
            if (entry.getKey().getCurrentCell().getX() == r
                    && entry.getKey().getCurrentCell().getY() == c)
                return entry.getKey();
        return null;
    }

    public void showMove(Attacker a) {
        Container c = map.get(a);
        if (c != null) {
            Rectangle r = cells[a.getCurrentCell().getX()][a.getCurrentCell().getY()];
            c.g.relocate(r.getLayoutX(), r.getLayoutY() - 40);
        }
    }

    public void showAttack(Attacker a) {

    }

    private void drawHub() {
        endTurn.relocate(1300, 700);
        pause.relocate(1300, 750);
        hub.getChildren().addAll(endTurn, pause);
    }

    private void setOnActions() {
        endTurn.setOnAction(event -> match.endTurn());
        pause.setOnAction(event -> pause());
    }

    private void pause() {

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
