package com.example;

import javax.swing.*;
import java.awt.*;

public class Main {
    // Main method to show frame
    public static void main(String[] args) {
        JFrame frame = new JFrame("Moving Ball Game");
        SimpleGame game = new SimpleGame();

        game.setBackground(Color.BLACK);
        frame.add(game);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
