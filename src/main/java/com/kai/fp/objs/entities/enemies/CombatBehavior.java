package com.kai.fp.objs.entities.enemies;

import com.kai.fp.objs.entities.Entity;

public enum CombatBehavior {
    STRAIGHT_CHASE {
        public void act(Enemy actor, Entity target) {
            if (actor.distanceTo(target) > actor.getWidth()/2) {
                final int variance = 8;

                int targetX = target.getCenterX() + ((int)(Math.random() * variance * 2 - variance));
                int targetY = target.getCenterY() + ((int)(Math.random() * variance * 2 - variance));

                double deltaX = targetX - actor.getCenterX();
                double deltaY = targetY - actor.getCenterY();
                double direction = Math.atan2(deltaY, deltaX);

                int xChange = (int)(actor.getStat("speed").getValue() * Math.cos(direction));
                int yChange = (int)(actor.getStat("speed").getValue() * Math.sin(direction));

                if (xChange>0) {
                    actor.moveRight(xChange);
                } else if (xChange<0) {
                    actor.moveLeft(xChange);
                }
                if (yChange > 0) {
                    actor.moveDown(yChange);
                } else if (yChange <0) {
                    actor.moveUp(yChange);
                }
            }
        }
    };

    public abstract void act(Enemy actor, Entity target);
}
