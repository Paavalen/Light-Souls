package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.io.File;

public class Player {
    private ImageView playerSprite;
    private Image[] upImages, downImages, leftImages, rightImages;
    private double x, y;
    private double speed;
    private boolean moveUp, moveDown, moveLeft, moveRight;
    private int hp;
    private int maxHp;
    private int frameCounter;

    public Player(double startX, double startY, double speed) {
        String resourcesPath = "src/resources/player/";
        String up1 = resourcesPath + "up_1.png";
        String up2 = resourcesPath + "up_2.png";
        String down1 = resourcesPath + "down_1.png";
        String down2 = resourcesPath + "down_2.png";
        String left1 = resourcesPath + "left_1.png";
        String left2 = resourcesPath + "left_2.png";
        String right1 = resourcesPath + "right_1.png";
        String right2 = resourcesPath + "right_2.png";

        this.upImages = new Image[]{new Image(new File(up1).toURI().toString()), new Image(new File(up2).toURI().toString())};
        this.downImages = new Image[]{new Image(new File(down1).toURI().toString()), new Image(new File(down2).toURI().toString())};
        this.leftImages = new Image[]{new Image(new File(left1).toURI().toString()), new Image(new File(left2).toURI().toString())};
        this.rightImages = new Image[]{new Image(new File(right1).toURI().toString()), new Image(new File(right2).toURI().toString())};

        // Set initial sprite to downImage
        this.playerSprite = new ImageView(downImages[0]);
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.maxHp = 100;
        this.hp = maxHp;
        this.frameCounter = 0;
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
                break;
            case S:
                moveDown = true;
                break;
            case A:
                moveLeft = true;
                break;
            case D:
                moveRight = true;
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
        frameCounter++;

        if (frameCounter >= 15) {
            frameCounter = 0;
            updateSpriteImages();
        }

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

    private void updateSpriteImages() {
        if (moveUp) {
            playerSprite.setImage(upImages[frameCounter % 2]);
        } else if (moveDown) {
            playerSprite.setImage(downImages[frameCounter % 2]);
        } else if (moveLeft) {
            playerSprite.setImage(leftImages[frameCounter % 2]);
        } else if (moveRight) {
            playerSprite.setImage(rightImages[frameCounter % 2]);
        }
    }
}
