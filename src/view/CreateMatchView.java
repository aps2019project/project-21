package view;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;
import controller.menus.MainMenu;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import models.AIPlayer;
import models.Player;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CreateMatchView {
    private static CreateMatchView instance = new CreateMatchView();

    public static CreateMatchView getInstance() {
        return instance;
    }

    private CreateMatchView() {
    }

    private Group root = new Group();
    private Scene scene = new Scene(root, 1536, 801.59);
    private Stack<Parent> boxes = new Stack<>();
    private HBox singleOrMultiplayer = new HBox();
    private VBox chooseOpp = new VBox();
    private HBox goalMode = new HBox();
    private HBox gameType = new HBox();
    private HBox storyMode = new HBox();
    private VBox customMode = new VBox();
    private ImageView back = new ImageView();
    private Group single = new Group();
    private Group multiplayer = new Group();
    private Group killHero = new Group();
    private Group holdFlag = new Group();
    private Group gatherFlag = new Group();
    private Label flagCount = new Label("1");
    private Group story = new Group();
    private Group custom = new Group();
    private Group story1 = new Group();
    private Group story2 = new Group();
    private Group story3 = new Group();

    void run() {
        View.setScene(scene);
        boxes.push(singleOrMultiplayer);
    }

    {
        scene.getStylesheets().add("view/stylesheets/create_match_view.css");

        setBackground();

        draw();

        setOnActions();

        handleChanges();
    }

    private void draw() {
        drawBack();

        drawSingleOrMultiplayer();

        drawMultiplayerChooseOpp();

        drawGoalMode();

        drawGameType();

        drawStoryMode();

        drawCustomMode();

        root.getChildren().add(back);
    }

    private void drawBack() {
        try {
            back = new ImageView(new Image(new FileInputStream
                    ("src/assets/resources/ui/button_back_corner@2x.png")));
            back.setFitWidth(90);
            back.setFitHeight(95);
        } catch (IOException e) {
            View.printThrowable(e);
        }
    }

    private void drawSingleOrMultiplayer() {
        single = makePanel("src/assets/resources/play/play_mode_sandbox@2x.jpg", "SINGLE");
        multiplayer = makePanel("src/assets/resources/play/play_mode_rankedladder@2x.jpg", "MULTIPLAYER");
        singleOrMultiplayer.relocate(470, 200);
        singleOrMultiplayer.setSpacing(100);
        singleOrMultiplayer.getChildren().addAll(single, multiplayer);
    }

    private void drawGameType() {
        story = makePanel("src/assets/resources/codex/chapters_coming_soon_preview@2x.jpg", "STORY");
        custom = makePanel("src/assets/resources/codex/chapter21_preview@2x.jpg", "CUSTOM");
        gameType.relocate(470, 200);
        gameType.setSpacing(100);
        gameType.getChildren().addAll(story, custom);
    }

    private void drawStoryMode() {
        story1 = makePanel("src/assets/resources/challenges/gate_013@2x.jpg", "STORY 1");
        story2 = makePanel("src/assets/resources/challenges/gate_014@2x.jpg", "STORY 2");
        story3 = makePanel("src/assets/resources/challenges/gate_015@2x.jpg", "STORY 3");
        storyMode.setSpacing(100);
        storyMode.relocate(310, 200);
        storyMode.getChildren().addAll(story1, story2, story3);
    }

    private void drawCustomMode() {
        customMode = drawChooseOpponent(new ArrayList<>(AIPlayer.getAiPlayers()));
        customMode.setSpacing(5);
        customMode.relocate(650, 200);
        customMode.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5)");
    }

    private void drawMultiplayerChooseOpp() {
        List<Player> opponents = new ArrayList<>();
        for (Player player : Player.getPlayers())
            if (!player.getUsername().equals(Player.getCurrentPlayer().getUsername()))
                opponents.add(player);
        chooseOpp = drawChooseOpponent(opponents);
        chooseOpp.setSpacing(5);
        chooseOpp.relocate(650, 200);
    }

    private VBox drawChooseOpponent(List<Player> playerList) {
        VBox vBox = new VBox();
        StackPane s = new StackPane();
        Rectangle r = new Rectangle(200, 50);
        r.setStyle("-fx-fill: rgba(0, 0, 0, 0.7)");
        Label label = new Label("CHOOSE YOUR OPPONENT:");
        label.setFont(Font.font(12));
        label.setStyle("-fx-text-fill: rgba(255, 255, 255, 0.85)");
        s.getChildren().addAll(r, label);
        vBox.getChildren().add(s);
        for (Player player : playerList) {
            s = new StackPane();
            r = new Rectangle(200, 50);
            r.setStyle("-fx-fill: rgba(0, 0, 0, 0.3)");
            Label heroName = new Label();
            if (player instanceof AIPlayer)
                heroName.setText(player.getCollection().getMainDeck().getHero().getName());
            else
                heroName.setText(player.getUsername());
            heroName.setFont(Font.font(20));
            heroName.setStyle("-fx-text-fill: rgba(255, 255, 255, 0.85)");
            s.getChildren().addAll(r, heroName);
            s.setOnMouseClicked(event -> {
                if (player instanceof AIPlayer) {
                    if (MainMenu.getInstance().chooseAI(((AIPlayer) player).getAiID()))
                        boxes.push(goalMode);
                } else if (MainMenu.getInstance().chooseOpp(player.getUsername()))
                    MainMenu.getInstance().startMatch();
            });
            s.setOnMouseEntered(event -> heroName.setStyle("-fx-text-fill: orange"));
            s.setOnMouseExited(event -> heroName.setStyle("-fx-text-fill: rgba(255, 255, 255, 0.85)"));
            vBox.getChildren().add(s);
        }
        return vBox;
    }

    private void drawGoalMode() {
        killHero = makePanel("src/assets/resources/challenges/gate_000@2x.jpg", "KILL HERO");
        holdFlag = makePanel("src/assets/resources/challenges/gate_001@2x.jpg", "HOLD FLAG");
        gatherFlag = makePanel("src/assets/resources/challenges/gate_005@2x.jpg", "GATHER FLAG");
        goalMode.setSpacing(90);
        goalMode.relocate(110, 200);
        Group group = new Group();
        try {
            ImageView image = new ImageView(new Image(new FileInputStream
                    ("src/assets/resources/ui/button_secondary@2x.png")));
            image.setFitWidth(210);
            image.setFitHeight(70);
            ImageView rightArrow = new ImageView(new Image(new FileInputStream
                    ("src/assets/resources/ui/dialogue_carat@2x.png")));
            rightArrow.relocate(200, -5);
            ImageView leftArrow = new ImageView(new Image(new FileInputStream
                    ("src/assets/resources/ui/dialogue_carat@2x.png")));
            leftArrow.relocate(-50, -5);
            leftArrow.setScaleX(-1);
            flagCount.setTextFill(Color.CYAN);
            flagCount.setFont(Font.font(30));
            flagCount.relocate(95, 12);
            rightArrow.setOnMouseClicked(event -> flagCount.setText(Integer.toString(Math.min(Integer.parseInt(flagCount.getText()) + 1, 7))));
            leftArrow.setOnMouseClicked(event -> flagCount.setText(Integer.toString(Math.max(Integer.parseInt(flagCount.getText()) - 1, 0))));
            group.getChildren().addAll(image, rightArrow, leftArrow, flagCount);
        } catch (IOException e) {
            View.printThrowable(e);
        }
        goalMode.setAlignment(Pos.CENTER);
        goalMode.getChildren().addAll(group, killHero, holdFlag, gatherFlag);
    }

    private Group makePanel(String imagePath, String name) {
        Group group = new Group();
        try {
            ImageView image = new ImageView(new Image(new FileInputStream
                    (imagePath)));
            image.setFitWidth(237);
            image.setFitHeight(435);
            ImageView trimPlate = new ImageView(new Image(new FileInputStream
                    ("src/assets/resources/ui/sliding_panel/panel_trim_plate@2x.png")));
            trimPlate.setFitWidth(237);
            trimPlate.setFitHeight(49);
            trimPlate.relocate(0, image.getFitHeight() - 20);
            Rectangle transparent = new Rectangle(237, 70);
            transparent.setStyle("-fx-fill: rgba(0, 0, 0, 0.6)");
            transparent.relocate(0, image.getFitHeight() - transparent.getHeight());
            FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
            Label label = new Label(name);
            label.setStyle("-fx-text-fill: rgba(255, 255, 255, 0.8)");
            label.setFont(Font.font(20));
            label.relocate(transparent.getWidth() / 2 - fontLoader.computeStringWidth(label.getText(), label.getFont()) / 2, transparent.getLayoutY() + 15);
            group.getChildren().addAll(image, transparent, trimPlate, label);
        } catch (IOException e) {
            View.printThrowable(e);
        }
        return group;
    }

    private void setOnActions() {
        back.setOnMouseClicked(event -> back());
        single.setOnMouseClicked(event -> {
            MainMenu.getInstance().setSingle();
            boxes.push(gameType);
            VoicePlay.select();
        });
        multiplayer.setOnMouseClicked(event -> {
            MainMenu.getInstance().setMultiplayer();
            boxes.push(goalMode);
            VoicePlay.select();
        });
        Group[] groups = {single, multiplayer, story, custom, story1, story2, story3, killHero,
                holdFlag, gatherFlag};
        for (Group g : groups) {
            g.setOnMouseEntered(event -> {
                g.setScaleX(1.07);
                g.setScaleY(1.07);
                VoicePlay.hover();
            });
            g.setOnMouseExited(event -> {
                g.setScaleX(1);
                g.setScaleY(1);
            });
        }
        killHero.setOnMouseClicked(event -> {
            MainMenu.getInstance().setKillHero();
            goalModeAction();
            VoicePlay.select();
        });
        holdFlag.setOnMouseClicked(event -> {
            MainMenu.getInstance().setHoldFlag(flagCount.getText());
            goalModeAction();
            VoicePlay.select();
        });
        gatherFlag.setOnMouseClicked(event -> {
            MainMenu.getInstance().setGatherFlag(flagCount.getText());
            goalModeAction();
            VoicePlay.select();
        });
        story.setOnMouseClicked(event -> {
            boxes.push(storyMode);
            VoicePlay.select();
        });
        story1.setOnMouseClicked(event -> {
            MainMenu.getInstance().startStoryMatch(1);
            VoicePlay.select();
        });
        story2.setOnMouseClicked(event -> {
            MainMenu.getInstance().startStoryMatch(2);
            VoicePlay.select();
        });
        story3.setOnMouseClicked(event -> {
            MainMenu.getInstance().startStoryMatch(3);
            VoicePlay.select();
        });
        custom.setOnMouseClicked(event -> {
            boxes.push(customMode);
            VoicePlay.select();
        });
    }

    private void goalModeAction() {
        if (boxes.contains(customMode))  // single player
            MainMenu.getInstance().startMatch();
        else  // multiplayer
            new WaitingForOppView().run();
    }

    private void back() {
        if (boxes.size() <= 1) {
            if (!boxes.isEmpty())
                boxes.pop();
            View.back();
        } else {
            root.getChildren().remove(boxes.pop());
            VoicePlay.back();
        }
    }


    private void setBackground() {
        try {
            ImageView background = new ImageView(new Image(new FileInputStream
                    ("src/assets/resources/scenes/load/scene_load_background@2x.jpg")));
            BoxBlur boxBlur = new BoxBlur();
            background.setEffect(boxBlur);
            background.fitWidthProperty().bind(scene.widthProperty());
            background.fitHeightProperty().bind(scene.heightProperty());
            root.getChildren().add(background);
        } catch (Exception e) {
            View.printThrowable(e);
        }
    }

    private void handleChanges() {
        AnimationTimer handleChanges = new AnimationTimer() {
            long last;

            @Override
            public void handle(long now) {
                if (now - last > 100) {
                    if (boxes.size() > 1)
                        root.getChildren().remove(boxes.get(boxes.size() - 2));
                    if (!boxes.isEmpty())
                        if (!root.getChildren().contains(boxes.peek()))
                            root.getChildren().add(boxes.peek());
                    last = now;
                }
            }
        };
        handleChanges.start();
    }
}
