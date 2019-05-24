package com.kai.fp.objs.inanimate;

import com.kai.fp.core.Game;
import com.kai.fp.core.Updatable;
import com.kai.fp.objs.Animation;
import com.kai.fp.world.WorldLocation;

public class Portal extends InanimateObj implements Updatable {

    public Portal(WorldLocation location) {
        super(location);
        setPhysical(false);
        Game.addUpdatable(this);
    }

    @Override
    public void update(long delta) {

    }

    @Override
    protected Animation getIdleAnim() {
        return null;
    }
}
