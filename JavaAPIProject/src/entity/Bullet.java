package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bullet extends Entity{
    GamePanel gp;
    Player player;

    public Bullet(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
        collisionArea = new Rectangle(0, 0, 24, 120);

        setDefaultValues();
        getBulletImage();
    }

    // Loads the images for the Bullet object
    public void getBulletImage() {
        try {

            up = ImageIO.read(getClass().getResourceAsStream("/bullet/BulletUp.png"));
            left = ImageIO.read(getClass().getResourceAsStream("/bullet/BulletLeft.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/bullet/BulletRight.png"));
            down = ImageIO.read(getClass().getResourceAsStream("/bullet/BulletDown.png"));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // displays the bullet depending on the direction the player is facing,
    public void displayBullet(Graphics2D g2) {
        BufferedImage image = down;
        int width = 0;
        int height = 0;
        if (player.direction.equals("up")) {
            x = player.x + 12;
            y = player.y + 12 - 120;
            width = 24;
            height = 120;
            image = up;

        } if (player.direction.equals("down")) {
            x = player.x + 12;
            y = player.y - 12 + gp.resizedSize;
            width = 24;
            height = 120;
            image = down;

        } if (player.direction.equals("left")) {
            x = player.x + 12 - 120;
            y = player.y + 12;
            width = 120;
            height = 24;
            image = left;

        } if (player.direction.equals("right")) {
            x = player.x - 12 + gp.resizedSize;
            y = player.y + 12;
            width = 120;
            height = 24;
            image = right;
        }

        collisionArea.setSize(width, height);
        collisionArea.setLocation(x, y);
        g2.drawImage(image, x, y, width, height, null);

//    code to show bullet's collision area
//        g2.setColor(Color.GREEN);
//        g2.fill(collisionArea);
//        g2.draw(collisionArea);
    }
}
