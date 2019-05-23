package com.kai.fp.world;

import com.kai.fp.core.Renderable;
import com.kai.fp.objs.Animation;
import com.kai.fp.objs.GameObject;
import com.kai.fp.util.DrawPoint;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Kai on May 13, 2019
 */
public class WorldTile extends GameObject implements Renderable {
    public static final int WIDTH = 32, HEIGHT = 32;

    private BufferedImage tileImage;
    private GameObject occupying;
    private boolean walkable;

    private final String type;

    public WorldTile(WorldLocation location, TileResource resource) {
        super(location, WIDTH, HEIGHT);
        tileImage = resource.getImage();
        type = resource.getType();
        walkable = resource.isWalkable();
    }

    @Override
    public void render(DrawPoint dp, Graphics g) {
        g.drawImage(tileImage, dp.getX(), dp.getY(), null);
        if (occupying != null) {
            occupying.render(dp, g);
        }
    }

    @Override
    protected Animation getIdleAnim() {
        return new Animation("idle", new BufferedImage[] {tileImage});
    }

    public String getType() {
        return type;
    }

    public GameObject getOccupying() {
        return occupying;
    }

    public void setOccupying(GameObject occupying) {
        this.occupying = occupying;
        this.occupying.setLocation(getLocation());
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    @Override
    public String toString() {
        return "WorldTile{" +
                "occupying=" + occupying +
                ", type='" + type + '\'' +
                '}';
    }
}
