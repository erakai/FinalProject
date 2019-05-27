package com.kai.fp.objs.entities.enemies;

import com.kai.fp.core.Camera;
import com.kai.fp.core.Game;
import com.kai.fp.items.ItemLoader;
import com.kai.fp.items.Rarity;
import com.kai.fp.objs.Animation;
import com.kai.fp.objs.GameObject;
import com.kai.fp.objs.Projectile;
import com.kai.fp.objs.entities.Entity;
import com.kai.fp.objs.entities.StatManager;
import com.kai.fp.objs.inanimate.LootChest;
import com.kai.fp.util.DrawPoint;
import com.kai.fp.util.Globals;
import com.kai.fp.world.WorldLocation;
import com.kai.fp.world.WorldTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//THIS IS MELEE ENEMY
public abstract class Enemy extends Entity {
    public enum EnemyTier {REGULAR, ELITE, BOSS}

    boolean active = false;
    private EnemyTier tier;
    private String id;
    private double rateOfFire = 1;
    private List<Projectile> projectiles;
    int attackTick, maxAttackTick;

    public Enemy(WorldLocation location, int width, int height) {
        super(location, width, height);
        Game.getWorld().removeObject(this);
    }

    public Enemy(WorldLocation location, EnemyResource base) {
        super(location, base.getWidth(), base.getHeight());
        projectiles = new ArrayList<>();
        base.projectiles.forEach((p) -> projectiles.add(p));
        active = true;
        tier = base.getTier();
        id = base.getId();
        for (StatManager.Stat s : base.getStats().getStats().values()) {
            getStat(s.getName()).baseValue = s.baseValue;
        }
        rateOfFire = base.getRateOfFire();

        attackTick = 10000;
        maxAttackTick = (int)(Globals.FRAMES_PER_SECOND / rateOfFire);
    }

    @Override
    public void render(DrawPoint dp, Graphics g) {
        if (active) {
            super.render(dp, g);
            g.setColor(Color.RED);
            g.fillRect(getScreenX() - Camera.x, getScreenY() - 10 - Camera.y,
                    (int) (((getStat("health").getValue() / (double) (getStat("max health").getValue()))) * getWidth()), 5);
        }
    }

    @Override
    public void update(long delta) {
        super.update(delta);
        if (active) {
            move();
            attackTick++;
            if (attackTick > maxAttackTick) {
                attack();
                attackTick = 0;
            }
        }
    }

    public abstract void move();

    public void attack() {
        if (checkCollision(Game.getPlayer())) {
            Game.getPlayer().takeDamage(getStat("damage").getValue());
        }
    }

    @Override
    public void die() {
        super.die();
        //TODO: have correct calculations later
//        WorldTile tile = Game.getWorld().getTile(getCenterX()/WorldTile.WIDTH, (getCenterY())/WorldTile.HEIGHT);
//        LootChest chest = new LootChest(new WorldLocation(getCenterX()/WorldTile.WIDTH, getCenterY()/WorldTile.HEIGHT));
//        if (tier == EnemyTier.REGULAR) {
//            chest.addItem(ItemLoader.getRandomItem(Rarity.COMMON));
//        } else if (tier == EnemyTier.ELITE) {
//            chest.addItem(ItemLoader.getRandomItem(Rarity.RARE)) ;
//        } else if (tier == EnemyTier.BOSS) {
//            chest.addItem(ItemLoader.getRandomItem(Rarity.GLYPHIC));
//        }
//        tile.setOccupying(chest);
    }

    public EnemyTier getTier() {
        return tier;
    }

    public double getRateofFire() {
        return rateOfFire;
    }

    public void setRateofFire(double rateofFire) {
        this.rateOfFire = rateofFire;
    }

    public void setTier(EnemyTier tier) {
        this.tier = tier;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    @Override
    public String toString() {
        return "Enemy{" +
                "tier=" + tier +
                ", id='" + id + '\'' +
                ", rateOfFire=" + rateOfFire +
                ", stats=" + getStats() +
                '}';
    }
}
