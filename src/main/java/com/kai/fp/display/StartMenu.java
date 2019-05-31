package com.kai.fp.display;

import com.kai.fp.core.Renderable;
import com.kai.fp.util.DrawPoint;
import com.kai.fp.util.MFont;

import java.awt.*;

public class StartMenu implements Renderable {

    public StartMenu() {
        Screen.addRenderable(this);
    }

    @Override
    public void render(DrawPoint dp, Graphics g) {
        g.setFont(new MFont(1.8));
        g.setColor(Color.WHITE);
        g.drawString("HOW TO PLAY", 20, 40);
        g.setFont(new MFont(1.3));
        g.drawString("- WASD or Arrow Keys to move", 20, 100);
        g.drawString("- Left Click to shoot (or hold down). Bullets travel to your cursor (if it's within their range).", 20, 125);
        g.drawString("- 'T' to enable autofire", 20, 150);
        g.drawString("- 'P' to skip the current world and move on to the next.", 20, 250);
        g.drawString("- 'L' to cheat in health/damage/defense to make the game easier.", 20, 275);
        g.drawString("Hit any key to move on to the first level.", 20, 350);
    }
}
