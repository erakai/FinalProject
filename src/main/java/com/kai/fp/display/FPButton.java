package com.kai.fp.display;

import com.kai.fp.objs.inanimate.Portal;
import com.kai.fp.util.MFont;
import com.kai.fp.util.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * @author Kai on May 23, 2019
 */
public class FPButton extends JButton {
    private static BufferedImage image = ResourceManager.getSprite("button");
    private Color hoverColor = new Color(50, 97, 131);
    private boolean isHovering = false;

    public FPButton(int x, int y, Clickable c) {
        setBounds(x, y, 96, 48);
        this.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {isHovering=true;});
            }

            public void mouseExited(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {isHovering=false;});
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                c.possibleClick(e.getX(), e.getY());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(image, getX(), getY(), null);
        if (isHovering) {
            g.setColor(hoverColor);
            g.fillRect(getX()+8, getY()+8, 79, 31);
        }

        g.setColor(Color.WHITE);
        g.setFont(new MFont(1.3));
        g.drawString("Teleport", getX()+12, getY()+ 30);
    }
}
