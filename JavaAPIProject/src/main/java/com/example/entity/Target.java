package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Target extends Entity {

    GamePanel gp;

    public Target(GamePanel gp) {
        this.gp = gp;

        setDefaultValues();
        getTargetImage();

        y = -200 + (int)(Math.random() * 150);
        x = (int) (Math.random() * (800 - gp.resizedSize));
        collisionArea = new Rectangle(x, y, 48, 48);
    }

    public Target(GamePanel gp, int speed) {
        super(speed);
        this.gp = gp;

        getTargetImage();

        y = -200 + (int)(Math.random() * 150);
        x = (int) (Math.random() * (800 - gp.resizedSize));
        collisionArea = new Rectangle(x, y, 48, 48);
    }

    public void getTargetImage() {
        try {
            down = ImageIO.read(getClass().getResourceAsStream("/target/Target.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (y < 750) {
            y += speed;
            collisionArea.translate(0, speed);

        } else {
            y = -150;
            x = (int) (Math.random() * (800 - gp.resizedSize));
            collisionArea.setLocation(x, y);
        }

    }

    public boolean checkCollision(Bullet b) {
        return collisionArea.intersects(b.collisionArea);
    }

    public void draw(Graphics2D g) {
        g.drawImage(down, x, y, gp.resizedSize, gp.resizedSize, null);
    }
}
