package com.kai.fp.objs.entities;

import com.kai.fp.core.Game;
import com.kai.fp.core.Updatable;
import com.kai.fp.objs.AnimationPlayer;
import com.kai.fp.objs.GameObject;
import com.kai.fp.world.WorldLocation;
import com.kai.fp.world.WorldTile;

/**
 * @author Kai on May 15, 2019
 */
public abstract class Entity extends GameObject implements Updatable {
    private StatManager stats;

    public Entity(WorldLocation location, int width, int height) {
        super(location, width, height);

        stats = new StatManager();
        stats.addStat("speed", "How fast the entity moves", 3);
        stats.addStat("max health", "The maximum health of the entity", 50);

        //TODO: Do I really want to do this?
        Game.getWorld().addObject(this);
    }

    public AnimationPlayer getAnim() {
        return anim;
    }

    public StatManager getStats() {
        return stats;
    }

    public StatManager.Stat getStat(String title) {
        return stats.getStat(title);
    }



    public void moveRight(int amu) {
        int newX = getScreenX() + getWidth() + getStat("speed").getValue();
        if (newX < 0) newX = -1 * WorldTile.WIDTH;
        WorldTile tile = Game.getWorld().getTile(newX/WorldTile.WIDTH, (getCenterY()+getHeight()/3)/WorldTile.HEIGHT);
        if (tile != null && tile.isWalkable()) {
            getLocation().moveRight(getStat("speed").getValue());
        }
    }

    public void moveLeft(int amu) {
        int newX = getScreenX() - getStat("speed").getValue();
        if (newX < 0) newX = -1 * WorldTile.WIDTH;
        WorldTile tile = Game.getWorld().getTile(newX/WorldTile.WIDTH, (getCenterY()+getHeight()/3)/WorldTile.HEIGHT);
        if (tile != null && tile.isWalkable()) {
            getLocation().moveLeft(getStat("speed").getValue());
        }
    }

    public void moveUp(int amu) {
        int newY = getScreenY() + (getHeight()/3) - getStat("speed").getValue();
        if (newY < 0) newY = -1 * WorldTile.HEIGHT;
        WorldTile tile = Game.getWorld().getTile(getCenterX()/WorldTile.WIDTH, newY/WorldTile.HEIGHT);
        if (tile != null && tile.isWalkable()) {
            getLocation().moveUp(getStat("speed").getValue());
        }
    }

    public void moveDown(int amu) {
        int newY = getScreenY() + getHeight() + getStat("speed").getValue();
        if (newY < 0) newY = -1 * WorldTile.HEIGHT;
        WorldTile tile = Game.getWorld().getTile(getCenterX()/WorldTile.WIDTH, newY/WorldTile.HEIGHT);
        if (tile != null && tile.isWalkable()) {
            getLocation().moveDown(getStat("speed").getValue());
        }
    }

    public void takeDamage(int amu) {

    }
}
