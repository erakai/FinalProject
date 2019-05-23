package com.kai.fp.util;

import java.awt.*;

public class MFont extends Font {

    public MFont(String name, int style, double sizeMultiplier) {
        super(name, style, (int)(Globals.ORIGINAL_FONT.getSize()*sizeMultiplier));
    }

    public MFont(double sizeMultiplier) {
        this(Globals.ORIGINAL_FONT.getName(), Globals.ORIGINAL_FONT.getStyle(), sizeMultiplier);
    }
}
