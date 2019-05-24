package com.kai.fp.objs.inanimate;

import com.kai.fp.core.Renderable;
import com.kai.fp.objs.GameObject;
import com.kai.fp.world.WorldLocation;

/**
 * @author Kai on May 15, 2019
 */
public abstract class InanimateObj extends GameObject implements Renderable {

    public InanimateObj(WorldLocation location) {
        super(location, 32, 32);
        setPhysical(true);
        anim.setFramesPerSecond(1);

    }


}
