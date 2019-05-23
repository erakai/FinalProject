package com.kai.fp.display;

import com.kai.fp.core.Camera;
import com.kai.fp.core.Game;
import com.kai.fp.core.InputHandler;
import com.kai.fp.core.Updatable;
import com.kai.fp.util.DrawPoint;
import com.kai.fp.util.Globals;

import javax.swing.*;
import java.awt.*;

/**
 * @author Kai on May 10, 2019
 */
public class Screen extends JPanel implements Updatable {
    private InputHandler inputHandler;
    private static HUD hud;

    public Screen() {
        setPreferredSize(new Dimension(Globals.DISPLAY_WIDTH, Globals.DISPLAY_HEIGHT));
        setBackground(Color.black);

        inputHandler = new InputHandler();
        inputHandler.createMap(this);
        hud = new HUD();
    }

    @Override
    public void update(long delta) {
        repaint();
        inputHandler.update(delta);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        switch (Game.gamestate) {
            case RUNNING:
                Camera.render(g);
                hud.render(null, g);
                break;
        }
    }

    public static HUD getHud() {
        return hud;
    }
}
