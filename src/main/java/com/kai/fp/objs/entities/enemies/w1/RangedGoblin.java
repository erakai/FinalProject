package com.kai.fp.objs.entities.enemies.w1;

import com.kai.fp.core.Game;
import com.kai.fp.objs.Animation;
import com.kai.fp.objs.entities.enemies.CombatBehavior;
import com.kai.fp.objs.entities.enemies.Enemy;
import com.kai.fp.objs.entities.enemies.EnemyLoader;
import com.kai.fp.objs.entities.enemies.ProjectileEnemy;
import com.kai.fp.util.ResourceManager;
import com.kai.fp.world.WorldLocation;

import java.awt.image.BufferedImage;

public class RangedGoblin extends ProjectileEnemy {
    private static BufferedImage[] frames = {
            ResourceManager.splice(ResourceManager.getSprite("enemies"), 0, 0, 32, 32),
            ResourceManager.splice(ResourceManager.getSprite("enemies"), 32, 0, 32, 32),
            ResourceManager.splice(ResourceManager.getSprite("enemies"), 64, 0, 32, 32),
            ResourceManager.splice(ResourceManager.getSprite("enemies"), 96, 0, 32, 32)
    };


    public RangedGoblin(WorldLocation location) {
        super(location, EnemyLoader.getEnemyResource("ranged goblin"));
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
