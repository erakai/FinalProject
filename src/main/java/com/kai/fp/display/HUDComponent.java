package com.kai.fp.display;

import com.kai.fp.core.Renderable;
import com.kai.fp.util.DrawPoint;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Kai on May 21, 2019
 */
public abstract class HUDComponent implements Renderable {
    private BufferedImage image;
    private int x, y, width, height;
    private boolean onScreen = false;


    public HUDComponent(int x, int y, BufferedImage image, int width, int height) {
        this.x=x;
        this.y=y;
        this.image=image;
        this.width=width;
        this.height=height;
    }

    @Override
    public void render(DrawPoint dp, Graphics g) {
        g.drawImage(image, dp.getX(), dp.getY(), null);
        x = dp.getX();
        y = dp.getY();
    }

    public boolean checkCollisionWithMouse(int otherX, int otherY) {
        return (otherX > x && otherX < x + width
                && otherY > y && otherY < y + height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setOnScreen(boolean onScreen) {
        this.onScreen = onScreen;
    }

    public boolean isOnScreen() {
        return onScreen;
    }
}
