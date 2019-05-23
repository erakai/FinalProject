package com.kai.fp.display;

import com.kai.fp.core.Renderable;
import com.kai.fp.items.Item;
import com.kai.fp.util.DrawPoint;
import com.kai.fp.util.Globals;
import com.kai.fp.util.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kai on May 19, 2019
 */
public class HUD extends HUDComponent {
    private static BufferedImage playerDisplay = ResourceManager.getSprite("hud");
    private ItemFrame weaponFrame, armorFrame;

    public HUD() {
        super(Globals.DISPLAY_WIDTH - 96, Globals.DISPLAY_HEIGHT-105, playerDisplay, 96, 105);

        weaponFrame = new ItemFrame(getX() + 8, getY() + 12, Item.ItemType.WEAPON);
        armorFrame = new ItemFrame(getX() + 8, getY() + 56, Item.ItemType.ARMOR);
    }

    @Override
    public void render(DrawPoint dp, Graphics g) {
        super.render(new DrawPoint(getX(), getY()), g);

        weaponFrame.render(new DrawPoint(weaponFrame.getX(), weaponFrame.getY()), g);
        armorFrame.render(new DrawPoint(armorFrame.getX(), armorFrame.getY()), g);
    }
}
