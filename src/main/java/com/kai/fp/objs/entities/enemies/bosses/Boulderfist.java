package com.kai.fp.objs.entities.enemies.bosses;

import com.kai.fp.core.Game;
import com.kai.fp.objs.Animation;
import com.kai.fp.objs.entities.enemies.CombatBehavior;
import com.kai.fp.objs.entities.enemies.EnemyFire;
import com.kai.fp.objs.entities.enemies.EnemyLoader;
import com.kai.fp.objs.entities.enemies.EnemyResource;
import com.kai.fp.objs.entities.enemies.w1.Goblin;
import com.kai.fp.objs.entities.enemies.w1.RangedGoblin;
import com.kai.fp.objs.entities.enemies.w1.Sludge;
import com.kai.fp.util.ResourceManager;
import com.kai.fp.world.WorldLocation;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Kai on May 29, 2019
 */
public class Boulderfist extends Boss {
    private static BufferedImage[] frames = {
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 128, 64, 32, 32).getScaledInstance(72,72, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 160, 64, 32, 32).getScaledInstance(72,72, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 192, 64, 32, 32).getScaledInstance(72,72, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 224, 64, 32, 32).getScaledInstance(72,72, Image.SCALE_FAST))
    };

    /*
    Stage 1: chase
    Stage 2: random shooting
    Stage 3: chase with dual fire knive thing
    Stage 4: stage 2 + 3
     */

    public Boulderfist(WorldLocation location) {
        super(location, EnemyLoader.getEnemyResource("boulderfist"), 5);
        spawnRate = 3.5;
    }

    @Override
    public void chase() {
        switch(stage) {
            case 1:
                CombatBehavior.STRAIGHT_CHASE.act(this, Game.getPlayer());
                break;
            case 2:
                break;
            case 3:
                CombatBehavior.STRAIGHT_CHASE.act(this, Game.getPlayer());
                break;
            case 4:
                CombatBehavior.STRAIGHT_CHASE.act(this, Game.getPlayer());
                manageSpawning();
                break;
        }
    }

    @Override
    public void attack() {
        super.attack();
        switch(stage) {
            case 2: case 3: case 4:
                createProjectile();
                break;
        }
    }

    @Override
    public void createProjectile() {
        switch(stage) {
            case 2:
                getFire().fire(this, Game.getPlayer());
                break;
            case 3:
                getFire().fire(this, Game.getPlayer());
                EnemyFire.DUAL_FIRE.fire(this, Game.getPlayer());
                break;
            case 4:
                EnemyFire.DUAL_FIRE.fire(this, Game.getPlayer());
                break;
        }
    }

    @Override
    public void checkTransitions() {
        int startingStage = stage;
        switch (startingStage) {
            case 1:
                if (healthTransition(0.80)) {
                    nextStage();
                    getStat("speed").baseValue = 2;
                }
                break;
            case 2:
                if (healthTransition(0.60)) {
                    nextStage();
                }
                break;
            case 3:
                if (healthTransition(0.40)) {
                    nextStage();
                }
                break;
        }
    }


    @Override
    public void spawnMinion() {
        WorldLocation location = new WorldLocation(getLocation());
        location.setWorldX(location.getWorldX() + (int)(Math.random () * 40 - 20));
        location.setWorldY(location.getWorldY() + (int)(Math.random () * 40 - 20));
        int spawnRandom = (int)(Math.random() * 10);
        if (spawnRandom < 6) {
            Game.addToWorldQueue(new Goblin(new WorldLocation(location)));
        } else if (spawnRandom < 8) {
            Game.addToWorldQueue(new RangedGoblin(new WorldLocation(location)));
        } else if (spawnRandom < 10) {
            Game.addToWorldQueue(new Sludge(new WorldLocation(location)));
        }
    }

    @Override
    protected Animation getIdleAnim() {
        return new Animation("idle", frames);
    }
}
