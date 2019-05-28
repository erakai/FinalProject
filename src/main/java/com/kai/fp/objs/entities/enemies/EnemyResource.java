package com.kai.fp.objs.entities.enemies;

import com.kai.fp.objs.Projectile;
import com.kai.fp.objs.entities.StatManager;

import java.util.ArrayList;
import java.util.List;

public class EnemyResource {
    public StatManager stats;
    public Enemy.EnemyTier tier;
    public String id;
    public double rateOfFire = 1;
    public int width, height;
    public EnemyFire firemode;
    public List<Projectile> projectiles;

    public EnemyResource() {
        stats = new StatManager();
        projectiles = new ArrayList<>();
        stats.addStat("speed", "How fast the entity moves", 2);
        stats.addStat("max health", "The maximum health of the entity", 50);
        stats.addStat("health", "The current health of the entity", 50);
        stats.addStat("defense", "Reduces the amount of damage taken", 0);
        stats.addStat("damage", "Increases the amount of damage dealt", 0);
    }

    public StatManager getStats() {
        return stats;
    }

    public Enemy.EnemyTier getTier() {
        return tier;
    }

    public String getId() {
        return id;
    }

    public double getRateOfFire() {
        return rateOfFire;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "EnemyResource{" +
                "id='" + id + '\'' +
                ", tier=" + tier +
                ", rateOfFire=" + rateOfFire +
                ", width=" + width +
                ", height=" + height +
                ", stats=" + stats +
                '}';
    }
}
