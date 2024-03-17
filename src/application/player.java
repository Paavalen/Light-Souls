package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.io.File;

public class Player {
    private ImageView playerSprite;
    private Image upImage, downImage, leftImage, rightImage;
    private double x, y;
    private double speed;
    private boolean moveUp, moveDown, moveLeft, moveRight;
    private int hp;
    private int maxHp;

    public Player(double startX, double startY, double speed) {
        String resourcesPath = "src/resources/";
        String upImagePath = resourcesPath + "up_1.png";
        String downImagePath = resourcesPath + "down_1.png";
        String leftImagePath = resourcesPath + "left_1.png";
        String rightImagePath = resourcesPath + "right_1.png";

        this.upImage = new Image(new File(upImagePath).toURI().toString());
        this.downImage = new Image(new File(downImagePath).toURI().toString());
        this.leftImage = new Image(new File(leftImagePath).toURI().toString());
        this.rightImage = new Image(new File(rightImagePath).toURI().toString());

        // Set initial sprite to downImage
        this.playerSprite = new ImageView(downImage);
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.maxHp = 100;
        this.hp = maxHp;
        this.playerSprite.setX(x);
        this.playerSprite.setY(y);
    }
    
    public ImageView getSprite() {
        return playerSprite;
    }
    
    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }
    
    public void takeDamage(int damage) {
        hp = Math.max(0, hp - damage); 
    }

    public void heal(int healAmount) {
        hp = Math.min(maxHp, hp + healAmount); 
    }
    
    public void handleInput(KeyCode code) {
        switch (code) {
            case W:
                moveUp = true;
                playerSprite.setImage(upImage);
                break;
            case S:
                moveDown = true;
                playerSprite.setImage(downImage);
                break;
            case A:
                moveLeft = true;
                playerSprite.setImage(leftImage);
                break;
            case D:
                moveRight = true;
                playerSprite.setImage(rightImage);
                break;
            default:
                break;
        }
    }

    public void stopInput(KeyCode code) {
        switch (code) {
            case W:
                moveUp = false;
                break;
            case S:
                moveDown = false;
                break;
            case A:
                moveLeft = false;
                break;
            case D:
                moveRight = false;
                break;
            default:
                break;
        }
    }

    public void update(double deltaTime) {
        double dx = 0, dy = 0;
        
        if (moveUp) {
            dy -= speed * deltaTime;
        }
        if (moveDown) {
            dy += speed * deltaTime;
        }
        if (moveLeft) {
            dx -= speed * deltaTime;
        }
        if (moveRight) {
            dx += speed * deltaTime;
        }

        x += dx;
        y += dy;

        playerSprite.setX(x);
        playerSprite.setY(y);
    }
}
