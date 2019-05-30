package com.kai.fp.items;

import java.awt.image.BufferedImage;
import java.util.List;

public class Potion extends Item{

    public Potion(String id, BufferedImage image, List<ItemBehavior> behaviors, String description, Rarity rarity) {
        super(id, image, behaviors, description, ItemType.POTION, rarity);
    }

    public Potion(Item otherItem) {
        super(otherItem);
    }


}
