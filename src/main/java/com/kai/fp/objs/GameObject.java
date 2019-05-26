package com.kai.fp.objs;

import com.kai.fp.core.Renderable;
import com.kai.fp.util.DrawPoint;
import com.kai.fp.world.WorldLocation;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Kai on May 10, 2019
 */
public abstract class GameObject implements Renderable {
    private WorldLocation location;
    private int width, height;
    protected AnimationPlayer anim = new AnimationPlayer();
    private BufferedImage loneImage = null;
    private boolean physical = true;
    private boolean markedForRemoval = false;

    public GameObject(WorldLocation location, int width, int height) {
        this.location = location;
        this.width = width;
        this.height = height;

        Animation idleAnim = getIdleAnim();
        if (idleAnim.getFrameCount() == 1) {
            loneImage = idleAnim.getFrame(0);
        } else {
            idleAnim.setTitle("idle");
            anim.addAnim(idleAnim);
            anim.setRepeatingAnim("idle");
        }

        addAnims();
    }

    @Override
    public void render(DrawPoint dp, Graphics g) {
        if (loneImage == null) {
            g.drawImage(anim.nextFrame(), dp.getX(), dp.getY(), null);
        } else {
            g.drawImage(loneImage, dp.getX(), dp.getY(), null);
        }
    }

    public boolean checkCollision(GameObject otherObject) {
        return ((otherObject.getScreenX() < getScreenX()+getWidth()) &&
                (otherObject.getScreenX()+otherObject.getWidth() > getScreenX()) &&
                (otherObject.getScreenY() < getScreenY()+getHeight()) &&
                (otherObject.getScreenY()+otherObject.getHeight() > getScreenY()));
    }

    public boolean checkCollisionWithMouse(int otherX, int otherY) {
        return (otherX > getScreenY() && otherX < getScreenX() + getWidth()
                && otherY > getScreenY() && otherY < getScreenY() + getHeight());
    }

    public double distanceTo (int tX, int tY) {
        double a = Math.abs( (getCenterX()) - tX);
        double b = Math.abs( (getCenterY()) - tY);
        return(Math.sqrt((a*a) + (b*b)));
    }

    public double distanceTo(GameObject otherObject) {
        return distanceTo(otherObject.getCenterX(), otherObject.getCenterY());
    }

    public void setLocation(WorldLocation location) { this.location = location; }
    public WorldLocation getLocation() { return location; }
    public int getScreenX() { return getLocation().getWorldX(); }
    public int getScreenY() { return getLocation().getWorldY(); }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getCenterX() { return (getScreenX() + getWidth()/2); }
    public int getCenterY() { return (getScreenY() + getHeight()/2); }

    public boolean isPhysical() {
        return physical;
    }

    public void setPhysical(boolean physical) {
        this.physical = physical;
    }

    public void setLoneImage(BufferedImage loneImage) {
        this.loneImage = loneImage;
    }

    public BufferedImage getLoneImage() {
        return loneImage;
    }

    protected abstract Animation getIdleAnim();
    protected void addAnims() {}

    public void die() {
        markedForRemoval = true;
    }

    public boolean isMarkedForRemoval() {
        return markedForRemoval;
    }
}
