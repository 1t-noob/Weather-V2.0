package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // лепим иконку
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("img/icon.png")));
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("WEATHER");
        Scene scene = new Scene(root, 360, 540);
        primaryStage.setScene(scene);
        // конектим css
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        primaryStage.setResizable(true);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
