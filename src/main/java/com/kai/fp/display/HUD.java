package com.kai.fp.display;

import com.kai.fp.core.Game;
import com.kai.fp.core.Renderable;
import com.kai.fp.items.Item;
import com.kai.fp.util.DrawPoint;
import com.kai.fp.util.Globals;
import com.kai.fp.util.MFont;
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

        weaponFrame.render(new DrawPoint(getX() + 8, getY() + 12), g);
        armorFrame.render(new DrawPoint(getX() + 8, getY() + 56), g);

        g.setColor(Color.RED);
        int playerHealth = Game.getPlayer().getStat("health").getValue();
        int maxPlayerHealth = Game.getPlayer().getStat("max health").getValue();
        g.fillRect(getX()+48, (int)(getY()+13 + ((1.00 - ((double)playerHealth/maxPlayerHealth)) * 78)), 14, (int)((double)playerHealth/maxPlayerHealth * 78));

        g.setColor(Color.BLACK);
        g.setFont(new MFont(0.9));
        g.drawString("Quest: " + Game.getCurrentWorldName().substring(Game.getCurrentWorldName().length()-1), getX() + 6, getY() + 10);
        g.setFont(new MFont(1.2));
        g.drawString(playerHealth + "", getX() + 68, getY() + 55);
        g.drawString("----", getX()+68, getY()+62);
        g.drawString(maxPlayerHealth+ "", getX()+68, getY()+72);
    }

    public ItemFrame getWeaponFrame() {
        return weaponFrame;
    }

    public ItemFrame getArmorFrame() {
        return armorFrame;
    }
}
