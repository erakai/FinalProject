package com.kai.fp.objs.entities.enemies.w2;

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
public class Eyeball extends ProjectileEnemy {
    private static BufferedImage[] frames = {
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 0, 128 ,32 , 32).getScaledInstance(24,24, Image.SCALE_SMOOTH)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 32, 128 ,32 , 32).getScaledInstance(24,24, Image.SCALE_SMOOTH)),
    };


    public Eyeball(WorldLocation location) {
        super(location, EnemyLoader.getEnemyResource("eyeball"));
    }

    @Override
    public void move() {
        CombatBehavior.FLY_CHASE.act(this, Game.getPlayer());
    }

    @Override
    protected Animation getIdleAnim() {
        return new Animation("idle", frames);
    }
}
