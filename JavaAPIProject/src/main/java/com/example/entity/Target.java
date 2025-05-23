package entity;

import java.awt.*;

public class Target extends Entity{

    public Target() {
        setDefaultValues();
        collisionArea = new Rectangle(0, 0, 48, 48);
    }
}
