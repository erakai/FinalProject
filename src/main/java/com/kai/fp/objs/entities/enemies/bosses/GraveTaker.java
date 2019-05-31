package com.kai.fp.objs.entities.enemies.bosses;

import com.kai.fp.core.Game;
import com.kai.fp.objs.Animation;
import com.kai.fp.objs.entities.enemies.EnemyFire;
import com.kai.fp.objs.entities.enemies.EnemyLoader;
import com.kai.fp.objs.entities.enemies.EnemyResource;
import com.kai.fp.objs.entities.enemies.w3.CandleMan;
import com.kai.fp.objs.entities.enemies.w3.Waxy;
import com.kai.fp.util.ResourceManager;
import com.kai.fp.world.WorldLocation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GraveTaker extends Boss {
    private static BufferedImage[] frames = {
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 128, 160, 32, 32).getScaledInstance(64,64, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 160, 160, 32, 32).getScaledInstance(64,64, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 192, 160, 32, 32).getScaledInstance(64,64, Image.SCALE_FAST)),
            ResourceManager.toBufferedImage(ResourceManager.splice(ResourceManager.getSprite("enemies"), 224, 160, 32, 32).getScaledInstance(64,64, Image.SCALE_FAST))
    };
    private static int[][] spawnLocations = {
            {48*32, 42*32},
            {48*32, 58*32},
            {59*32, 42*32},
            {59*32, 58*32},
            {70*32, 42*32},
            {70*32, 58*32}
    };
    private List<CandleMan> candlemen;

    /*
    1. shoot player
    2. spawn candlemen shoot player
    3. rings + randomly spawning skeletons
    4. big bullets + rings + candlemen
    5. big bullets + candlemen + rings + firing + randomly spawning skeletons
     */

    public GraveTaker(WorldLocation location) {
        super(location, EnemyLoader.getEnemyResource("gravetaker"), 6);
        setSpawnRate(3);
        candlemen = new ArrayList<>();
    }

    @Override
    public void createProjectile() {
        switch(stage) {
            case 1:
                getFire().fire(this, Game.getPlayer());
                break;
            case 2:
                getFire().fire(this, Game.getPlayer());
                break;
            case 3:
                EnemyFire.RANDOM_FIRE.fire(this, Game.getPlayer());
                break;
            case 4:
                EnemyFire.RANDOM_FIRE.fire(this, Game.getPlayer());
                EnemyFire.SECONDARY_FIRE.fire(this, Game.getPlayer());
                break;
            case 5:
                if (Math.random() > 0.8) {
                    EnemyFire.SECONDARY_FIRE.fire(this, Game.getPlayer());
                } else if (Math.random() > 0.4) {
                    EnemyFire.RANDOM_FIRE.fire(this, Game.getPlayer());
                }

                if (Math.random() > 0.4) {
                    getFire().fire(this, Game.getPlayer());
                }
                break;
        }
    }

    @Override
    public void checkTransitions() {
        int startingStage = stage;
        switch (startingStage) {
            case 1:
                if (healthTransition(0.80)) {
                    nextStage();
                    lightCandles();
                }
                break;
            case 2:
                if (healthTransition(0.60)) {
                    nextStage();
                    setRateofFire(5);
                }
                break;
            case 3:
                if (healthTransition(0.40)) {
                    setRateofFire(7);
                    nextStage();
                    lightCandles();
                }
                break;
            case 4:
                if (healthTransition(0.15)) {
                    nextStage();
                    lightCandles();
                }
                break;
        }
    }

    @Override
    public void chase() {
        switch(stage) {
            case 3:
                manageSpawning();
        }
    }

    private void lightCandles() {
        for (int i = 0; i < candlemen.size(); i++) {
            if (!candlemen.get(i).isMarkedForRemoval()) candlemen.get(i).die();
        }
        candlemen.clear();
        for (int[] pair: spawnLocations) {
            WorldLocation l = new WorldLocation(pair[0], pair[1]);
            CandleMan e = new CandleMan(l);
            Game.addToWorldQueue(e);
            candlemen.add(e);
        }
    }

    @Override
    public void spawnMinion() {
        int tx = (int)(Math.random() * 896) + 1504;
        int ty = (int)(Math.random() * 640) + 1312;
        Game.addToWorldQueue(new Waxy(new WorldLocation(tx, ty)));
    }

    @Override
    protected Animation getIdleAnim() {
        return new Animation("idle", frames);
    }
}
