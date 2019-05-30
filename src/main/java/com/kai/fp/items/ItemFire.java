package com.kai.fp.items;

import com.kai.fp.core.Game;
import com.kai.fp.items.Item;
import com.kai.fp.objs.Projectile;
import com.kai.fp.objs.entities.enemies.Enemy;
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
    },
    SHOTGUN_FIRE {
        @Override
        void fire(Weapon actor, WorldLocation target, Player owner) {
            for (int i = 0; i < 5; i++) {
                int tX = target.getWorldX() + ((Math.random() > 0.5) ? ((int)(Math.random() * 40)) : ((int)(-1 * (Math.random() * 40))));
                int tY = target.getWorldY() + ((Math.random() > 0.5) ? ((int)(Math.random() * 40)) : ((int)(-1 * (Math.random() * 40))));
                Game.getWorld().addObject(new Projectile(new WorldLocation(owner.getCenterX(), owner.getCenterY()), new WorldLocation(tX, tY), owner, actor.getProjectile(0)));
            }
        }
    },
    DUAL_FIRE {
        @Override
        void fire(Weapon actor, WorldLocation target, Player owner) {
            for (int i = 0; i < 2; i++) {
                int tX = target.getWorldX() + ((Math.random() > 0.5) ? ((int)(Math.random() * 65)) : ((int)(-1 * (Math.random() * 65))));
                int tY = target.getWorldY() + ((Math.random() > 0.5) ? ((int)(Math.random() * 65)) : ((int)(-1 * (Math.random() * 65))));
                Game.getWorld().addObject(new Projectile(new WorldLocation(owner.getCenterX(), owner.getCenterY()), new WorldLocation(tX, tY), owner, actor.getProjectile(i)));
            }
        }
    },
    RANDOM_FIRE {
        @Override
        void fire(Weapon actor, WorldLocation target, Player owner) {
            for (int i = 0; i < 10; i++) {
                int tX = owner.getScreenX() + ((Math.random() > 0.5) ? ((int)(Math.random() * 500)) : ((int)(-1 * (Math.random() * 500))));
                int tY = owner.getScreenY() + ((Math.random() > 0.5) ? ((int)(Math.random() * 500)) : ((int)(-1 * (Math.random() * 500))));
                Game.getWorld().addObject(new Projectile(new WorldLocation(owner.getCenterX(), owner.getCenterY()), new WorldLocation(tX, tY), owner, actor.getProjectiles().get((i % 2))));
            }
        }
    };

    abstract void fire(Weapon actor, WorldLocation target, Player owner);
}
