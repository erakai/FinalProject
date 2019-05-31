package com.kai.fp.objs.entities.enemies.bosses;

import com.kai.fp.objs.Animation;
import com.kai.fp.objs.entities.enemies.Enemy;
import com.kai.fp.objs.entities.enemies.EnemyResource;
import com.kai.fp.objs.entities.enemies.ProjectileEnemy;
import com.kai.fp.util.Globals;
import com.kai.fp.util.MTimer;
import com.kai.fp.world.WorldLocation;

/**
 * @author Kai on May 29, 2019
 */
public abstract class Boss extends Enemy {

    private MTimer timer;

    boolean immune = false;
    int stage, maxStages;
    int currentSpawnTick, maxSpawnTick;
    double spawnRate = 2.5;


    public Boss(WorldLocation location, EnemyResource base, int maxStages) {
        super(location, base);

        timer = new MTimer();
        this.maxStages = maxStages;
        maxSpawnTick = (int)(spawnRate * Globals.FRAMES_PER_SECOND);
        currentSpawnTick = maxSpawnTick;
        stage = 1;
    }

    public abstract void createProjectile();
    public abstract void checkTransitions();
    public abstract void chase();
    public abstract void spawnMinion();

    @Override
    public void move() {
        checkTransitions();
        chase();
    }

    void nextStage() {
        transitionToStage(stage + 1);
    }

    void transitionToStage(int newStage) {
        timer.markTime("stage " + newStage);
        this.stage = newStage;
    }

    boolean timeTransition(String markedTimeKey, int necessaryLength) {
        return (timer.getSecondsSinceMarkedTime(markedTimeKey) >= necessaryLength);
    }

    boolean healthTransition(double percentageRequired) {
        return (getStat("health").getValue() / (double) getStat("max health").getValue() <= percentageRequired);
    }

    @Override
    public void takeDamage(int amu) {
        if (!immune) {
            super.takeDamage(amu);
        }
    }

    @Override
    public void attack() {
        super.attack();
        createProjectile();
    }

    void manageSpawning() {
        currentSpawnTick++;
        if (currentSpawnTick > maxSpawnTick) {
            currentSpawnTick = 0;
            spawnMinion();
        }
    }

    public void setSpawnRate(double spawnRate) {
        this.spawnRate = spawnRate;
        maxSpawnTick = (int)(spawnRate * Globals.FRAMES_PER_SECOND);
    }
}
