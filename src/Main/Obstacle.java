package Main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Obstacle {
    private double x;
    private double y;
    public static final double WIDTH = 50;
    public static final double SPEED = 5;
    private int health; 

    public Obstacle(double x, double y) {
        this.x = x;
        this.y = y;
        this.health = 45; 
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.ORANGE);
        gc.fillRect(x, y, WIDTH, WIDTH);
    }

    public void takeDamage() {
        health--; 
        if (health <= 0) {
            destroy(); 
        }
    }

    private void destroy() {

        Main.obstacles.remove(this);
    }

    public void move() {
        y += SPEED;
    }

    public boolean collidesWith(Player player) {
        // Check collision with player
        return player.getX() + Player.WIDTH > x &&
               player.getX() < x + WIDTH &&
               player.getY() + Player.WIDTH > y &&
               player.getY() < y + WIDTH;
    }
    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}