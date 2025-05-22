package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int x, y;
    int speed;

    public BufferedImage up, down, left, right;
    public String direction;
    public Rectangle collisionArea;

    public void setDefaultValues() {
        x = 699;
        y = 501;
        speed = 3;
        direction = "down";
    }


}
