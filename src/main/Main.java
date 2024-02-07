package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;

public class Main extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage){
        stage.setTitle("Light Souls");
        StackPane pane = new StackPane();
        Canvas canvas = new Canvas(0, 0)
        Scene scene = new Scene(pane,800,600);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
}
