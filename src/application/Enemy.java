package application;

import javafx.scene.image.ImageView;

public class Enemy {
    private ImageView enemySprite;
    private double x, y;
    private double speed;
    private int hp;
    private int maxHp;
    private int damage;
    private boolean isDead;

    private Player player; 
    
    public Enemy(String spriteFilePath, double startX, double startY, double speed, int maxHp, int damage, Player player) {
        this.enemySprite = new ImageView(spriteFilePath);
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.damage = damage;
        this.isDead = false;
        this.player = player;
        this.enemySprite.setX(x);
        this.enemySprite.setY(y);
    }

    public ImageView getSprite() {
        return enemySprite;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public boolean isDead() {
        return isDead;
    }

    public void takeDamage(int damage) {
        hp = Math.max(0, hp - damage);
        if (hp == 0) {
            isDead = true;
        }
    }

    public void update(double deltaTime) {
        double dx = player.getX() - x;
        double dy = player.getY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            dx /= distance; 
            dy /= distance;

            x += dx * speed * deltaTime;
            y += dy * speed * deltaTime;
        }

        enemySprite.setX(x);
        enemySprite.setY(y);

        double playerX = player.getX();
        double playerY = player.getY();
        double playerRadius = player.getRadius();
        double enemyRadius = getRadius();

        if (distance <= playerRadius + enemyRadius) {
            player.takeDamage(damage);
        }
    }

    private double getRadius() {
        return enemySprite.getFitWidth() / 2;
    }
}