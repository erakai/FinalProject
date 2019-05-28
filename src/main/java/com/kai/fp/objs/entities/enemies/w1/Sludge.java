package com.kai.fp.objs.entities.enemies.w1;

import com.kai.fp.core.Game;
import com.kai.fp.objs.Animation;
import com.kai.fp.objs.entities.enemies.CombatBehavior;
import com.kai.fp.objs.entities.enemies.EnemyLoader;
import com.kai.fp.objs.entities.enemies.EnemyResource;
import com.kai.fp.objs.entities.enemies.ProjectileEnemy;
import com.kai.fp.util.ResourceManager;
import com.kai.fp.world.WorldLocation;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Kai on May 28, 2019
 */
public class Sludge extends ProjectileEnemy {
    private static BufferedImage[] frames = {
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 0, 96, 32, 32).getScaledInstance(32,32, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 32, 96, 32, 32).getScaledInstance(32,32, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 64, 96, 32, 32).getScaledInstance(32,32, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 96, 96, 32, 32).getScaledInstance(32,32, Image.SCALE_FAST))
    };


    public Sludge(WorldLocation location) {
        super(location, EnemyLoader.getEnemyResource("sludge"));
    }

    @Override
    public void move() {
        CombatBehavior.STRAIGHT_CHASE.act(this, Game.getPlayer());
    }

    @Override
    protected Animation getIdleAnim() {
        return new Animation("idle", frames);
    }
}
