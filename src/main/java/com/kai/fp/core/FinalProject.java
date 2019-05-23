package com.kai.fp.core;

import com.kai.fp.display.Screen;

import javax.swing.*;

/**
 * @author Kai on May 10, 2019
 */
public class FinalProject {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Final Project");
        Screen s = new Screen();
        frame.add(s);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        new Thread(new Game(s)).start();
    }

}
