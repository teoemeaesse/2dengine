package engine.ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tomas on 5/8/2017.
 */
public abstract class Button {
    protected Sprite sprite;
    private Rectangle textBounds;
    private String text;
    private Color textColor;
    private java.awt.Font font;
    private int triggerButton;
    private boolean mouseOver = false;

    public static final int LMB = MouseEvent.BUTTON1, RMB = MouseEvent.BUTTON3, MMB = MouseEvent.BUTTON2;

    private static Map<Integer, Button> instances = new HashMap<>();

    public Button(Sprite sprite, int triggerButton, Color textColor, Graphics g, java.awt.Font font, String text){
        this.sprite = sprite;
        this.triggerButton = triggerButton;
        this.text = text;
        this.textColor = textColor;
        this.font = font;
        textBounds = getStringBounds((Graphics2D) g, text, sprite.getCollisionBox().x, sprite.getCollisionBox().y);
    }

    public final void clicked(int button, int x, int y){
        if(button == triggerButton && new Rectangle(x, y, 1, 1).intersects(sprite.getCollisionBox()))
            onClick();
    }
    public abstract void onClick();
    public abstract void onMouseOver(Graphics g);


    public static void createButton(int id, Button button){
        instances.put(id, button);
    }
    public static void deleteButton(int id){
        instances.remove(id);
    }

    public static void renderAllButtons(Graphics g){
        for(Map.Entry<Integer, Button> me : instances.entrySet())
            me.getValue().render(g);
    }

    private void render(Graphics g){
        sprite.draw(g, sprite.getCollisionBox().x, sprite.getCollisionBox().y);
        g.setColor(textColor);
        g.setFont(font);
        g.drawString(text, sprite.getCollisionBox().x + sprite.getCollisionBox().width / 2 - textBounds.width / 2, sprite.getCollisionBox().y + sprite.getCollisionBox().height / 2 + textBounds.height / 2);
        onMouseOver(g);
    }

    private Rectangle getStringBounds(Graphics2D g2, String str, float x, float y) {
        g2.setFont(font);
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv = g2.getFont().createGlyphVector(frc, str);
        return gv.getPixelBounds(null, x, y);
    }

    public static Map<Integer, Button> getInstances() {
        return instances;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean highlight) {
        this.mouseOver = highlight;
    }
}
