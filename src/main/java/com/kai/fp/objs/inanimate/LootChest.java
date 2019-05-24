package com.kai.fp.objs.inanimate;

import com.kai.fp.core.Game;
import com.kai.fp.core.Updatable;
import com.kai.fp.display.ItemFrame;
import com.kai.fp.display.Screen;
import com.kai.fp.items.Item;
import com.kai.fp.items.Rarity;
import com.kai.fp.objs.Animation;
import com.kai.fp.util.DrawPoint;
import com.kai.fp.util.Globals;
import com.kai.fp.util.ResourceManager;
import com.kai.fp.world.WorldLocation;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Kai on May 22, 2019
 */
public class LootChest extends InanimateObj implements Updatable {
    private static BufferedImage commonChest = ResourceManager.splice(ResourceManager.getSprite("chests"), 0,32,32,32);
    private static BufferedImage rareChest = ResourceManager.splice(ResourceManager.getSprite("chests"), 32,32,32,32);
    private static BufferedImage glyphicChest = ResourceManager.splice(ResourceManager.getSprite("chests"), 64,32,32,32);
    private static BufferedImage chestContents = ResourceManager.getSprite("chestcontents");

    private ItemFrame[] items;
    private boolean showItems = false;

    public LootChest(WorldLocation location) {
        super(location);
        setPhysical(false);
        Game.addUpdatable(this);

        items = new ItemFrame[4];
        for (int i = 0; i < 4; i++) {
            items[i] = new ItemFrame(0,0);
        }
    }

    @Override
    public void update(long delta) {
        if (!showItems && checkCollision(Game.getPlayer())) {
            showItems = true;
            for (ItemFrame i: items) { i.setOnScreen(true); }
        } else if (showItems && !checkCollision(Game.getPlayer())) {
            for (ItemFrame i: items) { i.setOnScreen(false); }
            showItems = false;
        }

        boolean empty = true;
        for (ItemFrame i: items) {
            if (i.getItem() != null) {
                empty = false;
            }
        }
        if (empty) {
            die();
        }
    }

    @Override
    public void render(DrawPoint dp, Graphics g) {
        super.render(dp, g);
        if (showItems) {
            g.drawImage(chestContents,10, 10, null);
            int x = 15;
            int y = 15;
            for (ItemFrame i: items) {
                Screen.addRenderOnTop(new DrawPoint(x, y), i);
                x+=36;
            }
        }
    }

    public void addItem(Item i) {
        for (ItemFrame frame: items) {
            if (frame.getItem() == null) {
                frame.setItem(i);
                break;
            }
        }

        updateLoneImage();
    }

    @Override
    public void die() {
        super.die();
        for (ItemFrame i: items) {
            i.die();
        }
    }

    @Override
    protected Animation getIdleAnim() {
        return new Animation("none", new BufferedImage[] {commonChest});
    }

    private void updateLoneImage() {
        Rarity highestRarity = Rarity.COMMON;
        for (ItemFrame f: items) {
            if (f.getItem() != null) {
                Item i = f.getItem();
                if (i.getRarity() == Rarity.RARE && highestRarity == Rarity.COMMON) highestRarity = Rarity.RARE;
                if (i.getRarity() == Rarity.GLYPHIC && highestRarity != Rarity.GLYPHIC) highestRarity = Rarity.GLYPHIC;
            }
        }
        if (highestRarity == Rarity.COMMON) setLoneImage(commonChest);
        if (highestRarity == Rarity.RARE) setLoneImage(rareChest);
        if (highestRarity == Rarity.GLYPHIC) setLoneImage(glyphicChest);
    }
}
