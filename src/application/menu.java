package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleExpression;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class menu extends Application {
    private static Font regularFont;
    private static Font boldFont;

    static {
        try {
            regularFont = Font.loadFont(new FileInputStream("PixelMplus10-Regular.ttf"), 30);
            boldFont = Font.loadFont(new FileInputStream("PixelMplus10-Regular.ttf"), 75);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private VBox menuBox;
    private int currentItem = 0;

    private ScheduledExecutorService bgThread = Executors.newSingleThreadScheduledExecutor();

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(900, 600);

        Image backgroundImage = new Image(getClass().getResourceAsStream("/background.jpg"));
        ImageView backgroundImageView = new ImageView(backgroundImage);

        backgroundImageView.fitWidthProperty().bind(root.widthProperty());
        backgroundImageView.fitHeightProperty().bind(root.heightProperty());

        Text titleText = new Text("LIGHT SOULS");
        titleText.setFont(boldFont);
        titleText.setFill(Color.MIDNIGHTBLUE);
        titleText.xProperty().bind(root.widthProperty().subtract(titleText.getBoundsInLocal().getWidth()).divide(2));
        titleText.yProperty().bind(root.heightProperty().multiply(0.3));

        MenuItem itemExit = new MenuItem("EXIT");
        itemExit.setOnActivate(() -> System.exit(0));

        menuBox = new VBox(10,
                new MenuItem("PLAY GAME"),
                new MenuItem("SETTINGS"),
                new MenuItem("HOW TO PLAY"),
                itemExit);

        menuBox.setAlignment(Pos.TOP_CENTER);

        menuBox.prefWidthProperty().bind(root.widthProperty().multiply(0.4));
        menuBox.prefHeightProperty().bind(root.heightProperty().multiply(0.5));
        DoubleExpression menuTranslateX = Bindings.multiply(root.widthProperty(), 0.3);
        DoubleExpression menuTranslateY = Bindings.multiply(root.heightProperty(), 0.4);
        menuBox.translateXProperty().bind(menuTranslateX);
        menuBox.translateYProperty().bind(menuTranslateY);

        getMenuItem(0).setActive(true);

        root.getChildren().addAll(backgroundImageView, titleText, menuBox);
        return root;
    }

    private MenuItem getMenuItem(int index) {
        return (MenuItem) menuBox.getChildren().get(index);
    }

    private static class MenuItem extends HBox {
        private TriCircle c1 = new TriCircle(), c2 = new TriCircle();
        private Text text;
        private Runnable script;

        public MenuItem(String name) {
            super(15);
            setAlignment(Pos.CENTER);

            text = new Text(name);
            text.setFont(menu.regularFont);
            text.setEffect(new GaussianBlur(2));

            getChildren().addAll(c1, text, c2);
            setActive(false);
            setOnActivate(() -> System.out.println(name + " activated"));
        }

        public void setActive(boolean b) {
            c1.setVisible(b);
            c2.setVisible(b);
            text.setFill(b ? Color.BLUE : Color.ANTIQUEWHITE);
        }

        public void setOnActivate(Runnable r) {
            script = r;
        }

        public void activate() {
            if (script != null)
                script.run();
        }
    }

    private static class TriCircle extends Parent {
        public TriCircle() {
            Shape shape1 = Shape.subtract(new Circle(3), new Circle(2));
            shape1.setFill(Color.BLUE);

            Shape shape2 = Shape.subtract(new Circle(3), new Circle(2));
            shape2.setFill(Color.BLUE);
            shape2.setTranslateX(5);

            Shape shape3 = Shape.subtract(new Circle(3), new Circle(2));
            shape3.setFill(Color.BLUE);
            shape3.setTranslateX(2.5);
            shape3.setTranslateY(-5);

            getChildren().addAll(shape1, shape2, shape3);
        }
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Light Souls");
        Image icon = new Image(getClass().getResourceAsStream("/icon.jpeg"));
        primaryStage.getIcons().add(icon);


        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                if (currentItem > 0) {
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(--currentItem).setActive(true);
                }
            }
            if (event.getCode() == KeyCode.DOWN) {
                if (currentItem < menuBox.getChildren().size() - 1) {
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(++currentItem).setActive(true);
                }
            }
            if (event.getCode() == KeyCode.ENTER) {
                getMenuItem(currentItem).activate();
            }
        });
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            bgThread.shutdownNow();
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
