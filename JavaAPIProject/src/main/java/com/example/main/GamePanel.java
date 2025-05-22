package main;

import entity.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable {
    final int origSize = 16; // 16 by 16
    final int scaleFactor = 3;
    public int resizedSize = origSize * scaleFactor; // 48 by 48
    public int WindowWidth = 800;
    public int WindowHeight = 600;

    BufferedImage background;
    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    CollisionController cController = new CollisionController(this);
    Player player = new Player(this, keyH);

    int FPS = 60;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WindowWidth, WindowHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        try {
            background = ImageIO.read(getClass().getResourceAsStream("/background/space.jpg"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();

    }

    public void update() {
        player.update();
    }

    public void run() {
            double drawInterval = 1000000000.0/FPS;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;

            while (gameThread != null) {
                currentTime = System.nanoTime();
                delta += (currentTime - lastTime)/drawInterval;
                lastTime = currentTime;

                if (delta >= 1) {
                    update();
                    repaint();
                    delta--;
                }

            }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(background, 0, 0, 800,600, null);
        player.draw(g2);
        g2.dispose();
    }
}
