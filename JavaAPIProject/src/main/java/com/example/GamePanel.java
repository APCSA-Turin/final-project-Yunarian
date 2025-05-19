package com.example;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    Thread gameThread;
    KeyHandler keyH = new KeyHandler();

    public GamePanel() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
    }

    public void run() {
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();

    }
}
