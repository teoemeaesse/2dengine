package engine.gfx;

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
    private float alpha = 1;
    private boolean visible = true;

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
    public static Sprite loadSpriteFromFile(int x, int y, InputStream is){
        BufferedImage image = loadImageFromFile(is);
        return new Sprite(new Rectangle(x, y, image.getWidth(), image.getHeight()), image);
    }

    public void draw(Graphics2D g) {
        if(visible){
            AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
            if(alpha < 1)
                g.setComposite(alphaComposite);
            if(image == null) {
                g.setColor(color);
                g.fillRect(getX(), getY(), getWidth(), getHeight());
            }else
                g.drawImage(image, getX(), getY(), null);
        }
    }
    public void draw(Graphics2D g, int xOffset, int yOffset, int widthOffset, int heightOffset) {
        if(visible){
            AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
            if(alpha < 1)
                g.setComposite(alphaComposite);
            if(image == null) {
                g.setColor(color);
                g.fillRect(getX() + xOffset, getY() + yOffset, getWidth() + widthOffset, getHeight() + heightOffset);
            }else
                g.drawImage(image, getX() + xOffset, getY() + yOffset, null);
        }
    }

    public void highlight(Graphics g, int x, int y, int thickness, Color color){
        g.setColor(color);
        for(int i = 0; i < thickness; i++)
            g.drawRect(x + i, y + i, getWidth() - i * 2 - 1, getHeight() - i * 2 - 1);
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
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
    public int getX(){
        return collisionBox.x;
    }
    public int getY(){
        return collisionBox.y;
    }
    public int getWidth(){
        return collisionBox.width;
    }
    public int getHeight(){
        return collisionBox.height;
    }
    public void setLocation(int x, int y){
        getCollisionBox().setLocation(x, y);
    }
    public void setSize(int width, int height){
        getCollisionBox().setSize(width, height);
    }
    public void setVisible(boolean visible){
        this.visible = visible;
    }
}
