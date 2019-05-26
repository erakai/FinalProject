package com.kai.fp.core;

import com.kai.fp.display.Clickable;
import com.kai.fp.display.HUDComponent;
import com.kai.fp.display.Hoverable;
import com.kai.fp.display.Screen;
import com.kai.fp.items.Weapon;
import com.kai.fp.world.WorldLocation;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Kai on May 16, 2019
 */
public class InputHandler implements Updatable, MouseListener {
    private boolean up, down, left, right;

    private static List<Hoverable> hoverables = new ArrayList<>();
    private static List<Clickable> clickables = new ArrayList<>();
    private int currentMouseX, currentMouseY;

    public void mouseExists(int cx, int cy) {
        this.currentMouseX = cx;
        this.currentMouseY = cy;
    }

    public static void addHoverable(Hoverable h) {
        hoverables.add(h);
    }

    public static void addClickable(Clickable c) {
        clickables.add(c);
    }

    public static void reset() {
        clickables.clear();
        hoverables.clear();
    }

    public void createMap(Screen comp) {
        InputMap im = comp.getInputMap();
        ActionMap am = comp.getActionMap();

        im.put(KeyStroke.getKeyStroke("pressed RIGHT"), "right-pressed");
        im.put(KeyStroke.getKeyStroke("pressed D"), "right-pressed");
        am.put("right-pressed", new DirectionAction("right", true));

        im.put(KeyStroke.getKeyStroke("pressed LEFT"), "left-pressed");
        im.put(KeyStroke.getKeyStroke("pressed A"), "left-pressed");
        am.put("left-pressed", new DirectionAction("left", true));

        im.put(KeyStroke.getKeyStroke("pressed UP"), "up-pressed");
        im.put(KeyStroke.getKeyStroke("pressed W"), "up-pressed");
        am.put("up-pressed", new DirectionAction("up", true));

        im.put(KeyStroke.getKeyStroke("pressed DOWN"), "down-pressed");
        im.put(KeyStroke.getKeyStroke("pressed S"), "down-pressed");
        am.put("down-pressed", new DirectionAction("down", true));

        im.put(KeyStroke.getKeyStroke("released RIGHT"), "right-released");
        im.put(KeyStroke.getKeyStroke("released D"), "right-released");
        am.put("right-released", new DirectionAction("right", false));

        im.put(KeyStroke.getKeyStroke("released LEFT"), "left-released");
        im.put(KeyStroke.getKeyStroke("released A"), "left-released");
        am.put("left-released", new DirectionAction("left", false));

        im.put(KeyStroke.getKeyStroke("released UP"), "up-released");
        im.put(KeyStroke.getKeyStroke("released W"), "up-released");
        am.put("up-released", new DirectionAction("up", false));

        im.put(KeyStroke.getKeyStroke("released DOWN"), "down-released");
        im.put(KeyStroke.getKeyStroke("released S"), "down-released");
        am.put("down-released", new DirectionAction("down", false));
    }

    @Override
    public void update(long delta) {
        /*if (right) Camera.x += Camera.speed;
        if (left) Camera.x -= Camera.speed;
        if (up) Camera.y -= Camera.speed;
        if (down) Camera.y += Camera.speed;*/

        List<Hoverable> toRemove = new ArrayList<>();
        for (Hoverable h: hoverables) {
            h.updateHovered(currentMouseX, currentMouseY);
            if (h instanceof HUDComponent) {
                if (((HUDComponent) h).isMarkedForRemoval()) toRemove.add(h);
            }
        }
        hoverables.removeAll(toRemove);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        SwingUtilities.invokeLater(() -> {
            Weapon w = Game.getPlayer().getInventory().getWeapon();
            if (w != null) {
                int screenX = e.getX();
                int screenY = e.getY();
                w.fire(new WorldLocation(screenX+Camera.x,screenY+Camera.y));
            }
        });
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        List<Clickable> toRemove = new ArrayList<>();
        for (Clickable c: clickables) {
            c.possibleClick(e.getX(), e.getY());
            if (c instanceof HUDComponent) {
                if (((HUDComponent) c).isMarkedForRemoval()) toRemove.add(c);
            }
        }
        clickables.removeAll(toRemove);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    class DirectionAction extends AbstractAction {
        private String dir;
        private boolean setDirTo;

        public DirectionAction(String dir, boolean setDirTo) {
            this.dir = dir;
            this.setDirTo = setDirTo;
        }

        public void actionPerformed(ActionEvent e) {
            if (dir.equals("right")) Game.getPlayer().right = setDirTo;
            if (dir.equals("left")) Game.getPlayer().left = setDirTo;
            if (dir.equals("up")) Game.getPlayer().up = setDirTo;
            if (dir.equals("down")) Game.getPlayer().down = setDirTo;
        }
    }
}
