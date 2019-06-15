package view;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class VolumeController {
    public static VolumeController instance = new VolumeController();
    private Stage stage = new Stage();
    private Group root = new Group();
    private HBox layout = new HBox(5);
    Scene scene = new Scene(root, 200, 200);

    public static VolumeController getInstance() {
        return instance;
    }

    {
        setBackground();

        root.getChildren().addAll(layout);

        stage.setTitle("Volume");
        stage.setScene(scene);
    }

    public void run(){

        MediaPlayer mediaPlayer = VoicePlay.getThisMediaPlayer();
        MediaView mediaView = new MediaView(mediaPlayer);

        layout.setStyle("-fx-padding: 10;");
        layout.getChildren().clear();
        layout.getChildren().addAll(
                createVolumeControls(mediaPlayer),
                mediaView
        );

        stage.show();
    }

    private void setBackground() {
        try {
            ImageView background = new ImageView(new Image(new FileInputStream("src\\assets\\volUP.png")));
            background.fitWidthProperty().bind(scene.widthProperty());
            background.fitHeightProperty().bind(scene.heightProperty());
            root.getChildren().add(background);
        } catch (Exception e) {
            View.printThrowable(e);
        }
    }

    private Region createVolumeControls(final MediaPlayer mediaPlayer) {
        final Slider volumeSlider = new Slider(0, 1, VoicePlay.getThisMediaPlayer().getVolume());
        volumeSlider.relocate(10, 0);
        volumeSlider.setOrientation(Orientation.VERTICAL);

        mediaPlayer.volumeProperty().bindBidirectional(volumeSlider.valueProperty());



        VBox controls = new VBox(5);
        controls.getChildren().addAll(
                volumeSlider
        );
        controls.setAlignment(Pos.CENTER);
        VBox.setVgrow(volumeSlider, Priority.ALWAYS);


        return controls;
    }
}
