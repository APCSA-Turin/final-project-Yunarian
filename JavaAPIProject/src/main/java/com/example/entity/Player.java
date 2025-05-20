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

        } else if (keyH.downPressed) {
            direction = "down";
            y += speed;

        } if (keyH.leftPressed) {
            direction = "left";
            x -= speed;

        } else if (keyH.rightPressed) {
            direction = "right";
            x += speed;

        }
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
