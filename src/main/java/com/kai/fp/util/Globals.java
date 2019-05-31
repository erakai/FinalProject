package com.kai.fp.util;

import javax.swing.*;
import java.awt.*;

/**
 * Holds global variables in one class for ease of change.
 *
 * @author Kai on May 10, 2019
 */
public class Globals {

    /**
     * The initial size of the display in pixels.
     */
    public static  int DISPLAY_WIDTH = 802, DISPLAY_HEIGHT = 602;

    /**
     * The ideal/max frames per second of the app.
     * The display is refreshed ever (1000/fps) ms.
     */
    public static final int FRAMES_PER_SECOND = 60;

    /**
     * The original font as set by Java for the client os.
     */
    public static final Font ORIGINAL_FONT = new JPanel().getFont();

    /**
     * If set to true, the user will be prompted to enter a custom display size.
     */
    public static final boolean CUSTOM_DISPLAY_SIZE = false;

    /**
     * Enables/Disables sounds.
     */
    public static final boolean PLAY_SOUNDS = false;

}
