package com.kai.fp.objs.npcs;

import com.kai.fp.objs.Animation;
import com.kai.fp.util.ResourceManager;
import com.kai.fp.world.WorldLocation;

import java.awt.image.BufferedImage;

public class SallyCharacter extends Character {
    private static BufferedImage[] frames = {
            ResourceManager.splice(ResourceManager.getSprite("enemies"), 0, 64, 32, 32),
            ResourceManager.splice(ResourceManager.getSprite("enemies"), 32, 64, 32, 32),
            ResourceManager.splice(ResourceManager.getSprite("enemies"), 64, 64, 32, 32)
    };
    private static String[] lines = {
            "My name is sally and im scared",
            "Please kill monsters so im less scared",
            "also why is there so much bloody fleshy stuff"
    };

    public SallyCharacter(WorldLocation location) {
        super(location, lines, lines[2]);
        anim.setFramesPerSecond(2);
    }

    @Override
    protected Animation getIdleAnim() {
        return new Animation("idle", frames);
    }
}
