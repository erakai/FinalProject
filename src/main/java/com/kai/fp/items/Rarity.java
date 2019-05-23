package com.kai.fp.items;

import java.awt.*;

/**
 * @author Kai on May 22, 2019
 */
public enum Rarity {
    COMMON(new Color(239, 239, 239)),
    RARE(new Color(74, 161, 238)),
    GLYPHIC(new Color(255, 242, 50));

    private Color color;
    Rarity(Color c) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }}
