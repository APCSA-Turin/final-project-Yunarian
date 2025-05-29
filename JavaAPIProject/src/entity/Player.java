package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public Player (GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();

        collOffset = 9;
        collSize = 30;
        collisionArea = new Rectangle(x + collOffset, y + collOffset, collSize, collSize);
    }

    public void getPlayerImage () {
        try {

            up = ImageIO.read(getClass().getResourceAsStream("/player/PlayerUp.png"));
            left = ImageIO.read(getClass().getResourceAsStream("/player/PlayerLeft.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/player/PlayerRight.png"));
            down = ImageIO.read(getClass().getResourceAsStream("/player/PlayerDown.png"));

        } catch(IOException e) {
            e.printStackTrace();
        }


    }

    public void update() {

        if (keyH.upPressed) {
            direction = "up";
            y -= speed;
            collisionArea.translate(0, -1 * speed);

        }
        if (keyH.downPressed) {
            direction = "down";
            y += speed;
            collisionArea.translate(0, speed);
        }

        if (keyH.leftPressed) {
            direction = "left";
            x -= speed;
            collisionArea.translate( -1 * speed, 0);

        }
        if (keyH.rightPressed) {
            direction = "right";
            x += speed;
            collisionArea.translate(speed, 0);

        }
        inBounds();
    }

    // keeps the player always visible on the window
    public void inBounds() {
        int topBound = 0;
        int bottomBound = gp.WindowHeight - 87;
        int leftBound = 0;
        int rightBound = gp.WindowWidth - 64;

        if (y < topBound) {
            y = topBound;
            collisionArea.setLocation(x + collOffset, topBound + collOffset);

        } else if (y > bottomBound){
            y = bottomBound;
            collisionArea.setLocation(x + collOffset, bottomBound + collOffset);

        } if (x < leftBound){
            x = leftBound;
            collisionArea.setLocation(leftBound + collOffset, y + collOffset);

        } else if (x > rightBound){
            x = rightBound;
            collisionArea.setLocation(rightBound + collOffset, y + collOffset);
        }

    }

    public boolean checkCollision (Entity entity) {
        return collisionArea.intersects(entity.collisionArea);
    }

    public void draw(Graphics2D g) {
        BufferedImage image = switch (direction) {
            case "up" -> up;
            case "down" -> down;
            case "left" -> left;
            case "right" -> right;
            default -> null;
        };

        g.drawImage(image, x, y, gp.resizedSize, gp.resizedSize , null);

//        code to show player's collision area
//        g.setColor(Color.BLUE);
//        g.fill(collisionArea);
//        g.draw(collisionArea);
    }
}
