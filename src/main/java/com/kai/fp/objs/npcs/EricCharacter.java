package com.kai.fp.objs.npcs;

import com.kai.fp.objs.Animation;
import com.kai.fp.util.ResourceManager;
import com.kai.fp.world.WorldLocation;

import java.awt.image.BufferedImage;

public class EricCharacter extends Character {
    private static BufferedImage[] frames = {
            ResourceManager.splice(ResourceManager.getSprite("enemies"), 0, 160, 32, 32),
            ResourceManager.splice(ResourceManager.getSprite("enemies"), 32, 160, 32, 32),
            ResourceManager.splice(ResourceManager.getSprite("enemies"), 64, 160, 32, 32),
            ResourceManager.splice(ResourceManager.getSprite("enemies"), 96, 160, 32, 32)

    };
    private static String[] lines = {
            "I am Eric.",
            "You are dead.",
            "Nobody has ever left this place alive.",
            "John and Sally sent you to die."
    };
    private static String[] secondLines = {
            "Facing the Gravetaker is death.",
            "We shall meet again in the afterlife."
    };
    private static String[] thirdLines = {
            "Curious.",
            "Survival was supposed to be impossible."
    };

    public EricCharacter(WorldLocation location, int variation) {
        super(location, ((variation == 0) ? lines : ((variation == 1) ?secondLines:thirdLines)), ((variation == 0) ? lines[2] : ((variation == 1) ? secondLines[1] : thirdLines[1])));
    }

    @Override
    protected Animation getIdleAnim() {
        return new Animation("idle", frames);
    }
}
