package entity;

import main.GamePanel;

public class Bullet extends Entity{
    GamePanel gp;

    public Bullet(GamePanel gp) {
        this.gp = gp;

        setDefaultValues();
        getBulletImage();
    }

    public void getBulletImage() {

    }
}
