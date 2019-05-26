package com.kai.fp.items;

import com.kai.fp.core.Game;
import com.kai.fp.items.Item;
import com.kai.fp.objs.Projectile;
import com.kai.fp.objs.entities.player.Player;
import com.kai.fp.world.WorldLocation;

/**
 * @author Kai on May 20, 2019
 */
public enum ItemFire {
    STRAIGHT_FIRE {
        @Override
        void fire(Weapon actor, WorldLocation target, Player owner) {
            Game.getWorld().addObject(new Projectile(new WorldLocation(owner.getCenterX(), owner.getCenterY()), target, owner, actor.getProjectile(0) ));
        }
    };


    abstract void fire(Weapon actor, WorldLocation target, Player owner);
}
