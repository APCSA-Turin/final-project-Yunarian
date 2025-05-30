package main;

import entity.Bullet;
import entity.Entity;
import entity.Player;
import entity.Target;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class GamePanel extends JPanel implements Runnable {
    final int origSize = 16; // 16 by 16
    final int scaleFactor = 3;
    public int resizedSize = origSize * scaleFactor; // 48 by 48
    public int WindowWidth = 800;
    public int WindowHeight = 600;

    BufferedImage background;
    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    Player player = new Player(this, keyH);
    Target target1 = new Target(this);
    Target target2 = new Target(this, 4);

    Bullet bullet = new Bullet(this, player);

    int FPS = 60;
    int score = 0;
    boolean gameRunning = true;
//    int highScore = findHighScore();

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
        if (target1.checkCollision(bullet)) {
            target1.newX();
            target1.newY();
            target1.collisionArea.setLocation(target1.x, target1.y);
            score++;
            target1.speed++;
            target2.speed++;

        }

        if (target2.checkCollision(bullet)) {
            target2.newX();
            target2.newY();
            target2.collisionArea.setLocation(target2.x, target2.y);
            score++;
            target1.speed--;
            target2.speed--;

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

            while (gameThread != null && gameRunning) {
                currentTime = System.nanoTime();
                delta += (currentTime - lastTime)/drawInterval;
                lastTime = currentTime;

                if (delta >= 1) {
                    update();
                    repaint();
                    delta--;
                }

            }
        // when the game ends, save the score to the file textScores.
        try {
            FileWriter fW = new FileWriter("src/main/textScores", true);
            BufferedWriter bW = new BufferedWriter(fW);

            bW.write(score);
            bW.newLine();
            bW.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
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

        bullet.collisionArea.setSize(0, 0);
        if (keyH.shooting) {
            bullet.displayBullet(g2);
        }

        g.setColor(Color.WHITE);
        setFont(new Font("SansSerif", Font.PLAIN, 32));
        g.drawString("Score: " + score, 9, WindowHeight - 50);
        g.drawString("High Score: " + 200, 9, WindowHeight - 100);

        if (!gameRunning) {
            g.drawString("YOU LOST!!!!!!!!", 300, 300);
        }
        g2.dispose();
    }

    private int findHighScore() {
        int hScore = 0;
        try {
            File scoreFile = new File("src/main/textScores");
            Scanner myReader = new Scanner(scoreFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                // if there's a number in the file which is greater than high score,
                // set high score equal to it
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return hScore;
    }
}
