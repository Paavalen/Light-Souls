package application;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService; 

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleExpression;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox; 
import javafx.scene.layout.Pane; 
import javafx.scene.layout.VBox; 
import javafx.scene.paint.Color; 
import javafx.scene.shape.Circle; 
import javafx.scene.shape.Shape; 
import javafx.scene.text.Font; 
import javafx.scene.text.FontWeight; 
import javafx.scene.text.Text; 
import javafx.stage.Stage; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class menu extends Application {
	private static final Font FONT = Font.font("", FontWeight.BOLD, 18);
	private VBox menuBox;
	private int currentItem = 0;
			
	private ScheduledExecutorService bgThread = Executors.newSingleThreadScheduledExecutor();	
	
	private Parent createContent() {
		Pane root = new Pane(); 
		root.setPrefSize(900, 600);
		
		Image backgroundImage = new Image(getClass().getResourceAsStream("/bg1.png"));
        ImageView backgroundImageView = new ImageView(backgroundImage);

        backgroundImageView.fitWidthProperty().bind(root.widthProperty());
        backgroundImageView.fitHeightProperty().bind(root.heightProperty());
		
        Text titleText = new Text("LIGHT SOULS");
        titleText.setFont(Font.font("", FontWeight.BOLD, 60));
        titleText.setFill(Color.PALEGOLDENROD);
        titleText.xProperty().bind(root.widthProperty().subtract(titleText.getBoundsInLocal().getWidth()).divide(2));
        titleText.yProperty().bind(root.heightProperty().multiply(0.3)); 
        
		MenuItem itemExit = new MenuItem("EXIT"); 
		itemExit.setOnActivate(()-> System.exit(0));
		
		menuBox = new VBox(10,
				new MenuItem("PLAY GAME"), 
				new MenuItem("SETTINGS"), 
				new MenuItem("HOW TO PLAY"), 
				itemExit);
		
		menuBox.setAlignment (Pos. TOP_CENTER); 
		
		menuBox.prefWidthProperty().bind(root.widthProperty().multiply(0.4)); // Adjust the multiplier as needed
	    menuBox.prefHeightProperty().bind(root.heightProperty().multiply(0.5)); // Adjust the multiplier as needed
	    DoubleExpression menuTranslateX = Bindings.multiply(root.widthProperty(), 0.3); // Adjust the multiplier as needed
	    DoubleExpression menuTranslateY = Bindings.multiply(root.heightProperty(), 0.4); // Adjust the multiplier as needed
	    menuBox.translateXProperty().bind(menuTranslateX);
	    menuBox.translateYProperty().bind(menuTranslateY);
        
		getMenuItem(0).setActive(true);
		
		root.getChildren().addAll(backgroundImageView, titleText, menuBox);
		return root;
	}
	

	
	private MenuItem getMenuItem(int index) {
		return (MenuItem)menuBox.getChildren().get(index);
	}
	

	
	private static class MenuItem extends HBox {
		private TriCircle c1 = new TriCircle(), c2 = new TriCircle();
		private Text text;
		private Runnable script;
		
		public MenuItem(String name) {
			super(15);
			setAlignment(Pos.CENTER);
			
			text = new Text(name);
			text.setFont(FONT);
			text.setEffect(new GaussianBlur(2));
			
			getChildren().addAll(c1, text, c2);
			setActive(false);
			setOnActivate(() -> System.out.println(name + "activated"));
		}
		
		public void setActive(boolean b) {
			c1.setVisible(b);
			c2.setVisible(b);
			text.setFill(b ? Color.	LIGHTSALMON : Color.PALEGOLDENROD);
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
			shape1.setFill(Color.LIGHTSALMON);
			
			Shape shape2 = Shape.subtract(new Circle(3), new Circle(2));
			shape2.setFill(Color.LIGHTSALMON);
			shape2.setTranslateX(5);
			
			Shape shape3 = Shape.subtract(new Circle(3), new Circle(2));
			shape3.setFill(Color.LIGHTSALMON);
			shape3.setTranslateX(2.5);
			shape3.setTranslateY(-5);
			
			getChildren().addAll(shape1, shape2, shape3);
			
		}
	}

	
	public void start(Stage primaryStage) throws Exception {		
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