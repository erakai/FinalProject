package com.kai.fp.world;

/**
 * @author Kai on May 10, 2019
 */
public class WorldLocation {

    private int worldX, worldY;

    public WorldLocation(int worldTileX, int worldTileY) {
        this.worldX = worldTileX;
        this.worldY = worldTileY;
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public void moveLeft() {
        moveLeft(1);
    }

    public void moveUp() {
        moveUp(1);
    }

    public void moveRight() {
        moveRight(1);
    }

    public void moveDown() {
        moveDown(1);
    }

    public void moveLeft(int amu) {
        worldX-=amu;
    }

    public void moveUp(int amu) {
        worldY-=amu;
    }

    public void moveRight(int amu) {
        worldX+=amu;
    }

    public void moveDown(int amu) {
        worldY+=amu;
    }
}
