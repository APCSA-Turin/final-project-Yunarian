package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int x, y;
    public int speed;
    public int collOffset;
    public int collSize;

    public BufferedImage up, down, left, right;
    public String direction;
    public Rectangle collisionArea;


    public Entity() {
        setDefaultValues();
    }


    public Entity (int speed) {
        setDefaultValues();
        this.speed = speed;
    }


    public Entity(int x, int y, int speed, String direction) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;
    }


    // Sets the default values for all Entity objects.
    public void setDefaultValues() {
        x = 699;
        y = 501;
        speed = 3;
        direction = "down";
    }

}
