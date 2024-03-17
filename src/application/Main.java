package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Game game = new Game();
        game.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}