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
        public void fire(Enemy actor, Player target) {
            Game.addToWorldQueue(new Projectile(new WorldLocation(actor.getCenterX(), actor.getCenterY()), new WorldLocation(target.getLocation()), actor, actor.getProjectiles().get(0)));
        }
    },
    CIRCULAR_FIRE {
        @Override
        public void fire(Enemy actor, Player target) {
            Game.addToWorldQueue(new Projectile(new WorldLocation(actor.getCenterX()-actor.getWidth(), actor.getCenterY()-actor.getHeight()), new WorldLocation(target.getLocation()), actor, actor.getProjectiles().get(0)));
            Game.addToWorldQueue(new Projectile(new WorldLocation(actor.getCenterX()+actor.getWidth(), actor.getCenterY()+actor.getHeight()), new WorldLocation(target.getLocation()), actor, actor.getProjectiles().get(0)));
            Game.addToWorldQueue(new Projectile(new WorldLocation(actor.getCenterX()-actor.getWidth(), actor.getCenterY()+actor.getHeight()), new WorldLocation(target.getLocation()), actor, actor.getProjectiles().get(0)));
            Game.addToWorldQueue(new Projectile(new WorldLocation(actor.getCenterX()+actor.getWidth(), actor.getCenterY()-actor.getHeight()), new WorldLocation(target.getLocation()), actor, actor.getProjectiles().get(0)));
            Game.addToWorldQueue(new Projectile(new WorldLocation(actor.getCenterX(), actor.getCenterY()-actor.getHeight()), new WorldLocation(target.getLocation()), actor, actor.getProjectiles().get(0)));
            Game.addToWorldQueue(new Projectile(new WorldLocation(actor.getCenterX()-actor.getWidth(), actor.getCenterY()), new WorldLocation(target.getLocation()), actor, actor.getProjectiles().get(0)));
            Game.addToWorldQueue(new Projectile(new WorldLocation(actor.getCenterX()+actor.getWidth(), actor.getCenterY()), new WorldLocation(target.getLocation()), actor, actor.getProjectiles().get(0)));
            Game.addToWorldQueue(new Projectile(new WorldLocation(actor.getCenterX(), actor.getCenterY()+actor.getHeight()), new WorldLocation(target.getLocation()), actor, actor.getProjectiles().get(0)));
        }
    },
    DUAL_FIRE {
        @Override
        public void fire(Enemy actor, Player target) {
            for (int i = 0; i < 2; i++) {
                int tX = target.getScreenX() + ((Math.random() > 0.5) ? ((int)(Math.random() * 60)) : ((int)(-1 * (Math.random() * 60))));
                int tY = target.getScreenY() + ((Math.random() > 0.5) ? ((int)(Math.random() * 60)) : ((int)(-1 * (Math.random() * 60))));
                Game.getWorld().addObject(new Projectile(new WorldLocation(actor.getCenterX(), actor.getCenterY()), new WorldLocation(tX, tY), actor, actor.getProjectiles().get(i)));
            }
        }
    },
    RANDOM_FIRE{
        @Override
        public void fire(Enemy actor, Player target) {
            for (int i = 0; i < 12; i++) {
                int tX = actor.getScreenX() + ((Math.random() > 0.5) ? ((int)(Math.random() * 500)) : ((int)(-1 * (Math.random() * 500))));
                int tY = actor.getScreenY() + ((Math.random() > 0.5) ? ((int)(Math.random() * 500)) : ((int)(-1 * (Math.random() * 500))));
                Game.getWorld().addObject(new Projectile(new WorldLocation(actor.getCenterX(), actor.getCenterY()), new WorldLocation(tX, tY), actor, actor.getProjectiles().get(2)));
            }
        }
    },
    SECONDARY_FIRE {
        @Override
        public void fire(Enemy actor, Player target) {
            Game.addToWorldQueue(new Projectile(new WorldLocation(actor.getCenterX(), actor.getCenterY()), new WorldLocation(target.getLocation()), actor, actor.getProjectiles().get(1)));
        }
    };



    public abstract void fire(Enemy actor, Player target);
}
