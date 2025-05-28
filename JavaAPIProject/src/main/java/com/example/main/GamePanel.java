package main;

import entity.Bullet;
import entity.Entity;
import entity.Player;
import entity.Target;

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
    Target target1 = new Target(this);
    Target target2 = new Target(this, 4);

    Bullet bullet = new Bullet(this, player);

    int FPS = 60;
    int score = 0;
    boolean gameRunning = false;

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
        target1.update();
        target2.update();


        // if a target intersects with the bullet,
        // that target has their location regenerated,
        // and score is incremented by 1.

        // currently running too many times whenever true.
        if (target1.checkCollision(bullet)) {
            target1.newX();
            target1.newY();
            score++;

            System.out.println("Score: " + score);

        } else if (target1.y == 0) {
            target1.collisionArea.setSize(48, 48);
        }

        if (target2.checkCollision(bullet)) {
            target2.newX();
            target2.newY();
            target2.collisionArea.setLocation(target2.x, target2.y);
            score++;
            System.out.println("Score: " + score);

        } else if (target2.y == 0) {
            target2.collisionArea.setSize(48, 48);
        }

        if (player.checkCollision(target1) || player.checkCollision(target2)) {
            gameRunning = false;

        }

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
        target1.draw(g2);
        target2.draw(g2);
        player.draw(g2);
        if (keyH.shooting) {
            bullet.displayBullet(g2);
        }

        g2.dispose();
    }
}
