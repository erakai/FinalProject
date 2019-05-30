package com.kai.fp.objs;

import com.kai.fp.core.Game;
import com.kai.fp.core.Updatable;
import com.kai.fp.objs.entities.Entity;
import com.kai.fp.objs.entities.enemies.Enemy;
import com.kai.fp.objs.entities.player.Player;
import com.kai.fp.util.DrawPoint;
import com.kai.fp.util.Globals;
import com.kai.fp.world.WorldLocation;
import com.kai.fp.world.WorldTile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile extends GameObject implements Updatable {
    private Entity owner;
    private BufferedImage image;
    private WorldLocation target;
    private int speed, range, variance;
    private int damage;

    private int distanceTraveled = 0;

    public Projectile(int width, int height) {
        super(new WorldLocation(0,0), width, height);
    }

    public Projectile(WorldLocation location, WorldLocation target, Entity owner, Projectile base) {
        super(location, base.getWidth(), base.getHeight());
        this.owner = owner;
        setDamage(base.getDamage() + owner.getStat("damage").getValue());
        setSpeed(base.getSpeed());
        setTarget(target);
        setRange(base.getRange());
        setVariance(base.getVariance());
        setImage(base.getImage());
        setLoneImage(image);

        getTarget().setWorldX(getTarget().getWorldX() + ((int)(Math.random() * variance * 2 - variance)));
        getTarget().setWorldY(getTarget().getWorldY() + ((int)(Math.random() * variance * 2 - variance)));


        //TODO: Update target to go beyond where mouse clicked and reach max range
    }

    @Override
    protected Animation getIdleAnim() {
        return new Animation("idle", new BufferedImage[] {image});
    }


    @Override
    public void update(long delta) {
        double deltaX = getTarget().getWorldX() - getScreenX();
        double deltaY = getTarget().getWorldY() - getScreenY();
        double direction = Math.atan2(deltaY, deltaX);

        int xTravelAmount = (int) ((speed * Math.cos(direction)));
        int yTravelAmount = (int) ((speed * Math.sin(direction)));

        updateDistanceTraveled(xTravelAmount, yTravelAmount);

        //TODO: Make range not really broken depending on screen size.
        if (distanceTraveled > range && speed > 0) {
            die();
        }
        if (Math.abs(getScreenX() - getTarget().getWorldX()) < 5 && Math.abs(getScreenY() - getTarget().getWorldY()) < 5) {
            die();
        }

        getLocation().setWorldX(getScreenX() + xTravelAmount);
        getLocation().setWorldY(getScreenY() + yTravelAmount);

        checkCollisions();
    }

    private void checkCollisions() {
        if (owner instanceof Player) {
            for (GameObject g: Game.getWorld().getObjects()) {
                if (g instanceof Enemy) {
                    if (checkCollision(g)) {
                        ((Enemy) g).takeDamage(damage);
                        //TODO: check if piercing and if it is then add enemy to a do not hit list
                        die();
                    }
                }
            }
        } else {
            if (checkCollision(Game.getPlayer())) {
                Game.getPlayer().takeDamage(damage);
                die();
            }
        }

        WorldTile tile = Game.getWorld().getTile(getCenterX()/WorldTile.WIDTH, (getCenterY())/WorldTile.HEIGHT);
        if (tile != null && !tile.getType().equals("void") && !tile.isWalkable()) {
            die();
        }
    }


    private void updateDistanceTraveled(int xAmount, int yAmount) {
        distanceTraveled += Math.abs(xAmount);
        distanceTraveled += Math.abs(yAmount);
    }

    public WorldLocation getTarget() {
        return target;
    }

    public void setTarget(WorldLocation target) {
        this.target = target;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getRange() {
        return range/15;
    }

    public void setRange(int range) {
        this.range = range * 15;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setVariance(int variance) {
        this.variance = variance;
    }

    public int getVariance() {
        return variance;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Projectile{" +
                "target=" + target +
                ", speed=" + speed +
                ", range=" + range +
                ", variance=" + variance +
                ", damage=" + damage +
                ", location=" + getLocation() +
                '}';
    }
}
