package view;

import controller.menus.MainMenu;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
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
    private Stack<VBox> vBoxes = new Stack<>();
    private VBox singleOrMultiplayer = new VBox();
    private VBox chooseOpp = new VBox();
    private VBox goalMode = new VBox();
    private VBox gameType = new VBox();
    private VBox storyMode = new VBox();
    private VBox customMode = new VBox();
    private Button back = new Button("BACK");
    private Button single = new Button("SINGLE");
    private Button multiplayer = new Button("MULTIPLAYER");
    private TextField opp = new TextField();
    private Button killHero = new Button("KILL HERO");
    private Button holdFlag = new Button("HOLD FLAG");
    private Button gatherFlag = new Button("GATHER FLAG");
    private TextField flagCount = new TextField();
    private Button story = new Button("STORY");
    private Button custom = new Button("CUSTOM");
    private Button story1 = new Button("STORY 1");
    private Button story2 = new Button("STORY 2");
    private Button story3 = new Button("STORY 3");
    private TextField aiNum = new TextField();

    void run() {
        View.getInstance().setScene(scene);
        vBoxes.push(singleOrMultiplayer);
    }

    {
        scene.getStylesheets().add("view/stylesheets/create_match_view.css");

        setBackground();

        draw();

        setOnActions();

        handleChanges();
    }

    private void draw() {
        back.relocate(100, 500);
        root.getChildren().add(back);

        singleOrMultiplayer.setSpacing(15);
        singleOrMultiplayer.relocate(100, 100);
        singleOrMultiplayer.getChildren().addAll(single, multiplayer);

        chooseOpp.setSpacing(15);
        chooseOpp.relocate(100, 100);
        opp.setPromptText("OPP");
        chooseOpp.getChildren().addAll(opp);

        flagCount.setPromptText("FLAG COUNT");
        goalMode.setSpacing(15);
        goalMode.relocate(100, 100);
        goalMode.getChildren().addAll(flagCount, killHero, holdFlag, gatherFlag);

        gameType.setSpacing(15);
        gameType.relocate(100, 100);
        gameType.getChildren().addAll(story, custom);

        storyMode.setSpacing(15);
        storyMode.relocate(100, 100);
        storyMode.getChildren().addAll(story1, story2, story3);

        customMode.setSpacing(15);
        customMode.relocate(100, 100);
        customMode.getChildren().addAll(aiNum);
    }

    private void setOnActions() {
        single.setOnAction(event -> {
            MainMenu.getInstance().setSingle();
            vBoxes.push(gameType);
        });
        back.setOnAction(event -> back());
        multiplayer.setOnAction(event -> {
            MainMenu.getInstance().setMultiplayer();
            vBoxes.push(chooseOpp);
        });
        opp.setOnAction(event -> {
            if (MainMenu.getInstance().chooseOpp(opp.getText()))
                vBoxes.push(goalMode);
        });
        killHero.setOnAction(event -> {
            MainMenu.getInstance().setKillHero();
            MainMenu.getInstance().startMatch();
        });
        holdFlag.setOnAction(event -> {
            MainMenu.getInstance().setHoldFlag(flagCount.getText());
            MainMenu.getInstance().startMatch();
        });
        gatherFlag.setOnAction(event -> {
            MainMenu.getInstance().setGatherFlag(flagCount.getText());
            MainMenu.getInstance().startMatch();
        });
        story.setOnAction(event -> vBoxes.push(storyMode));
        story1.setOnAction(event -> MainMenu.getInstance().startStoryMatch(1));
        story2.setOnAction(event -> MainMenu.getInstance().startStoryMatch(2));
        story3.setOnAction(event -> MainMenu.getInstance().startStoryMatch(3));
        custom.setOnAction(event -> vBoxes.push(customMode));
        aiNum.setOnAction(event -> {
            if (MainMenu.getInstance().chooseAI(aiNum.getText()))
                vBoxes.push(goalMode);
        });
    }

    private void back() {
        if (vBoxes.size() <= 1) {
            if (!vBoxes.isEmpty())
                vBoxes.pop();
            MainMenuView.getInstance().run();
        } else
            root.getChildren().remove(vBoxes.pop());
    }


    private void setBackground() {
        try {
            ImageView background = new ImageView(new Image(new FileInputStream("src/assets/resources/scenes/load/scene_load_background.jpg")));
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
                    if (vBoxes.size() > 1)
                        root.getChildren().remove(vBoxes.get(vBoxes.size() - 2));
                    if (!vBoxes.isEmpty())
                        if (!root.getChildren().contains(vBoxes.peek()))
                            root.getChildren().add(vBoxes.peek());
                    last = now;
                }
            }
        };
        handleChanges.start();
    }
}
