package com.kai.fp.core;

import com.kai.fp.display.Screen;
import com.kai.fp.util.Globals;

import javax.swing.*;
import java.util.Scanner;

/**
 * @author Kai on May 10, 2019
 */
public class FinalProject {

    public static void main(String[] args) {
        String coords = JOptionPane.showInputDialog("Display size? 802x602 is recommended. Format: \"[width] [height]\".");
        if (coords != null && coords.split(" ").length == 2) {
            try {
                Globals.DISPLAY_WIDTH = Integer.valueOf(coords.split(" ")[0]);
                Globals.DISPLAY_HEIGHT = Integer.valueOf(coords.split(" ")[1]);
                System.out.println();
            } catch(Exception ex) {}
        }

        JFrame frame = new JFrame("Final Project");
        Screen s = new Screen();
        frame.add(s);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);



        new Thread(new Game(s)).start();
    }

}
