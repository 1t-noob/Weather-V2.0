package sample;


import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.scene.Node;

public class Animation {
    private TranslateTransition tt;
    public Animation(Node node) {
        tt = new TranslateTransition(Duration.millis(30),  node);
        tt.setFromX(0f);
        tt.setFromY(0f);
        tt.setByY(10f);
        tt.setByX(-10f);
        tt.setCycleCount(10);
        tt.setAutoReverse(true);
    }
    public void playAnim()  {
        tt.playFromStart();

    }
}
