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
            "hey john and sally DIEd because u were too slow",
            "go avenge them by taking out your frusturation and killing innocent monsters",
            "you have a moral obligation to"
    };

    public EricCharacter(WorldLocation location) {
        super(location, lines, lines[1]);
    }

    @Override
    protected Animation getIdleAnim() {
        return new Animation("idle", frames);
    }
}
