package engine.ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by tomas on 6/1/2017.
 */
public abstract class UIElement {
    public static final int LMB = MouseEvent.BUTTON1, RMB = MouseEvent.BUTTON3, MMB = MouseEvent.BUTTON2,
            DRAG_NONE = 0, DRAG_CLICK_HOLD_RELEASE = 1, DRAG_CLICK_CLICK = 2,
            DRAG_OFFSET_TOP_LEFT = 0, DRAG_OFFSET_TOP_RIGHT = 1, DRAG_OFFSET_BOTTOM_LEFT = 2, DRAG_OFFSET_BOTTOM_RIGHT = 3, DRAG_OFFSET_MIDDLE = 4, DRAG_OFFSET_MOUSE = 5;

    private Sprite sprite;
    private int id;

    private static ArrayList<UIElement> instances = new ArrayList<>();

    public UIElement(Sprite sprite) {
        this.sprite = sprite;
        id = instances.size();
        instances.add(this);
    }
    public UIElement(Sprite sprite, int id) {
        this.sprite = sprite;
        this.id = id;
        instances.add(this);
    }


    public static void renderUIElements(Graphics g){
        for(UIElement uie : instances){
            uie.render(g);
        }
    }

    public final void render(Graphics g){
        sprite.draw(g);
        additionalRender(g);
    }

    public abstract void additionalRender(Graphics g);

    public static void removeInstance(int id){
        for(int i = 0; i < instances.size(); i++){
            if(instances.get(i).getId() == id){
                instances.remove(i);
                break;
            }
        }
    }
    public static UIElement getInstance(int id){
        UIElement instance = null;

        for(int i = 0; i < instances.size(); i++){
            if(instances.get(i).getId() == id){
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

    public int getId() {
        return id;
    }

    public static ArrayList<UIElement> getInstances() {
        return instances;
    }
}
