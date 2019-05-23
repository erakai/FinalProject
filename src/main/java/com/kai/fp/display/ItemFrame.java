package com.kai.fp.display;

import com.kai.fp.items.Item;
import com.kai.fp.util.DrawPoint;
import com.kai.fp.util.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Kai on May 21, 2019
 */
public class ItemFrame extends HUDComponent implements Hoverable {
    private static BufferedImage itemFrameAsset = ResourceManager.getSprite("itemframe");
    private Item.ItemType setType;
    private Item item;

    public ItemFrame(int x, int y) {
        super(x, y, itemFrameAsset, 32, 32);
    }

    public ItemFrame(int x, int y, Item.ItemType type) {
        this(x, y);
        this.setType = type;
    }

    @Override
    public void updateHovered(int mouseX, int mouseY) {
        if (item != null) item.setHovered(checkCollisionWithMouse(mouseX, mouseY));
    }

    public void setItem(Item item) {
        if (setType == null || (setType != null && item.getType() == setType)) {
            this.item = item;
        }
    }
}
