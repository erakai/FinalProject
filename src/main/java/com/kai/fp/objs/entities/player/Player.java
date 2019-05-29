package com.kai.fp.objs.entities.player;

import com.kai.fp.core.Camera;
import com.kai.fp.core.Game;
import com.kai.fp.display.Screen;
import com.kai.fp.objs.Animation;
import com.kai.fp.objs.entities.Entity;
import com.kai.fp.util.DrawPoint;
import com.kai.fp.util.MFont;
import com.kai.fp.util.ResourceManager;
import com.kai.fp.world.WorldLocation;
import com.kai.fp.world.WorldTile;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Kai on May 16, 2019
 */
public class Player extends Entity {
    private static BufferedImage[] idleRight = {
            ResourceManager.splice(ResourceManager.getSprite("player"), 0, 0 ,32 , 38),
            ResourceManager.splice(ResourceManager.getSprite("player"), 32, 0 ,32 , 38),
            ResourceManager.splice(ResourceManager.getSprite("player"), 64, 0 ,32 , 38),
            ResourceManager.splice(ResourceManager.getSprite("player"), 96, 0 ,32 , 38)
    };
    private static BufferedImage[] runRight = {
            ResourceManager.splice(ResourceManager.getSprite("player"), 0, 64 ,32 , 42),
            ResourceManager.splice(ResourceManager.getSprite("player"), 32, 64 ,30 , 42),
            ResourceManager.splice(ResourceManager.getSprite("player"), 62, 64 ,32 , 42),
    };
    private static BufferedImage[] runLeft = ResourceManager.mirrorList(runRight);
    private static BufferedImage[] idleLeft = ResourceManager.mirrorList(idleRight);


    public boolean up, down, left, right;
    private PlayerInventory inventory;

    public Player(WorldLocation location) {
        super(location, 30, 35);
        getStat("max health").baseValue = 75;
        getStat("health").baseValue = 75;
        getStat("speed").baseValue = 4;
        inventory = new PlayerInventory(this);
    }

    @Override
    protected void addAnims() {
        anim.addAnim("run right", runRight);
        anim.addAnim("idle left", idleLeft);
        anim.addAnim("run left", runLeft);
    }

    @Override
    public void update(long delta) {
        super.update(delta);
        if (right) {
            anim.setRepeatingAnim("run right");
        } else if (left) {
            anim.setRepeatingAnim("run left");
        } else {
            if (anim.getRepeatingAnim().getTitle().equals("run right")) {
                anim.setRepeatingAnim("idle");
            } else if (anim.getRepeatingAnim().getTitle().equals("run left")) {
                anim.setRepeatingAnim("idle left");
            }
        }

        if (right) {
            moveRight(getStat("speed").getValue());
        }
        if (left) {
            moveLeft(getStat("speed").getValue());
        }
        if (up) {
            moveUp(getStat("speed").getValue());
        }
        if (down) {
            moveDown(getStat("speed").getValue());
        }


        Camera.x = getLocation().getWorldX() - Camera.width / 2;
        Camera.y = getLocation().getWorldY() - Camera.height / 2;

    }

    @Override
    public void die() {
        super.die();
        System.exit(0);
    }

    @Override
    public void render(DrawPoint dp, Graphics g) {
        super.render(dp, g);
    }

    @Override
    protected Animation getIdleAnim() {
        return new Animation("idle", idleRight);
    }

    public PlayerInventory getInventory() {
        return inventory;
    }

    public void setInventory(PlayerInventory inventory) {
        inventory.removeArmor();
        inventory.removeWeapon();
        if (inventory.getArmor() != null) {
            this.inventory.equipItem(inventory.getArmor());
        }
        if (inventory.getWeapon() != null) {
            this.inventory.equipItem(inventory.getWeapon());
        }

    }
}
