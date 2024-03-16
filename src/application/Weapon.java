package application;

import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;

public class Weapon {
    private String name;
    private int damage;
    private double range;
    private double cooldownTime;
    private double lastFiredTime;
    private WeaponBehavior behavior;
    private List<WeaponEffect> effects;
    private ImageView weaponSprite;

    public Weapon(String name, int damage, double range, double cooldownTime, WeaponBehavior behavior, String spriteFilePath) {
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.cooldownTime = cooldownTime;
        this.lastFiredTime = cooldownTime;
        this.behavior = behavior;
        this.effects = new ArrayList<>();
        this.weaponSprite = new ImageView(spriteFilePath);
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public double getRange() {
        return range;
    }

    public double getCooldownTime() {
        return cooldownTime;
    }

    public double getLastFiredTime() {
        return lastFiredTime;
    }

    public ImageView getSprite() {
        return weaponSprite;
    }

    public void update(double deltaTime) {
        lastFiredTime += deltaTime;
        behavior.update(this, deltaTime);
        for (WeaponEffect effect : effects) {
            effect.update(this, deltaTime);
        }

        if (lastFiredTime >= cooldownTime) {
            lastFiredTime = 0.0;
            behavior.fire(this);
        }
    }

    public void addEffect(WeaponEffect effect) {
        effects.add(effect);
    }

    public void removeEffect(WeaponEffect effect) {
        effects.remove(effect);
    }

    public interface WeaponBehavior {
    	void update(Weapon weapon, double deltaTime);
    	void fire(Weapon weapon);
    }

    public interface WeaponEffect {
    	void update(Weapon weapon, double deltaTime);
    }
}
