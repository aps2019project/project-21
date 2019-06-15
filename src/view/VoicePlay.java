package view;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

class VoicePlay {
    private static double backgroundVolume = 1;
    private static File file = new File("src\\assets\\mainMenu.m4a");
    private static File file1 = new File("src\\assets\\shop.m4a");
    private static File file2 = new File("src\\assets\\matchHistory.m4a");
    private static MediaPlayer mainMenu = new MediaPlayer(new Media(file.toURI().toString()));
    private static MediaPlayer shop = new MediaPlayer(new Media(file1.toURI().toString()));
    private static MediaPlayer matchHistory = new MediaPlayer(new Media(file2.toURI().toString()));

    static {
        mainMenu.setVolume(backgroundVolume);
        mainMenu.setCycleCount(MediaPlayer.INDEFINITE);

        shop.setVolume(backgroundVolume);
        shop.setCycleCount(MediaPlayer.INDEFINITE);

        matchHistory.setVolume(backgroundVolume);
        matchHistory.setCycleCount(MediaPlayer.INDEFINITE);

    }

    static void buttonPlay() {
        File file = new File("src\\assets\\button.m4a");
        MediaPlayer click = new MediaPlayer(new Media(file.toURI().toString()));
        click.play();

    }

    static void notification() {
        File file = new File("src\\assets\\notification.m4a");
        MediaPlayer click = new MediaPlayer(new Media(file.toURI().toString()));
        click.play();
    }

    static void setThisMenu(String n) {
        if (n.equals("main menu")) {
            mainMenu.play();
            shop.pause();
            matchHistory.pause();
        } else if (n.equals("shop")) {
            mainMenu.pause();
            shop.play();
        } else if (n.equals("match history")) {
            matchHistory.play();
            mainMenu.pause();
        }
    }
}
