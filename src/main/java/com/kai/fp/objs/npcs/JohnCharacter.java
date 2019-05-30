package com.kai.fp.objs.npcs;

import com.kai.fp.objs.Animation;
import com.kai.fp.util.ResourceManager;
import com.kai.fp.world.WorldLocation;

import java.awt.image.BufferedImage;

public class JohnCharacter extends Character {
    private static BufferedImage[] frames = {
            ResourceManager.splice(ResourceManager.getSprite("enemies"), 0, 32, 32, 32),
            ResourceManager.splice(ResourceManager.getSprite("enemies"), 32, 32, 32, 32),
            ResourceManager.splice(ResourceManager.getSprite("enemies"), 64, 32, 32, 32)
    };
    private static String[] lines = {
            "My name is john and im scared",
            "Please kill monsters so im less scared",
            "Theres a portal and items around too"
    };
    private static String[] secondLines = {
            "there is an angry kitchen dude in there",
            "the portal is behind him so good luck",
            "if you aren't fast enough i will die )))))):"
    };

    public JohnCharacter(WorldLocation location, boolean variation) {
        super(location, ((!variation) ? lines : secondLines), ((!variation) ? lines[1] : secondLines[2]));
        anim.setFramesPerSecond(2);
    }

    @Override
    protected Animation getIdleAnim() {
        return new Animation("idle", frames);
    }
}
