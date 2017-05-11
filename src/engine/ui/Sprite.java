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
    private BufferedImage[] images;
    private Color color;

    public Sprite(Rectangle collisionBox, BufferedImage ... images) {
        this.collisionBox = collisionBox;
        this.images = images;
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
        if(this.images == null) {
            g.setColor(this.color);
            g.fillRect(x, y, this.collisionBox.width, this.collisionBox.height);
        }else
            for(BufferedImage bi : images)
                g.drawImage(bi, x, y, null);
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
        this.collisionBox.setLocation(this.collisionBox.x + xIncrement, this.collisionBox.y + yIncrement);
    }

    public Rectangle getCollisionBox() {
        return this.collisionBox;
    }
    public BufferedImage[] getImage() {
        return this.images;
    }
    public Color getColor() {
        return this.color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public boolean isColliding(Sprite sprite) {
        return this.collisionBox.intersects(sprite.getCollisionBox());
    }
    public boolean isColliding(Rectangle collisionBox) {
        return this.collisionBox.intersects(collisionBox);
    }
}
