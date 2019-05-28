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


    @Override
    public void update(long delta) {
        //TODO: Garbage way to do this but im too lazy to do it some other way.
        List<GameObject> gameObjects = Game.getWorld().getObjects();

        List<GameObject> toRemove = new ArrayList<>();
        for (int i = 0; i < gameObjects.size() ;i++) {
            GameObject e = gameObjects.get(i);

            if (e.distanceTo(getCenterX(), getCenterY()) < Math.sqrt(width*width + height*height)/5 * 2) {
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

}
