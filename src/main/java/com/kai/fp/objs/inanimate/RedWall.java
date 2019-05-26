package com.kai.fp.objs.inanimate;

import com.kai.fp.objs.Animation;
import com.kai.fp.util.ResourceManager;
import com.kai.fp.world.WorldLocation;

import java.awt.image.BufferedImage;

public class RedWall extends InanimateObj {
    private static BufferedImage[] frames = {
            ResourceManager.splice(ResourceManager.getSprite("redwall"), 0, 0, 32, 32),
            ResourceManager.splice(ResourceManager.getSprite("redwall"), 32, 0, 32, 32),
            ResourceManager.splice(ResourceManager.getSprite("redwall"), 64, 0, 32, 32)
    };

    public RedWall(WorldLocation location) {
        super(location);
        anim.setFramesPerSecond(6);
    }

    @Override
    protected Animation getIdleAnim() {
        return new Animation("idle", frames);
    }
}
