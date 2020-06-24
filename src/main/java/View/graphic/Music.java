package View.graphic;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Music {
    private static Music ourInstance = new Music();
    private MediaPlayer closeSound;
    private MediaPlayer openSound;
    private MediaPlayer confirmationSound;
    private MediaPlayer backPageSound;

    public static Music getInstance() {
        return ourInstance;
    }

    private Music() {
        closeSound = getCloseSound();
        openSound = getOpenSound();
        confirmationSound = getConfirmationSound();
        backPageSound = getBackPageSound();
    }

    public void close() {
        closeSound.play();
    }

    public void open() {
        openSound.play();
    }

    public void confirmation() {
        confirmationSound.play();
    }

    public void backPage() {
        backPageSound.play();
    }

    private MediaPlayer getCloseSound() {
        File file = new File("src/main/resources/music/close.mp3");
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        return mediaPlayer;
    }

    private MediaPlayer getOpenSound() {
        File file = new File("src/main/resources/music/open.mp3");
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        return mediaPlayer;
    }

    private MediaPlayer getConfirmationSound() {
        File file = new File("src/main/resources/music/confirmation.mp3");
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        return mediaPlayer;
    }

    private MediaPlayer getBackPageSound() {
        File file = new File("src/main/resources/music/backPage.mp3");
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        return mediaPlayer;
    }
}
