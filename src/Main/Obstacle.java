package Main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Obstacle {
    private double x;
    private double y;
    public static final double WIDTH = 50;
    public static final double SPEED = 5;
    private int health; // Health attribute for the obstacle

    public Obstacle(double x, double y) {
        this.x = x;
        this.y = y;
        this.health = 45; // Initial health
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.ORANGE);
        gc.fillRect(x, y, WIDTH, WIDTH);
    }

    public void takeDamage() {
        health--; // Reduce health by 1
        if (health <= 0) {
            destroy(); // Destroy obstacle if health reaches zero
        }
    }

    private void destroy() {
        // Remove obstacle from the game
        // You can implement removal logic here
        // For example, remove it from the list of obstacles
        Main.obstacles.remove(this);
    }

    public void move() {
        // Move the obstacle downwards
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