package engine.ui;

import com.sun.istack.internal.Nullable;
import engine.gfx.Sprite;

import java.awt.*;

/**
 * Created by tomas on 5/8/2017.
 */
public abstract class Button extends UIElement implements Clickable, Highlightable, Draggable {
    private Color outlineColor;
    private int triggerButton = LMB, dragButton = LMB, dragMode = DRAG_NONE, dragOffset = OFFSET_TOP_LEFT,
                outlineThickness = 0,
                mouseHorizontalOffset = 0, mouseVerticalOffset = 0;
    private boolean mouseOver = false, dragging = false;
    private TextBox[] textBoxes;

    public Button(Sprite sprite, String id, TextBox ... textBoxes){
        super(sprite, id);
        this.textBoxes = textBoxes;
    }
    public Button(Sprite sprite, TextBox ... textBoxes){
        super(sprite);
        this.textBoxes = textBoxes;
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
                if(dragOffset == OFFSET_MOUSE){
                    mouseHorizontalOffset = x - getSprite().getX();
                    mouseVerticalOffset = y - getSprite().getY();
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
        for(TextBox tb : textBoxes) {
            tb.updateParentSprite(getSprite());
            tb.render(g);
        }

        if(outlineThickness > 0){
            getSprite().highlight(g, getSprite().getCollisionBox().getBounds().x, getSprite().getCollisionBox().getBounds().y, outlineThickness, outlineColor);
        }
        onMouseOver(g);
    }

    @Override
    public final void drag(int mouseX, int mouseY){
        if(dragging){
            if(dragOffset == OFFSET_TOP_LEFT)
                getSprite().updateCollisionBox(mouseX, mouseY);
            else if(dragOffset == OFFSET_TOP_RIGHT)
                getSprite().updateCollisionBox(mouseX - getSprite().getWidth(), mouseY);
            else if(dragOffset == OFFSET_BOTTOM_LEFT)
                getSprite().updateCollisionBox(mouseX, mouseY - getSprite().getHeight());
            else if(dragOffset == OFFSET_BOTTOM_RIGHT)
                getSprite().updateCollisionBox(mouseX - getSprite().getWidth(), mouseY - getSprite().getHeight());
            else if(dragOffset == OFFSET_MIDDLE)
                getSprite().updateCollisionBox(mouseX - getSprite().getWidth() / 2, mouseY - getSprite().getHeight() / 2);
            else if(dragOffset == OFFSET_MOUSE)
                getSprite().updateCollisionBox(mouseX - mouseHorizontalOffset, mouseY - mouseVerticalOffset);
        }
    }

    @Override public final boolean isMouseOver() {
        return mouseOver;
    }
    @Override public final void setMouseOver(boolean highlight) {
        this.mouseOver = highlight;
    }
    @Override public final void setTriggerButton(int triggerButton){
        this.triggerButton = triggerButton;
    }
    @Override public final int getTriggerButton(){
        return triggerButton;
    }
    @Override public final void setDragging(boolean dragging){
        this.dragging = dragging;
    }
    @Override public final boolean getDragging(){
        return dragging;
    }
    @Override public final void setDragMode(int dragMode){
        this.dragMode = dragMode;
    }
    @Override public final int getDragMode(){
        return dragMode;
    }
    @Override public final void setDragOffset(int dragOffset){
        this.dragOffset = dragOffset;
    }
    @Override public final void setDragButton(int dragButton){
        this.dragButton = dragButton;
    }
    public final void setOutline(Color outlineColor, int outlineThickness){
        this.outlineThickness = outlineThickness;
        this.outlineColor = outlineColor;
    }

    @Nullable
    public TextBox getTextBox(String id){
        TextBox textBox = null;

        if(textBoxes.length != 0)
            for(TextBox tb : textBoxes)
                if(tb.getId().equals(id))
                    textBox = tb;

        return textBox;
    }
}
