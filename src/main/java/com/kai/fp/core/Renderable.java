package com.kai.fp.core;

import com.kai.fp.util.DrawPoint;

import java.awt.*;

/**
 * Marks a class that is able to be rendered on the display.
 *
 * @author Kai on May 10, 2019
 */
public interface Renderable {
    void render(DrawPoint dp, Graphics g);
}
