package com.kai.fp.objs.entities.enemies.w3;

import com.kai.fp.core.Game;
import com.kai.fp.objs.Animation;
import com.kai.fp.objs.entities.enemies.EnemyLoader;
import com.kai.fp.objs.entities.enemies.EnemyResource;
import com.kai.fp.objs.entities.enemies.ProjectileEnemy;
import com.kai.fp.util.ResourceManager;
import com.kai.fp.world.WorldLocation;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Kai on May 30, 2019
 */
public class CandleMan extends ProjectileEnemy {
    private static BufferedImage[] frames = {
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 128, 96, 32, 32).getScaledInstance(32,32, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 160, 96, 32, 32).getScaledInstance(24,32, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 192, 96, 32, 32).getScaledInstance(32,32, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 224, 96, 32, 32).getScaledInstance(32,32, Image.SCALE_FAST))
    };

    public CandleMan(WorldLocation location) {
        super(location, EnemyLoader.getEnemyResource("candleman"));
    }

    @Override
    public void die() {
        super.die();
        for (int i = 0; i < 4; i++) {
            int tx = getScreenX() + (int) (Math.random() * 130 - 65);
            int ty = getScreenY() + (int) (Math.random() * 130 - 65);
            Game.addToWorldQueue(new Waxy(new WorldLocation(tx, ty)));
        }
    }


    @Override
    public void move() {
        //imagine moving lmao
    }

    @Override
    protected Animation getIdleAnim() {
        return new Animation("idle", frames);
    }
}
