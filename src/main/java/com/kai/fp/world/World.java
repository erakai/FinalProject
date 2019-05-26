package com.kai.fp.world;

import com.kai.fp.core.Camera;
import com.kai.fp.core.Renderable;
import com.kai.fp.display.Screen;
import com.kai.fp.objs.GameObject;
import com.kai.fp.objs.Projectile;
import com.kai.fp.util.DrawPoint;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * A world object representing a game world.
 *
 * @author Kai on May 13, 2019
 */
public class World implements Renderable {
    private WorldTile[][] worldTiles;
    private List<GameObject> gameObjects;
    private boolean objectsModified = true;

    public World(String worldName) {
        worldTiles = WorldLoader.load(worldName);
        gameObjects = new ArrayList<>();
    }

    @Override
    public void render(DrawPoint dp, Graphics g) {

        for (int h = 0; h < worldTiles.length; h++)  {
            for (int r = 0; r < worldTiles[h].length; r++) {
                if (worldTiles[h][r] != null) {
                    int x = dp.x + (r * WorldTile.WIDTH);
                    int y = dp.y + (h * WorldTile.HEIGHT);

                    WorldTile tile = worldTiles[h][r];
                    if (tile.distanceTo(Camera.getCenterX(), Camera.getCenterY()) <  Math.sqrt(Camera.width*Camera.width + Camera.height*Camera.height)/2) {
                        tile.render(new DrawPoint(tile.getScreenX() - dp.getX(), tile.getScreenY() - dp.getY()), g);
                    }
                }
            }
        }

        for (GameObject e: gameObjects) {
            if (e.distanceTo(Camera.getCenterX(), Camera.getCenterY()) <  Math.sqrt(Camera.width*Camera.width + Camera.height*Camera.height)/2) {
                //TODO: I desperately need display layers.
                e.render(new DrawPoint(e.getScreenX() - dp.getX(), e.getScreenY() - dp.getY()), g);
            }
        }

    }

    public WorldTile getTile(int x, int y) {
        try {
            return worldTiles[y][x];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null; //THERE IS NO GOD
        }
    }

    public void addObject(GameObject e) {
        gameObjects.add(e);
        objectsModified = true;
    }

    public void removeObject(GameObject e) {
        gameObjects.remove(e);
        objectsModified = true;
    }

    public List<GameObject> getObjects() {
        objectsModified = false;
        return gameObjects;
    }

    public boolean isObjectsModified() {
        return objectsModified;
    }

}
