package com.kai.fp.core;

import com.kai.fp.display.Screen;
import com.kai.fp.objs.entities.player.Player;
import com.kai.fp.util.DrawPoint;
import com.kai.fp.util.Globals;
import com.kai.fp.util.ResourceManager;
import com.kai.fp.world.World;
import com.kai.fp.world.WorldLocation;

/**
 * The main game class with a game loop that handles and calls everything.
 * Implements Runnable because it's cool.
 *
 * @author Kai on May 10, 2019
 */
public class Game implements Runnable, Updatable {

    public enum State{START, RUNNING, LOADING, END}
    public static State gamestate = State.START;

    private Screen display;
    private static World currentWorld;
    private static Camera camera;
    private static Player player;

    private long previousFrameTime = -1;

    public Game(Screen display) {
        this.display = display;

        new ResourceManager();
        camera = new Camera();
        currentWorld = new World("testworld");
        player = new Player(new WorldLocation(0, 0));

        gamestate = State.RUNNING;
    }

    public void run() {

        while(true) {

            long currentTime = System.currentTimeMillis();
            long delta = (previousFrameTime - currentTime);
            update(delta);
            previousFrameTime = currentTime;

            try {
                Thread.sleep(1000/ Globals.FRAMES_PER_SECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void update(long delta) {
        camera.update(delta);
        display.update(delta);
    }

    public static World getCurrentWorld() {
        return currentWorld;
    }

    public static Player getPlayer() {
        return player;
    }
}
