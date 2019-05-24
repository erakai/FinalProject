package com.kai.fp.items;

import java.awt.image.BufferedImage;
import java.util.List;

public class Weapon extends Item {

    public Weapon(String id, BufferedImage image, List<ItemBehavior> behaviors, String description, ItemType type, Rarity rarity) {
        super(id, image, behaviors, description, type, rarity);
    }

    public Weapon(Item otherItem) {
        super(otherItem);
}
    }
