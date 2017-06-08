package engine.ui;

import com.sun.istack.internal.Nullable;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by tomas on 6/1/2017.
 */
public abstract class UIElement {
    public static final int LMB = MouseEvent.BUTTON1, RMB = MouseEvent.BUTTON3, MMB = MouseEvent.BUTTON2,
            DRAG_NONE = 0, DRAG_CLICK_HOLD_RELEASE = 1, DRAG_CLICK_CLICK = 2,
            OFFSET_TOP_LEFT = 0, OFFSET_TOP_RIGHT = 1, OFFSET_BOTTOM_LEFT = 2, OFFSET_BOTTOM_RIGHT = 3, OFFSET_MIDDLE = 4, OFFSET_MOUSE = 5, OFFSET_TOP_MIDDLE = 6, OFFSET_LEFT_MIDDLE = 7, OFFSET_BOTTOM_MIDDLE = 8, OFFSET_RIGHT_MIDDLE = 9;

    private Sprite sprite;
    private String id;
    private boolean autoRender = true;

    private static ArrayList<UIElement> instances = new ArrayList<>();

    public UIElement(@Nullable Sprite sprite, String id) {
        this.sprite = sprite;
        this.id = id;
        instances.add(this);
    }


    public static void renderUIElements(Graphics g){
        for(UIElement uie : instances)
            if(uie.autoRender)
                uie.render(g);
    }

    public final void render(Graphics g){
        if(sprite != null)
            sprite.draw(g);
        additionalRender(g);
    }

    public abstract void additionalRender(Graphics g);

    public static void removeInstance(String id){
        for(int i = 0; i < instances.size(); i++){
            if(instances.get(i).getId().equals(id)){
                instances.remove(i);
                break;
            }
        }
    }
    public static UIElement getInstance(String id){
        UIElement instance = null;

        for(int i = 0; i < instances.size(); i++){
            if(instances.get(i).getId().equals(id)){
                instance = instances.get(i);
                break;
            }
        }

        return instance;
    }

    public Sprite getSprite() {
        return sprite;
    }
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    public String getId() {
        return id;
    }
    public static ArrayList<UIElement> getInstances() {
        return instances;
    }
    public void setAutoRender(boolean autoRender) {
        this.autoRender = autoRender;
    }
}
