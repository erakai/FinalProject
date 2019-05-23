package com.kai.fp.items;

import com.kai.fp.util.DrawPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @author Kai on May 21, 2019
 */
public class Armor extends Item {

    public Armor(String id, BufferedImage image, List<ItemBehavior> behaviors, String description, ItemType type) {
        super(id, image, behaviors, description, type);
    }


}
