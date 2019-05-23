package com.kai.fp.world;

import java.awt.image.BufferedImage;

/**
 * A class created by ResourceManager on program load that is used to create tiles.
 *
 * @author Kai on May 13, 2019
 */
public class TileResource {
    private BufferedImage image;
    private String type;
    private boolean walkable = true;

    public TileResource(BufferedImage image, String type) {
        this.image = image;
        this.type = type;
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getType() {
        return type;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    @Override
    public String toString() {
        return "TileResource{" +
                "image=" + image +
                ", type='" + type + '\'' +
                '}';
    }
}
