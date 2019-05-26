package com.kai.fp.objs.entities.enemies;

import com.kai.fp.objs.Animation;
import com.kai.fp.objs.GameObject;
import com.kai.fp.objs.entities.Entity;
import com.kai.fp.world.WorldLocation;

public class Enemy extends Entity {
    public Enemy(WorldLocation location, int width, int height) {
        super(location, width, height);
    }

    @Override
    public void update(long delta) {

    }

    @Override
    protected Animation getIdleAnim() {
        return null;
    }
}
