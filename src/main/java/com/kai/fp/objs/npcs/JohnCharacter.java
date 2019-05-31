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
            "Hi, I'm john. What are you doing here?",
            "You need to escape! It's not safe.",
            "Try to pick up items to become stronger.",
            "There's a portal around here somewhere."
    };
    private static String[] secondLines = {
            "There's a pretty tough dude guarding the portal in there.",
            "You're going to have to go in - good luck.",
    };

    public JohnCharacter(WorldLocation location, boolean variation) {
        super(location, ((!variation) ? lines : secondLines), ((!variation) ? lines[2] : secondLines[1]));
        anim.setFramesPerSecond(2);
    }

    @Override
    protected Animation getIdleAnim() {
        return new Animation("idle", frames);
    }
}
