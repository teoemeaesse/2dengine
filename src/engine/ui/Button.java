package engine.ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tomas on 5/8/2017.
 */
public abstract class Button extends UIElement implements Clickable, Highlightable, Draggable {
    private Rectangle textBounds;
    private String text;
    private Color textColor, outlineColor;
    private java.awt.Font font;
    private int triggerButton = LMB, dragButton = LMB, dragMode = DRAG_NONE, dragOffset = DRAG_OFFSET_TOP_LEFT, outlineThickness = 0, xOffset = 0, yOffset = 0;
    private boolean mouseOver = false, dragging = false;

    public Button(Sprite sprite, Color textColor, Graphics g, java.awt.Font font, String text){
        super(sprite);
        this.text = text;
        this.textColor = textColor;
        this.font = font;
        textBounds = getStringBounds((Graphics2D) g, text, sprite.getCollisionBox().getBounds().x, sprite.getCollisionBox().getBounds().y);
    }
    public Button(Sprite sprite, Color textColor, Graphics g, java.awt.Font font, String text, int id){
        super(sprite, id);
        this.text = text;
        this.textColor = textColor;
        this.font = font;
        textBounds = getStringBounds((Graphics2D) g, text, sprite.getCollisionBox().getBounds().x, sprite.getCollisionBox().getBounds().y);
    }
    public Button(Sprite sprite) {
        super(sprite);
    }
    public Button(Sprite sprite, int id) {
        super(sprite, id);
    }

    @Override
    public final void clicked(int button, int x, int y){
        if(new Rectangle(x, y, 1, 1).intersects(getSprite().getCollisionBox().getBounds())){
            if(button == triggerButton)
                onClick();
        }

        if(button == dragButton){
            if(!dragging && new Rectangle(x, y, 1, 1).intersects(getSprite().getCollisionBox().getBounds())) {
                setDragging(true);
                if(dragOffset == DRAG_OFFSET_MOUSE){
                    xOffset = x - getSprite().getX();
                    yOffset = y - getSprite().getY();
                }
            }
            else if(dragMode == DRAG_CLICK_CLICK) {
                setDragging(false);
            }
        }
    }
    @Override
    public abstract void onClick();
    @Override
    public abstract void onMouseOver(Graphics g);

    @Override
    public final void additionalRender(Graphics g){
        if(font != null){
            g.setColor(textColor);
            g.setFont(font);
            g.drawString(text, getSprite().getX() + getSprite().getWidth() / 2 - textBounds.width / 2, getSprite().getY() + getSprite().getHeight() / 2 + textBounds.height / 2);
        }
        if(outlineThickness > 0){
            getSprite().highlight(g, getSprite().getCollisionBox().getBounds().x, getSprite().getCollisionBox().getBounds().y, outlineThickness, outlineColor);
        }
        onMouseOver(g);
    }

    private Rectangle getStringBounds(Graphics2D g2, String str, float x, float y) {
        g2.setFont(font);
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv = g2.getFont().createGlyphVector(frc, str);
        return gv.getPixelBounds(null, x, y);
    }

    @Override
    public final void drag(int mouseX, int mouseY){
        if(dragging){
            if(dragOffset == DRAG_OFFSET_TOP_LEFT)
                getSprite().updateCollisionBox(mouseX, mouseY);
            else if(dragOffset == DRAG_OFFSET_TOP_RIGHT)
                getSprite().updateCollisionBox(mouseX - getSprite().getWidth(), mouseY);
            else if(dragOffset == DRAG_OFFSET_BOTTOM_LEFT)
                getSprite().updateCollisionBox(mouseX, mouseY - getSprite().getHeight());
            else if(dragOffset == DRAG_OFFSET_BOTTOM_RIGHT)
                getSprite().updateCollisionBox(mouseX - getSprite().getWidth(), mouseY - getSprite().getHeight());
            else if(dragOffset == DRAG_OFFSET_MIDDLE)
                getSprite().updateCollisionBox(mouseX - getSprite().getWidth() / 2, mouseY - getSprite().getHeight() / 2);
            else if(dragOffset == DRAG_OFFSET_MOUSE){
                getSprite().updateCollisionBox(mouseX - xOffset, mouseY - yOffset);
            }
        }
    }

    @Override
    public final boolean isMouseOver() {
        return mouseOver;
    }
    @Override
    public final void setMouseOver(boolean highlight) {
        this.mouseOver = highlight;
    }
    @Override
    public final void setTriggerButton(int triggerButton){
        this.triggerButton = triggerButton;
    }
    @Override
    public final int getTriggerButton(){
        return triggerButton;
    }
    @Override
    public final void setDragging(boolean dragging){
        this.dragging = dragging;
    }
    @Override
    public final boolean getDragging(){
        return dragging;
    }
    @Override
    public final void setDragMode(int dragMode){
        this.dragMode = dragMode;
    }
    @Override
    public final int getDragMode(){
        return dragMode;
    }
    @Override
    public final void setDragOffset(int dragOffset){
        this.dragOffset = dragOffset;
    }
    @Override
    public final void setDragButton(int dragButton){
        this.dragButton = dragButton;
    }

    public final void setOutline(Color outlineColor, int outlineThickness){
        this.outlineThickness = outlineThickness;
        this.outlineColor = outlineColor;
    }

    public final void setText(String text, Graphics g) {
        this.text = text;
        textBounds = getStringBounds((Graphics2D) g, text, getSprite().getCollisionBox().getBounds().x, getSprite().getCollisionBox().getBounds().y);
    }


}
