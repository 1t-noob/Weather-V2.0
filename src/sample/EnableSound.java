package sample;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.InputStream;

public class EnableSound {
    void goodLoad() throws JavaLayerException {
        InputStream fis = Controller.class.getResourceAsStream("sound/load.mp3");
        Player playMP3 = new Player(fis);
        playMP3.play();
    }
    void errorLoad() throws JavaLayerException {
        InputStream fis = Controller.class.getResourceAsStream("sound/error.mp3");
        Player playMP3 = new Player(fis);
        playMP3.play();
    }
}
