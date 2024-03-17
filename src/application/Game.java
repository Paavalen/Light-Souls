package application;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Game {

    public void start(Stage primaryStage) {
        // Creating the player object
        Player player = new Player(100, 100, 4);

        // Creating a pane to hold the player
        Pane root = new Pane();
        root.setPrefSize(800, 600);
        root.getChildren().add(player.getSprite());

        primaryStage.setTitle("Light Souls");

        Canvas canvas = new Canvas();

        double aspectRatio = 16.0 / 9.0;

        primaryStage.widthProperty().addListener((observable, oldWidth, newWidth) -> {
            double newHeight = newWidth.doubleValue() / aspectRatio;
            primaryStage.setHeight(newHeight);
        });

        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty());

        root.getChildren().add(canvas);

        // Creating the scene
        Scene scene = new Scene(root, Color.BLACK);

        // Handling keyboard input to move the player
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            player.handleInput(code);
        });

        scene.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            player.stopInput(code);
        });

        Image icon = new Image(getClass().getResourceAsStream("/resources/icon.jpeg"));
        primaryStage.getIcons().add(icon);
        // Moving the player
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.show();

        // Animation timer for updating the player position
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                player.update(1); // Delta time is 1 for simplicity
            }
        };
        timer.start();
    }
}
