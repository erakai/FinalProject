package com.kai.fp.objs.entities.enemies.w3;

import com.kai.fp.core.Game;
import com.kai.fp.objs.Animation;
import com.kai.fp.objs.entities.enemies.CombatBehavior;
import com.kai.fp.objs.entities.enemies.Enemy;
import com.kai.fp.objs.entities.enemies.EnemyLoader;
import com.kai.fp.objs.entities.enemies.EnemyResource;
import com.kai.fp.util.Globals;
import com.kai.fp.util.ResourceManager;
import com.kai.fp.world.WorldLocation;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Kai on May 30, 2019
 */
public class Waxy extends Enemy {
    private double duplicationRate = 4;
    private int duplicateTick = 0, maxDuplicateTick;

    private static BufferedImage[] frames = {
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 128, 128, 32, 32).getScaledInstance(24,24, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 160, 128, 32, 32).getScaledInstance(24,24, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 192, 128, 32, 32).getScaledInstance(24,24, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 224, 128, 32, 32).getScaledInstance(24,24, Image.SCALE_FAST))
    };

    public Waxy(WorldLocation location) {
        super(location, EnemyLoader.getEnemyResource("waxy"));
        maxDuplicateTick = (int)(duplicationRate * Globals.FRAMES_PER_SECOND);
    }

    @Override
    public void update(long delta) {
        super.update(delta);
        duplicateTick++;
        if (duplicateTick > maxDuplicateTick) {
            duplicateTick = 0;
            if (Math.random() > 0.7) {
                int tx = getScreenX() + (int) (Math.random() * 80 - 40);
                int ty = getScreenY() + (int) (Math.random() * 80 - 40);
                Game.addToWorldQueue(new Waxy(new WorldLocation(tx, ty)));
            }
        }
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
