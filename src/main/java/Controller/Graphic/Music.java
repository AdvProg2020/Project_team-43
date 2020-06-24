package Controller.Graphic;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Music {
    private static Music ourInstance = new Music();
    private MediaPlayer closeSound;
    private MediaPlayer openSound;
    private MediaPlayer confirmationSound;
    private MediaPlayer backPageSound;
    private MediaPlayer errorSound;

    public static Music getInstance() {
        return ourInstance;
    }

    private Music() {
        closeSound = getCloseSound();
        openSound = getOpenSound();
        confirmationSound = getConfirmationSound();
        backPageSound = getBackPageSound();
        errorSound = getErrorSound();
    }

    public void close() {
        System.out.println("close");
        new Thread(new Runnable() {
            @Override
            public void run() {
                closeSound.stop();
                closeSound.play();
            }
        }).start();

    }

    public void open() {
        System.out.println("open");
        openSound.stop();
        openSound.play();
    }

    public void confirmation() {
        System.out.println("confirmation");
        confirmationSound.stop();
        confirmationSound.play();

    }

    public void backPage() {
        System.out.println("backPage");
        backPageSound.stop();
        backPageSound.play();
    }


    public void error() {
        System.out.println("error");
        errorSound.stop();
        errorSound.play();
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
        mediaPlayer.setAutoPlay(true);
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

    private MediaPlayer getErrorSound() {
        File file = new File("src/main/resources/music/error.mp3");
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        return mediaPlayer;
    }
}

