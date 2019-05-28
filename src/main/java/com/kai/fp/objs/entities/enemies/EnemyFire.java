package com.kai.fp.objs.entities.enemies;

import com.kai.fp.core.Game;
import com.kai.fp.items.Weapon;
import com.kai.fp.objs.Projectile;
import com.kai.fp.objs.entities.player.Player;
import com.kai.fp.world.WorldLocation;

/**
 * @author Kai on May 28, 2019
 */
public enum EnemyFire {
    STRAIGHT_SINGLE_FIRE {
        @Override
        void fire(Enemy actor, Player target) {
            Game.addToWorldQueue(new Projectile(new WorldLocation(actor.getCenterX(), actor.getCenterY()), new WorldLocation(target.getLocation()), actor, actor.getProjectiles().get(0)));
        }
    },
    CIRCULAR_FIRE {
        @Override
        void fire(Enemy actor, Player target) {
            Game.addToWorldQueue(new Projectile(new WorldLocation(actor.getCenterX()-actor.getWidth()*3/2, actor.getCenterY()-actor.getHeight()*3/2), new WorldLocation(target.getLocation()), actor, actor.getProjectiles().get(0)));
            Game.addToWorldQueue(new Projectile(new WorldLocation(actor.getCenterX()+actor.getWidth()*3/2, actor.getCenterY()+actor.getHeight()*3/2), new WorldLocation(target.getLocation()), actor, actor.getProjectiles().get(0)));
            Game.addToWorldQueue(new Projectile(new WorldLocation(actor.getCenterX()-actor.getWidth()*3/2, actor.getCenterY()+actor.getHeight()*3/2), new WorldLocation(target.getLocation()), actor, actor.getProjectiles().get(0)));
            Game.addToWorldQueue(new Projectile(new WorldLocation(actor.getCenterX()+actor.getWidth()*3/2, actor.getCenterY()-actor.getHeight()*3/2), new WorldLocation(target.getLocation()), actor, actor.getProjectiles().get(0)));
            Game.addToWorldQueue(new Projectile(new WorldLocation(actor.getCenterX(), actor.getCenterY()-actor.getHeight()*3/2), new WorldLocation(target.getLocation()), actor, actor.getProjectiles().get(0)));
            Game.addToWorldQueue(new Projectile(new WorldLocation(actor.getCenterX()-actor.getWidth()*3/2, actor.getCenterY()), new WorldLocation(target.getLocation()), actor, actor.getProjectiles().get(0)));
            Game.addToWorldQueue(new Projectile(new WorldLocation(actor.getCenterX()+actor.getWidth()*3/2, actor.getCenterY()), new WorldLocation(target.getLocation()), actor, actor.getProjectiles().get(0)));
            Game.addToWorldQueue(new Projectile(new WorldLocation(actor.getCenterX(), actor.getCenterY()+actor.getHeight()*3/2), new WorldLocation(target.getLocation()), actor, actor.getProjectiles().get(0)));
        }
    };



    abstract void fire(Enemy actor, Player target);
}
