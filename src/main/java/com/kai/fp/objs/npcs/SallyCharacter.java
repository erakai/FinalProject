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
            "It's been so long since I've seen another person!",
            "I'm Sally - I assume you're trying to get out.",
            "There should be one of the portals around here.",
            "I hear there's something dangerous lurking near it."
    };

    public SallyCharacter(WorldLocation location) {
        super(location, lines, lines[3]);
        anim.setFramesPerSecond(2);
    }

    @Override
    protected Animation getIdleAnim() {
        return new Animation("idle", frames);
    }
}
