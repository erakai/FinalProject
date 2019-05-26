package com.kai.fp.items;

import com.kai.fp.core.Game;
import com.kai.fp.objs.Projectile;
import com.kai.fp.util.DrawPoint;
import com.kai.fp.world.WorldLocation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

public class Weapon extends Item {
    private List<Projectile> projectiles;
    private String[] hudLines;
    private ItemFire fireMode;

    public Weapon(String id, BufferedImage image, List<ItemBehavior> behaviors, String description, Rarity rarity, ItemFire fireMode, String[] hudLines, List<Projectile> projectiles) {
        super(id, image, behaviors, description, ItemType.WEAPON, rarity);
        this.fireMode = fireMode;
        this.hudLines = hudLines;
        this.projectiles = projectiles;
    }

    public Weapon(Weapon otherItem) {
        super(otherItem);
        this.hudLines = otherItem.getHudLines();
        this.fireMode = otherItem.getFireMode();
        this.projectiles = otherItem.getProjectiles();
    }

    @Override
    public void drawHUD(DrawPoint dp, Graphics g) {
        super.drawHUD(dp, g);

        int stringHeight = g.getFontMetrics().getAscent()+4;
        int totalStringHeight = stringHeight * (hudLines.length + 1);

        g.setColor(new Color(50, 78, 105));
        g.fillRect(dp.getX()+10, hudLength+5, stringWidth, totalStringHeight+10);
        g.setColor(Color.WHITE);
        g.drawLine(dp.getX()+15, hudLength+12, stringWidth-5, hudLength+12);
        g.setColor(new Color(187, 187, 187));
        for (int i = 0; i < hudLines.length; i++) {
            g.drawString(hudLines[i], dp.getX()+15, hudLength+15+((i+1) * stringHeight));
        }
    }

    public void fire(WorldLocation target) {
        fireMode.fire(this, target, Game.getPlayer());
    }

    public Projectile getProjectile(int index) {
        return projectiles.get(index);
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public String[] getHudLines() {
        return hudLines;
    }

    public ItemFire getFireMode() {
        return fireMode;
    }
}
