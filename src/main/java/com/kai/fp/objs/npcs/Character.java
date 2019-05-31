package com.kai.fp.objs.npcs;

import com.kai.fp.core.Game;
import com.kai.fp.core.Renderable;
import com.kai.fp.core.Updatable;
import com.kai.fp.display.Screen;
import com.kai.fp.objs.inanimate.InanimateObj;
import com.kai.fp.util.DrawPoint;
import com.kai.fp.util.Globals;
import com.kai.fp.world.WorldLocation;
import jdk.nashorn.internal.objects.Global;

import java.awt.*;

public abstract class Character extends InanimateObj implements Updatable {
    boolean showDialogue = false;
    private String[] dialogueToShow;
    private String longestLine;

    public Character(WorldLocation location, String[] dialogueToShow, String longestLine) {
        super(location);
        setPhysical(false);
        this.longestLine = longestLine;
        this.dialogueToShow = dialogueToShow;
        Game.addUpdatable(this);
    }

    @Override
    public void update(long delta) {
        showDialogue = checkCollision(Game.getPlayer());
    }

    @Override
    public void render(DrawPoint dp, Graphics g) {
        super.render(dp, g);

        if (showDialogue) {
            Screen.addRenderOnTop(dp, new Renderable() {
                @Override
                public void render(DrawPoint dp, Graphics g) {
                    int stringHeight = g.getFontMetrics().getAscent()+4;
                    int stringWidth = (int)(g.getFontMetrics().stringWidth(longestLine)*1.35);
                    int totalStringHeight = stringHeight * dialogueToShow.length;

                    g.setColor(new Color(230,230,230));
                    g.fillRect(10, Globals.DISPLAY_HEIGHT-totalStringHeight-15, stringWidth+6, totalStringHeight+5);
                    g.setColor(Color.BLACK);
                    for (int i = 0; i < dialogueToShow.length; i++) {
                        String line = dialogueToShow[i];

                        g.drawString(line, 12, Globals.DISPLAY_HEIGHT - totalStringHeight-15 + ((i + 1) * stringHeight));
                    }
                }
            });
        }
    }
}
