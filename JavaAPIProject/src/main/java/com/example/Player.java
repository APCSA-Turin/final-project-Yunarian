import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.security.Key;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public Player (GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        this.setDefaultValues();
    }

    public void getPlayerImage () throws IOException {
        try {
            up = ImageIO.read(getClass().getResourceAsStream("res/player/PlayerUp.png"));

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public void update() {
        if (keyH.upPressed) {
            y -= speed;

        } else if (keyH.downPressed) {
            y += speed;

        } else if (keyH.leftPressed) {
            x -= speed;

        } else if (keyH.rightPressed){
            x += speed;

        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillOval(x, y, 30, 30);

    }
}
