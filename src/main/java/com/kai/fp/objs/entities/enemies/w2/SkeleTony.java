package com.kai.fp.objs.entities.enemies.w2;

import com.kai.fp.objs.Animation;
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
public class SkeleTony extends ProjectileEnemy {
    private static BufferedImage[] frames = {
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 128, 32, 24, 32).getScaledInstance(32,32, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 160, 32, 24, 32).getScaledInstance(32,32, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 192, 32, 32, 32).getScaledInstance(32,32, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 224, 32, 32, 32).getScaledInstance(32,32, Image.SCALE_FAST))
    };

    public SkeleTony(WorldLocation location) {
        super(location, EnemyLoader.getEnemyResource("skele tony"));
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
