package com.kai.fp.objs.inanimate;

import com.kai.fp.core.Game;
import com.kai.fp.core.Updatable;
import com.kai.fp.display.Clickable;
import com.kai.fp.display.FPButton;
import com.kai.fp.display.Screen;
import com.kai.fp.objs.Animation;
import com.kai.fp.util.DrawPoint;
import com.kai.fp.util.Globals;
import com.kai.fp.util.ResourceManager;
import com.kai.fp.world.WorldLocation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Portal extends InanimateObj implements Updatable, Clickable {
    private static BufferedImage[] idleAnim = {
            ResourceManager.splice(ResourceManager.getSprite("portals"), 0,0,32,32),
            ResourceManager.splice(ResourceManager.getSprite("portals"), 32,0,32,32)
    };

    private FPButton button;

    public Portal(WorldLocation location) {
        super(location);
        setPhysical(false);
        Game.addUpdatable(this);
/*
        button = new FPButton(Globals.DISPLAY_WIDTH-105, Globals.DISPLAY_HEIGHT-96-28, this);
*/
        button = new FPButton(0,0,this);
        Screen.getInstance().add(button);
    }

    @Override
    public void possibleClick(int mouseX, int mouseY) {
        String name = Game.getCurrentWorldName();
        name = name.substring(0,name.length()-1) + (Integer.valueOf(name.substring(name.length()-1)) + 1);
        Game.nextWorld(name);

    }

    @Override
    public void update(long delta) {
        if (!button.isVisible() && checkCollision(Game.getPlayer())) { button.setVisible(true); };
        if (button.isVisible() && !checkCollision(Game.getPlayer())) button.setVisible(false);
    }

    @Override
    protected Animation getIdleAnim() {
        return new Animation("idle", idleAnim);
    }
}
