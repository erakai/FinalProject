package com.kai.fp.core;

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

    private List<Entity> entities = new ArrayList<>();

    @Override
    public void update(long delta) {
        if (Game.getCurrentWorld().isEntitiesModified()) {
            this.entities = Game.getCurrentWorld().getEntities();
        }

        List<Entity> toRemove = new ArrayList<>();
        for (Entity e: entities) {
            if (e.distanceTo(getCenterX(), getCenterY()) < Math.sqrt(width*width + height*height)) {
                e.update(delta);
                if (e.isMarkedForRemoval()) toRemove.add(e);
            }
        }
        entities.removeAll(toRemove);
    }

    public static void render(Graphics g) {
        Game.getCurrentWorld().render(new DrawPoint(x, y), g);
    }

    public static int getCenterX() {
        return x + (width/2);
    }

    public static int getCenterY() {
        return y + (height/2);
    }
}
