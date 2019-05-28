package com.kai.fp.objs.entities.enemies.w2;

import com.kai.fp.core.Game;
import com.kai.fp.objs.Animation;
import com.kai.fp.objs.entities.enemies.CombatBehavior;
import com.kai.fp.objs.entities.enemies.EnemyLoader;
import com.kai.fp.objs.entities.enemies.EnemyResource;
import com.kai.fp.objs.entities.enemies.ProjectileEnemy;
import com.kai.fp.util.ResourceManager;
import com.kai.fp.world.WorldLocation;

import java.awt.image.BufferedImage;

/**
 * @author Kai on May 28, 2019
 */
public class Eyeball extends ProjectileEnemy {
    private static BufferedImage[] frames = {
            ResourceManager.splice(ResourceManager.getSprite("enemies"), 0, 96 ,32 , 32),
            ResourceManager.splice(ResourceManager.getSprite("enemies"), 32, 96, 32, 32),
            ResourceManager.splice(ResourceManager.getSprite("enemies"), 64, 96, 32, 32),
            ResourceManager.splice(ResourceManager.getSprite("enemies"), 96, 96, 32, 32)
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
