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

        collisionArea = new Rectangle(6, 6, 33, 33);
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

        }
        if (keyH.downPressed) {
            direction = "down";
            y += speed;
        }

        if (keyH.leftPressed) {
            direction = "left";
            x -= speed;

        }
        if (keyH.rightPressed) {
            direction = "right";
            x += speed;

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

        } else if (y > bottomBound){
            y = bottomBound;

        } if (x < leftBound){
            x = leftBound;

        } else if (x > rightBound){
            x = rightBound;
        }

    }

    public String checkCollision (Entity entity) {
        int playerLeftBound = x + collisionArea.x;
        int playerRightBound = x + collisionArea.x + collisionArea.width;
        int playerTopBound = y + collisionArea.y;
        int playerBottomBound = y + collisionArea.y + collisionArea.width;

        int entityLeftBound = entity.x + entity.collisionArea.x;
        int entityRightBound = entity.x + entity.collisionArea.x + entity.collisionArea.width;
        int entityTopBound = entity.y + entity.collisionArea.y;
        int entityBottomBound = entity.y + entity.collisionArea.y + entity.collisionArea.width;

        // returnedCollisions is a string with directions of collision with respect to the player
        String returnedCollisions = "";

        if (playerLeftBound > entityRightBound) {
            returnedCollisions += "left ";

        } if (playerRightBound < entityLeftBound) {
            returnedCollisions += "right ";

        } if (playerTopBound > entityBottomBound) {
            returnedCollisions += "top ";

        } if (playerBottomBound < entityTopBound) {
            returnedCollisions += "bottom ";

        }

        return returnedCollisions;
    }

    public void draw(Graphics2D g) {
        BufferedImage image = null;

        switch (direction) {
        case "up":
            image = up;
            break;

        case "down":
            image = down;
            break;

        case "left":
            image = left;
            break;

        case "right":
            image = right;
            break;
        }

        g.drawImage(image, x, y, gp.resizedSize, gp.resizedSize , null);
    }
}
