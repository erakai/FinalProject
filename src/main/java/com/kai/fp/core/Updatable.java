package com.kai.fp.core;

/**
 * Marks a class that can and should be updated every frame.
 *
 * @author Kai on May 10, 2019
 */
public interface Updatable {
    void update(long delta);
}
