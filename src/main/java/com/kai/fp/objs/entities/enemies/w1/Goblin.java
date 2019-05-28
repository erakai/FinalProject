package com.kai.fp.objs.entities.enemies.w1;

import com.kai.fp.core.Game;
import com.kai.fp.objs.Animation;
import com.kai.fp.objs.entities.enemies.CombatBehavior;
import com.kai.fp.objs.entities.enemies.Enemy;
import com.kai.fp.objs.entities.enemies.EnemyLoader;
import com.kai.fp.util.ResourceManager;
import com.kai.fp.world.WorldLocation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Goblin extends Enemy {
    private static BufferedImage[] frames = {
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 0, 0, 32, 32).getScaledInstance(16,16, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 32, 0, 32, 32).getScaledInstance(16,16, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 64, 0, 32, 32).getScaledInstance(16,16, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 96, 0, 32, 32).getScaledInstance(16,16, Image.SCALE_FAST))
    };


    public Goblin(WorldLocation location) {
        super(location, EnemyLoader.getEnemyResource("goblin"));
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
