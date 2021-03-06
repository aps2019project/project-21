package view;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class VoicePlay {
    private static final double backgroundVolume = 0.3;
    private static File file = new File("src/assets/mainMenu.m4a");
    private static File file1 = new File("src/assets/shop.m4a");
    private static File file2 = new File("src/assets/matchHistory.m4a");

    private static MediaPlayer mainMenu = new MediaPlayer(new Media(file.toURI().toString()));
    private static MediaPlayer shop = new MediaPlayer(new Media(file1.toURI().toString()));
    private static MediaPlayer matchHistory = new MediaPlayer(new Media(file2.toURI().toString()));

    private static String thisMenu = "main menu";

    static {
        mainMenu.setVolume(backgroundVolume);
        mainMenu.setCycleCount(MediaPlayer.INDEFINITE);

        shop.setVolume(backgroundVolume);
        shop.setCycleCount(MediaPlayer.INDEFINITE);

        matchHistory.setVolume(backgroundVolume);
        matchHistory.setCycleCount(MediaPlayer.INDEFINITE);

    }

    static void buttonPlay() {
        File file = new File("src/assets/button.m4a");
        MediaPlayer click = new MediaPlayer(new Media(file.toURI().toString()));
        click.play();
    }

    static void notification() {
        File file = new File("src/assets/notification.m4a");
        MediaPlayer click = new MediaPlayer(new Media(file.toURI().toString()));
        click.play();
    }

    static void hover() {
        playSoundEffect("hover");
    }

    static void select() {
        playSoundEffect("select");
    }

    static void setScene() {
        playSoundEffect("scene_change");
    }

    static void back() {
        playSoundEffect("back");
    }

    public static void attack() {
        playSoundEffect("attack");
    }

    public static void endturn() {
        playSoundEffect("endturn");
    }

    public static void insert() {
        playSoundEffect("insert");
    }

    public static void spell() {
        playSoundEffect("spell");
    }

    public static void move() {
        playSoundEffect("move");
    }

    public static void victory() {
        playSoundEffect("victory");
    }

    private static void playSoundEffect(String name) {
        File file = new File("src/assets/sounds/" + name + ".m4a");
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(file.toURI().toString()));
        mediaPlayer.setVolume(1);
        mediaPlayer.play();
    }

    static void setThisMenu(String n) {
        switch (n) {
            case "main menu":
                mainMenu.play();
                shop.pause();
                matchHistory.pause();
                thisMenu = "main menu";
                break;
            case "shop":
                mainMenu.pause();
                shop.play();
                thisMenu = "shop";
                break;
            case "match history":
                matchHistory.play();
                mainMenu.pause();
                thisMenu = "match history";
                break;
        }
    }

    static MediaPlayer getThisMediaPlayer() {
        switch (thisMenu) {
            case "main menu":
                return mainMenu;
            case "shop":
                return shop;
            case "match history":
                return matchHistory;
        }
        return mainMenu;
    }
}