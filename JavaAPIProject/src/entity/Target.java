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

        newX();
        newY();

        collOffset = 3;
        collSize = 42;
        collisionArea = new Rectangle(x - collOffset, y - collOffset, collSize, collSize);
    }

    public Target(GamePanel gp, int speed) {
        super(speed);
        this.gp = gp;

        getTargetImage();

        newX();
        newY();

        collOffset = 3;
        collSize = 42;
        collisionArea = new Rectangle(x - collOffset, y - collOffset, collSize, collSize);
    }

    // Loads the target's sprite
    public void getTargetImage() {
        try {
            down = ImageIO.read(getClass().getResourceAsStream("/target/Target.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // The target moves down every frame.
    // if the target is too far below the window, 
    // it's position is set to a random one above the window.
    public void update() {
        if (y < 750) {
            y += speed;
            collisionArea.translate(0, speed);

        } else {
            y = -150;
            newX();
            collisionArea.setLocation(x, y);
        }

    }

    // generates a random x-value 
    public void newX() {x = (int) (Math.random() * (800 - gp.resizedSize - 9));}


    public void newY() {y = -200 + (int)(Math.random() * 150);}


    public boolean checkCollision(Bullet b) {
        return collisionArea.intersects(b.collisionArea);
    }

    // Displays the target
    public void draw(Graphics2D g) {

        g.drawImage(down, x, y, gp.resizedSize, gp.resizedSize, null);

// hitbox displaying, for debugging
//        g.setColor(Color.ORANGE);
//        g.fill(collisionArea);
//        g.draw(collisionArea);
    }
}
