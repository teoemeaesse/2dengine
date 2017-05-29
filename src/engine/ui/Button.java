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
    private Color textColor, outlineColor;
    private java.awt.Font font;
    private int triggerButton, outlineThickness = 0;
    private boolean mouseOver = false;

    public static final int LMB = MouseEvent.BUTTON1, RMB = MouseEvent.BUTTON3, MMB = MouseEvent.BUTTON2;

    private static Map<Integer, Button> instances = new HashMap<>();

    public Button(Sprite sprite, Color textColor, Graphics g, java.awt.Font font, String text){
        this.sprite = sprite;
        this.triggerButton = LMB;
        this.text = text;
        this.textColor = textColor;
        this.font = font;
        textBounds = getStringBounds((Graphics2D) g, text, sprite.getCollisionBox().x, sprite.getCollisionBox().y);
    }
    public Button(Sprite sprite, Color textColor, Graphics g, java.awt.Font font){
        this.sprite = sprite;
        this.triggerButton = LMB;
        this.text = "";
        this.textColor = textColor;
        this.font = font;
        textBounds = getStringBounds((Graphics2D) g, text, sprite.getCollisionBox().x, sprite.getCollisionBox().y);
    }

    public Button(Sprite sprite) {
        this.sprite = sprite;
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
    public static void createButton(Button button){
        instances.put(instances.size(), button);
    }
    public static void deleteButton(int id){
        instances.remove(id);
    }
    public static void deleteButtons(){
        instances = new HashMap<>();
    }

    public static void renderButtons(Graphics g){
        for(Map.Entry<Integer, Button> me : instances.entrySet())
            me.getValue().render(g);
    }

    private void render(Graphics g){
        sprite.draw(g, sprite.getCollisionBox().x, sprite.getCollisionBox().y);
        if(font != null){
            g.setColor(textColor);
            g.setFont(font);
            g.drawString(text, sprite.getCollisionBox().x + sprite.getCollisionBox().width / 2 - textBounds.width / 2, sprite.getCollisionBox().y + sprite.getCollisionBox().height / 2 + textBounds.height / 2);
            if (outlineThickness > 0) {
                sprite.highlight(g, sprite.getCollisionBox().x, sprite.getCollisionBox().y, outlineThickness, outlineColor);
            }
        }
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
    public static Button getInstance(int id) {
        return instances.get(id);
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
    public final void setTriggerButton(int triggerButton){
        this.triggerButton = triggerButton;
    }
    public final void setOutline(Color outlineColor, int outlineThickness){
        this.outlineThickness = outlineThickness;
        this.outlineColor = outlineColor;
    }

    public void setText(String text, Graphics g) {
        this.text = text;
        textBounds = getStringBounds((Graphics2D) g, text, sprite.getCollisionBox().x, sprite.getCollisionBox().y);
    }
}
