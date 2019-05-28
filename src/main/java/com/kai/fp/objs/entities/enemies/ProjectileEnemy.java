package com.kai.fp.objs.entities.enemies;

import com.kai.fp.core.Game;
import com.kai.fp.objs.Projectile;
import com.kai.fp.world.WorldLocation;

public abstract class ProjectileEnemy extends Enemy {

    public ProjectileEnemy(WorldLocation location, EnemyResource base) {
        super(location, base);
    }

    @Override
    public void attack() {
        getFire().fire(this, Game.getPlayer());
    }
}
