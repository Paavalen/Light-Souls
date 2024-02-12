package main;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Light Souls");

        StackPane pane = new StackPane();
        Canvas canvas = new Canvas();

        double aspectRatio = 16.0 / 9.0;

        // Listener to maintain 16:9 aspect ratio
        stage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth) {
                double newHeight = newWidth.doubleValue() / aspectRatio;
                stage.setHeight(newHeight);
            }
        });

        canvas.widthProperty().bind(pane.widthProperty());
        canvas.heightProperty().bind(pane.heightProperty());

        pane.getChildren().add(canvas);

        Scene scene = new Scene(pane, 800, 600);
        
        Image icon = new Image(getClass().getResourceAsStream("/assets/icon.jpeg"));
		stage.getIcons().add(icon);
        
        stage.setScene(scene);
        stage.setResizable(true);

        stage.show();
    }
}