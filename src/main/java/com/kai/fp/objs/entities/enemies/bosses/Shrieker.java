package com.kai.fp.objs.entities.enemies.bosses;

import com.kai.fp.core.Game;
import com.kai.fp.objs.Animation;
import com.kai.fp.objs.Projectile;
import com.kai.fp.objs.entities.Entity;
import com.kai.fp.objs.entities.enemies.CombatBehavior;
import com.kai.fp.objs.entities.enemies.EnemyFire;
import com.kai.fp.objs.entities.enemies.EnemyLoader;
import com.kai.fp.objs.entities.enemies.EnemyResource;
import com.kai.fp.objs.entities.enemies.w2.SkeleTony;
import com.kai.fp.util.Globals;
import com.kai.fp.util.ResourceManager;
import com.kai.fp.world.WorldLocation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Shrieker extends Boss {
    private static BufferedImage[] frames = {
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 0, 224, 32, 32).getScaledInstance(96,96, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 32, 224, 32, 32).getScaledInstance(96,96, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 64, 224, 32, 32).getScaledInstance(96,96, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 96, 224, 32, 32).getScaledInstance(96,96, Image.SCALE_FAST))
    };

    private boolean charging = false;
    private int chargeTick = 0, maxChargeTick;
    private int chargeDurTick = 0, maxChargeDurTick;

    private double chargeRate = 2.5;
    private double chargeDuration = 0.6;

    private int tx, ty;

    /*
        1. charges the player
        2. charges + circular fire
        3. big thicc bullets + circular fire
     */

    public Shrieker(WorldLocation location) {
        super(location, EnemyLoader.getEnemyResource("shrieker"), 3);

        maxChargeTick = (int)(chargeRate * Globals.FRAMES_PER_SECOND);

        maxChargeDurTick = (int)(chargeDuration * Globals.FRAMES_PER_SECOND);
    }

    @Override
    public void createProjectile() {
        switch(stage) {
            case 2:
                fireCorners(Game.getPlayer());
                break;
            case 3:
                EnemyFire.SECONDARY_FIRE.fire(this, Game.getPlayer());
                fireCorners(Game.getPlayer());
            break;
        }
    }

    @Override
    public void checkTransitions() {
        int startingStage = stage;
        switch (startingStage) {
            case 1:
                if (healthTransition(0.90)) {
                    nextStage();
                }
                break;
            case 2:
                if (healthTransition(0.50)) {
                    nextStage();
                    setChargeRate(1.6);
                    getProjectiles().get(0).setRange(12);
                }
                break;
        }
    }

    @Override
    public void chase() {
        manageCharging();
        if (charging) {
            switch(stage) {
                case 1:
                    charge(tx, ty);
                    break;
                case 2:
                    charge(tx, ty);
                    break;
                case 3:
                    charge(tx, ty);
                    break;
            }
        }
    }

    private void manageCharging() {
        if (!charging) {
            chargeTick++;
            if (chargeTick > maxChargeTick) {
                chargeTick = 0;
                charging = true;
                if (stage == 3) {
                    if (Math.random() > 0.7) {
                        spawnMinion();
                    }
                    EnemyFire.RANDOM_FIRE.fire(this, Game.getPlayer());
                }
                tx = Game.getPlayer().getScreenX();
                ty = Game.getPlayer().getScreenY();
            }
        } else {
            chargeDurTick++;
            if (chargeDurTick > maxChargeDurTick) {
                chargeDurTick = 0;
                charging = false;
            }
        }
    }

    private void fireCorners(Entity target) {
        Game.addToWorldQueue(new Projectile(new WorldLocation(getCenterX()-getWidth(), getCenterY()-getHeight()), new WorldLocation(target.getLocation()), this, this.getProjectiles().get(0)));
        Game.addToWorldQueue(new Projectile(new WorldLocation(getCenterX()+getWidth(), getCenterY()+getHeight()), new WorldLocation(target.getLocation()), this, this.getProjectiles().get(0)));
        Game.addToWorldQueue(new Projectile(new WorldLocation(getCenterX()-getWidth(), getCenterY()+getHeight()), new WorldLocation(target.getLocation()), this, this.getProjectiles().get(0)));
        Game.addToWorldQueue(new Projectile(new WorldLocation(getCenterX()+getWidth(), getCenterY()-getHeight()), new WorldLocation(target.getLocation()), this, this.getProjectiles().get(0)));
    }

    @Override
    public void spawnMinion() {
        Game.addToWorldQueue(new SkeleTony(new WorldLocation(getLocation())));
    }

    @Override
    protected Animation getIdleAnim() {
        return new Animation("idle", frames);
    }

    //todo: put this literally anywhere else
    public void charge(int targetX, int targetY) {
        double deltaX = targetX - getCenterX();
        double deltaY = targetY - getCenterY();
        double direction = Math.atan2(deltaY, deltaX);

        int xChange = (int)(getStat("speed").getValue() * Math.cos(direction));
        int yChange = (int)(getStat("speed").getValue() * Math.sin(direction));

        if (xChange>0) {
            moveRight(xChange);
        } else if (xChange<0) {
            moveLeft(xChange);
        }
        if (yChange > 0) {
            moveDown(yChange);
        } else if (yChange <0) {
            moveUp(yChange);
        }
    }

    public void setChargeRate(double chargeRate) {
        this.chargeRate = chargeRate;
        maxChargeTick = (int)(chargeRate * Globals.FRAMES_PER_SECOND);
    }
}
