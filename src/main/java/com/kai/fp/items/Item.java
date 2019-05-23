package com.kai.fp.items;

import com.kai.fp.core.Renderable;
import com.kai.fp.objs.entities.Entity;
import com.kai.fp.objs.entities.player.Player;
import com.kai.fp.util.DrawPoint;
import com.kai.fp.util.MFont;

import java.awt.*;
import java.util.List;
import java.awt.image.BufferedImage;

/**
 * @author Kai on May 20, 2019
 */
public abstract class Item implements Renderable, ItemBehavior {
    public enum ItemType {WEAPON, ARMOR}

    private String id;
    private String description;
    private BufferedImage image;
    private List<ItemBehavior> behaviors;
    private ItemType type;
    private Rarity rarity;

    private boolean hovered = false;

    public Item(String id, BufferedImage image, List<ItemBehavior> behaviors, String description, ItemType type, Rarity rarity) {
        this.id = id;
        this.image = image;
        this.behaviors = behaviors;
        this.description = description;
        this.type = type;
        this.rarity = rarity;
    }

    public Item(Item otherItem) {
        this(otherItem.getId(), otherItem.getImage(), otherItem.getBehaviors(), otherItem.getDescription(), otherItem.getType(), otherItem.getRarity());
    }

    @Override
    public void render(DrawPoint dp, Graphics g) {
        g.drawImage(image, dp.getX(), dp.getY(), null);
        if (isHovered()) drawHUD(new DrawPoint(0,0), g);
    }

    public void drawHUD(DrawPoint dp, Graphics g) {
        g.setFont(new MFont(1.2));

        int stringHeight = g.getFontMetrics().getAscent()+5;
        int stringWidth = (int)(g.getFontMetrics().stringWidth(description) * 0.90);
        int totalStringHeight = stringHeight * (5 + behaviors.size() + (behaviors.size()-1));

        g.setColor(new Color(50, 78, 105));
        g.fillRect(10, 10, stringWidth, totalStringHeight+10);

        g.setColor(rarity.getColor());
        g.drawString(id, dp.getX() + 15, dp.getY() + 30);
        g.drawString("Rarity: " + rarity.toString().substring(0,1).toUpperCase() + rarity.toString().substring(1), dp.getX() + 15, dp.getY() + 30 + stringHeight);

        g.setFont(new MFont(1));
        g.drawString(description, dp.getX()+15, dp.getY()+(stringHeight*2));

        int lineCount = 1;
        g.setColor(Color.WHITE);
        g.drawLine(dp.getX()+15, dp.getY() + 30 + (stringHeight* (2+1)) - 5, stringWidth-5, dp.getY()+30 + (stringHeight*(2+1) - 5));
    }

    @Override
    public void onEquip(Player owner) {
        behaviors.forEach((b) -> b.onEquip(owner));
    }

    @Override
    public void onRemove(Player owner) {
        behaviors.forEach((b) -> b.onRemove(owner));
    }


    public String getId() {
        return id;
    }

    public BufferedImage getImage() {
        return image;
    }

    public List<ItemBehavior> getBehaviors() {
        return behaviors;
    }

    public boolean isHovered() {
        return hovered;
    }

    public ItemType getType() {
        return type;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public Rarity getRarity() {
        return rarity;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /*    public void setOwner(Entity owner) {
        this.owner = owner;
    }*/

}
