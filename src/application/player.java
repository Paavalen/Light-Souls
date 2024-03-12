package application;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class player {
    private ImageView playerSprite;
    private double x, y;
    private double speed;
    private boolean moveUp, moveDown, moveLeft, moveRight;
    private int hp;
    private int maxHp;

    public Player(String spriteFilePath, double startX, double startY, double speed) {
        this.playerSprite = new ImageView(spriteFilePath);
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
        if (moveUp) {
            y -= speed * deltaTime;
        }
        if (moveDown) {
            y += speed * deltaTime;
        }
        if (moveLeft) {
            x -= speed * deltaTime;
        }
        if (moveRight) {
            x += speed * deltaTime;
        }

        playerSprite.setX(x);
        playerSprite.setY(y);
    }
}