package com.kai.fp.display;

import com.kai.fp.core.Game;
import com.kai.fp.core.InputHandler;
import com.kai.fp.items.Item;
import com.kai.fp.items.Rarity;
import com.kai.fp.objs.entities.player.Player;
import com.kai.fp.objs.entities.player.PlayerInventory;
import com.kai.fp.util.DrawPoint;
import com.kai.fp.util.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Kai on May 21, 2019
 */
public class ItemFrame extends HUDComponent implements Hoverable, Clickable {
    private static BufferedImage itemFrameAsset = ResourceManager.getSprite("itemframe");
    private Item.ItemType setType;
    private Item item;


    public ItemFrame(int x, int y) {
        super(x, y, itemFrameAsset, 36, 36);
        InputHandler.addHoverable(this);
        InputHandler.addClickable(this);
    }

    public ItemFrame(int x, int y, Item.ItemType type) {
        this(x, y);
        this.setType = type;
    }

    @Override
    public void render(DrawPoint dp, Graphics g) {
        super.render(dp, g);
        if (item != null) {
            if (item.getRarity() == Rarity.RARE || item.getRarity() == Rarity.GLYPHIC) {
                g.setColor(item.getRarity().getColor());
                g.fillRect(dp.getX()+1, dp.getY()+1, 34, 34);
            }
            item.render(new DrawPoint(dp.getX()+2 , dp.getY()+2), g);
        }
    }

    @Override
    public void updateHovered(int mouseX, int mouseY) {
        if (item != null && isOnScreen()) {
            item.setHovered(checkCollisionWithMouse(mouseX, mouseY));
        }
    }

    @Override
    public void possibleClick(int mouseX, int mouseY) {
        if (item != null && checkCollisionWithMouse(mouseX, mouseY) && setType == null && isOnScreen()) {
            PlayerInventory p = Game.getPlayer().getInventory();
            Item i = null;

            if (item.getType() == Item.ItemType.ARMOR) {
                i = p.removeArmor();
            }
            if (item.getType() == Item.ItemType.WEAPON) {
                i = p.removeWeapon();
            }

            p.equipItem(item);
            removeItem();
            if (i != null) {
                //System.out.println(i.getRarity());
                setItem(i);
            }
        }
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void removeItem() {
        item = null;
    }

}
