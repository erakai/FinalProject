package com.kai.fp.items;

import com.kai.fp.util.DrawPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @author Kai on May 21, 2019
 */
public class Armor extends Item {

    public Armor(String id, BufferedImage image, List<ItemBehavior> behaviors, String description, Rarity rarity) {
        super(id, image, behaviors, description, ItemType.ARMOR, rarity);
    }

    public Armor(Item otherItem) {
        super(otherItem);
    }
}
