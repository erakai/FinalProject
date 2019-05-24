package com.kai.fp.display;

import com.kai.fp.core.*;
import com.kai.fp.util.DrawPoint;
import com.kai.fp.util.Globals;
import com.kai.fp.util.MFont;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kai on May 10, 2019
 */
public class Screen extends JPanel implements Updatable {
    private InputHandler inputHandler;
    private static HUD hud;

    private static Map<DrawPoint, Renderable> renderOnTop;

    public Screen() {
        setPreferredSize(new Dimension(Globals.DISPLAY_WIDTH, Globals.DISPLAY_HEIGHT));
        setBackground(Color.black);

        inputHandler = new InputHandler();
        inputHandler.createMap(this);
        addMouseListener(inputHandler);
        hud = new HUD();

        renderOnTop = new HashMap<>();
        //don't remove this or there is a 2 second freeze upon hovering over an item for the first time
        getFontMetrics(new MFont(1.0));
    }

    @Override
    public void update(long delta) {
        repaint();
        inputHandler.update(delta);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = (int)getLocationOnScreen().getX();
        int y = (int)getLocationOnScreen().getY();
        int currentMouseX = (int) (MouseInfo.getPointerInfo().getLocation().getX() - x);
        int currentMouseY = (int) (MouseInfo.getPointerInfo().getLocation().getY() - y);
        inputHandler.mouseExists(currentMouseX, currentMouseY);

        switch (Game.gamestate) {
            case RUNNING:
                Camera.render(g);
                hud.render(null, g);
                break;
        }

        for (DrawPoint dp: renderOnTop.keySet()) {
            renderOnTop.get(dp).render(dp, g);
        }

        renderOnTop.clear();

    }

    public static HUD getHud() {
        return hud;
    }

    public static void addRenderOnTop(DrawPoint dp, Renderable r) {
        renderOnTop.put(dp, r);
    }

}
