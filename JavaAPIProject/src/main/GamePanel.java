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
    int highScore = findHighScore();

    // GamePanel constructor which loads the background image.
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

    // starts the game thread
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
        // if target1 is hit, both targets speed up.
        // if target2 is hit, both targest slow down.
        if (target1.checkCollision(bullet)) {
            target1.newX();
            target1.newY();
            target1.collisionArea.setLocation(target1.x, target1.y);
            score++;
            target1.speed++;
            target2.speed++;

        } if (target2.checkCollision(bullet)) {
            target2.newX();
            target2.newY();
            target2.collisionArea.setLocation(target2.x, target2.y);
            score++;
            if (target1.speed < 2) {
                target1.speed--;
                target2.speed--;
            }
        }

        // ends the game if the player collides with a target.
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

                // Makes the game run at FPS (60) times per second
                if (delta >= 1) {
                    update();
                    repaint();
                    delta--;
                }

            }

        // when the game ends, save the score to the file textScores.txt.
        try {
            FileWriter fW = new FileWriter("src/main/textScores.txt", true);
            BufferedWriter bW = new BufferedWriter(fW);

            bW.write(score + "");
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

        // Draws the player, and targets on screen.
        g.drawImage(background, 0, 0, 800,600, null);
        target1.draw(g2);
        target2.draw(g2);
        player.draw(g2);

        // Makes the bullet only have collision when it is displayed.
        bullet.collisionArea.setSize(0, 0);
        // Displays the bullet if the game is running and space is held down
        if (gameRunning && keyH.shooting) {
            bullet.displayBullet(g2);
        }

        // Displays the current score and high score in the window
        g.setColor(Color.WHITE);
        int fontSize = 32;
        setFont(new Font("SansSerif", Font.PLAIN, fontSize));
        g.drawString("Score: " + score, 9, WindowHeight - resizedSize);
        g.drawString("High Score: " + highScore, 9, WindowHeight - (2 * resizedSize));

        // Displays the game over screen
        if (!gameRunning) {
            g.setColor(Color.ORANGE);
            g.fillRect(300 - fontSize , 300 - fontSize, 300, fontSize + 8);

            g.setColor(Color.BLACK);
            g.drawString("YOU LOST!!!!!!!!", 300, 300);
        }
        g2.dispose();
    }

    // returns the largest int in textScores.txt
    private int findHighScore() {
        // the largest number so far found in the file.
        int hScore = 0;
        try {
            String line;
            int score;
            BufferedReader reader = new BufferedReader(new FileReader("src/main/textScores.txt"));

            // while the next line is not null
            while ((line = reader.readLine()) != null) {
                // compare the value of the number on this line with hScore so far.
                // if the number on this line is larger,
                // set hScore equal to the current line's number.
                score = Integer.valueOf(line);
                if (score > hScore) {
                    hScore = score;

                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return hScore;
    }
}
