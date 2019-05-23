package com.kai.fp.objs.inanimate;

import com.kai.fp.objs.Animation;
import com.kai.fp.util.ResourceManager;
import com.kai.fp.world.WorldLocation;

import java.awt.image.BufferedImage;

/**
 * @author Kai on May 15, 2019
 */
public class Rock extends InanimateObj {
    private static BufferedImage[] frames = {
            ResourceManager.splice(ResourceManager.getSprite("rock"), 0, 0 ,32 , 32),
            ResourceManager.splice(ResourceManager.getSprite("rock"), 32, 0, 32, 32)
    };

    public Rock(WorldLocation location) {
        super(location);
        anim.setFramesPerSecond(1);
    }

    @Override
    protected Animation getIdleAnim() {
        return new Animation("idle", frames);
    }
}
