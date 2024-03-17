package Main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.*;

public class Player{
	private double x, y;
	public static List<Bullet> bullets = new ArrayList<>();
	public static final double WIDTH = 50;
	private boolean shooting = false, damage = false;
	private int hp = 100;
	
	public Player(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public double getX(){
		return this.x;
	}
	
	public double getY(){
		return this.y;
	}
	
	public int getHp(){
		return this.hp;
	}
	
	public void takeDamage(int dmg){
		if (damage) return;
		this.hp -= dmg;
		damage = true;
		Main.shedule(150, () -> damage = false);
	}
	
	public void render(GraphicsContext gc){
		gc.setFill(Color.YELLOW);
		gc.fillOval(this.x, this.y, WIDTH, WIDTH);
		for (int i = 0; i < Player.bullets.size(); i++){
			Player.bullets.get(i).render(gc);
		}
	}
	
	public void move(double dx, double dy) {
	    // Check collision with obstacles before moving
	    if (!checkObstacleCollision(x + dx, y + dy)) {
	        x += dx;
	        y += dy;
	    }
	}

	private boolean checkObstacleCollision(double newX, double newY) {
	    // Check collision with each obstacle
	    for (Obstacle obstacle : Main.obstacles) {
	        if (newX < obstacle.getX() + Obstacle.WIDTH &&
	            newX + Player.WIDTH > obstacle.getX() &&
	            newY < obstacle.getY() + Obstacle.WIDTH &&
	            newY + Player.WIDTH > obstacle.getY()) {
	            // Collision detected
	            return true;
	        }
	    }

	    return false;
	}
	
	public void shoot(double x, double y){
		if (shooting) return;
		shooting = true;
		Main.shedule(150, () -> this.shooting = false);
		double angle = Math.atan2(y-this.y, x-this.x);
		Bullet b = new Bullet(angle, this.x+WIDTH/2, this.y+WIDTH/2);
		Player.bullets.add(b);
	}
	
	
	
}
