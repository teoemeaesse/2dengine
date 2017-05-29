package engine.ui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tomas on 4/23/2017.
 */
public class Sprite {
    private Rectangle collisionBox = new Rectangle(0, 0, 0, 0);
    private BufferedImage image;
    private Color color;

    public Sprite(Rectangle collisionBox, BufferedImage image) {
        this.collisionBox = collisionBox;
        this.image = image;
    }
    public Sprite(Rectangle collisionBox, Color color) {
        this.collisionBox = collisionBox;
        this.color = color;
    }

    public static BufferedImage loadImageFromFile(InputStream is) {
        BufferedImage bufferedImage = null;

        try {
            bufferedImage = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bufferedImage;
    }

    public void draw(Graphics g, int x, int y) {
        if(image == null) {
            g.setColor(color);
            g.fillRect(x, y, collisionBox.width, collisionBox.height);
        }else
            g.drawImage(image, x, y, null);
    }
    public void highlight(Graphics g, int x, int y, int thickness, Color color){
        g.setColor(color);
        for(int i = 0; i < thickness; i++)
            g.drawRect(x + i, y + i, collisionBox.width - i * 2 - 1, collisionBox.height - i * 2 - 1);
    }

    public void updateCollisionBox(int newX, int newY) {
        this.collisionBox.setLocation(newX, newY);
    }
    public void incrementCollisionBox(int xIncrement, int yIncrement) {
        this.collisionBox.setLocation(collisionBox.x + xIncrement, collisionBox.y + yIncrement);
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }
    public BufferedImage getImage() {
        return image;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public boolean isColliding(Sprite sprite) {
        return collisionBox.intersects(sprite.getCollisionBox());
    }
    public boolean isColliding(Rectangle collisionBox) {
        return collisionBox.intersects(collisionBox);
    }
}
