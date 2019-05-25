package com.kai.fp.objs.inanimate;

import com.kai.fp.objs.Animation;
import com.kai.fp.util.DrawPoint;
import com.kai.fp.util.ResourceManager;
import com.kai.fp.world.WorldLocation;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Kai on May 24, 2019
 */
public class Tentacle extends InanimateObj {
    private static BufferedImage[] frames = {
            ResourceManager.splice(ResourceManager.getSprite("tentacle"), 0, 0, 32, 32),
            ResourceManager.splice(ResourceManager.getSprite("tentacle"), 32, 0, 32, 32),
            ResourceManager.splice(ResourceManager.getSprite("tentacle"), 64, 0, 32, 32),
            ResourceManager.splice(ResourceManager.getSprite("tentacle"), 96, 0, 32, 32)

    };

    public Tentacle(WorldLocation location) {
        super(location);
        anim.setFramesPerSecond(3);
    }


    @Override
    protected Animation getIdleAnim() {
        return new Animation("idle", frames);
    }
}
