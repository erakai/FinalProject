package com.kai.fp.display;

import com.kai.fp.core.Camera;
import com.kai.fp.core.Game;
import com.kai.fp.core.Updatable;
import com.kai.fp.objs.entities.Entity;
import com.kai.fp.objs.entities.enemies.Enemy;
import com.kai.fp.util.DrawPoint;
import com.kai.fp.util.MFont;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RisingText extends HUDComponent implements Updatable {
    private Entity owner;
    private String text;
    private Color color;

    private static final int speed = 2;

    private int yDiff = 0, frameDuration;
    private int xDiff = 0;

    public RisingText(Entity owner, String text, Color color, int frameDuration) {
        super(0, 0, null, 0, 0);
        Game.addUpdatable(this);
        Screen.addRenderable(this);
        this.owner = owner;
        this.text = text;
        this.color = color;
        this.frameDuration = frameDuration * speed;
        if (text.length() < 5) {
            xDiff = (int)(Math.random() * 20) - 10;
        } else {
            yDiff = (int)(Math.random() * 10) -5;
            xDiff = (int)(Math.random() * 40) - 55;
        }
    }

    public RisingText(Entity owner, String text, Color color) {
        this(owner, text, color, 25);
    }

    @Override
    public void update(long delta) {
        yDiff+=speed;

        if (yDiff > frameDuration) {
            die();
        }
    }

    @Override
    public void render(DrawPoint dp, Graphics g) {
       g.setColor(color);
       g.setFont(new MFont(1.5));
       g.drawString(text, (int)(owner.getCenterX() - Camera.x - owner.getWidth()/3 + xDiff), owner.getScreenY()-yDiff - Camera.y);
    }
}
