package com.kai.fp.items;

import com.kai.fp.objs.entities.Projectile;
import com.kai.fp.objs.entities.player.Player;

/**
 * @author Kai on May 20, 2019
 */
public enum ItemFire {
    STRAIGHT_FIRE {
        @Override
        void fire(Item actor, int targetX, int targetY, Player owner) {

        }
    };


    abstract void fire(Item actor, int targetX, int targetY, Player owner);
}
