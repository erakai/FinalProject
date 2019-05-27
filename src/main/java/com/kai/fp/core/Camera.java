package com.kai.fp.core;

import com.kai.fp.objs.GameObject;
import com.kai.fp.objs.entities.Entity;
import com.kai.fp.util.DrawPoint;
import com.kai.fp.util.Globals;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kai on May 10, 2019
 */
public class Camera implements Updatable {

    public static int x = 0, y = 0;
    public static int width = Globals.DISPLAY_WIDTH, height = Globals.DISPLAY_HEIGHT;

    private List<GameObject> gameObjects = new ArrayList<>();

    @Override
    public void update(long delta) {
        if (Game.getWorld() != null) {
            if (Game.getWorld().isObjectsModified()) {
                this.gameObjects = Game.getWorld().getObjects();
            }
        }

        List<GameObject> toRemove = new ArrayList<>();
        for (GameObject e: gameObjects) {
            if (e.distanceTo(getCenterX(), getCenterY()) < Math.sqrt(width*width + height*height)/2) {
                if (e instanceof Updatable) {
                    ((Updatable) e).update(delta);
                }
                if (e.isMarkedForRemoval()) toRemove.add(e);
            }
        }
        gameObjects.removeAll(toRemove);
    }

    public static void render(Graphics g) {
        if (Game.getWorld() != null) {
            Game.getWorld().render(new DrawPoint(x, y), g);
        }
    }

    public static int getCenterX() {
        return x + (width/2);
    }

    public static int getCenterY() {
        return y + (height/2);
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }
}
