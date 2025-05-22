package main;

import javax.swing.*;
import java.awt.*;

public class Main {
    // main.Main method to show frame
    public static void main(String[] args) {
        JFrame frame = new JFrame("Time Trial!!!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        GamePanel game = new GamePanel();
        game.setBackground(Color.BLACK);
        frame.add(game);

        frame.pack();
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setResizable(false);

        game.startGameThread();
    }
}
