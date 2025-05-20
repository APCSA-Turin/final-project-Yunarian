package entity;

import java.awt.image.BufferedImage;

public class Entity {
    int x, y;
    int speed;

    public BufferedImage up, down, left, right;
    public String direction;

    public void setDefaultValues() {
        x = 30;
        y = 30;
        speed = 4;
        direction = "down";
    }


}
