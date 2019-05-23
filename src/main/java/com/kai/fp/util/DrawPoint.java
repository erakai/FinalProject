package com.kai.fp.util;

import java.io.Serializable;

/**
 * A simple javabean representing a point on the display.
 *
 * @author Kai on May 10, 2019
 */
public class DrawPoint implements Serializable {
    public int x, y;
    private int convertedX, convertedY;

    public DrawPoint(int x, int y) {
        this.x = x;
        this.y = y;
        convert();
    }

    public DrawPoint(DrawPoint dp) {
        this(dp.getX(), dp.getY());
    }

    private void convert() {
        this.convertedX = x;
        this.convertedY = y;
    }

    public DrawPoint() {
        this(0,0);
    }

    public int getX() {
        return convertedX;
    }

    public void setX(int x) {
        this.x = x;
        convert();
    }

    public int getY() {
        return convertedY;
    }

    public void setY(int y) {
        this.y = y;
        convert();
    }

    public int xConversion(int ogX) {
        return ogX;
    }

    public int yConversion(int ogY) {
        return ogY;
    }
}
