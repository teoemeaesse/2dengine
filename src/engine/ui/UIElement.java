package engine.ui;

import engine.gfx.Sprite;
import engine.window.Window;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by tomas on 6/1/2017.
 */
public abstract class UIElement implements Renderable {
    public static final int LMB = MouseEvent.BUTTON1, RMB = MouseEvent.BUTTON3, MMB = MouseEvent.BUTTON2,
            DRAG_NONE = 0, DRAG_CLICK_HOLD_RELEASE = 1, DRAG_CLICK_CLICK = 2,
            OFFSET_TOP_LEFT = 0, OFFSET_TOP_RIGHT = 1, OFFSET_BOTTOM_LEFT = 2, OFFSET_BOTTOM_RIGHT = 3, OFFSET_MIDDLE = 4, OFFSET_MOUSE = 5, OFFSET_TOP_MIDDLE = 6, OFFSET_LEFT_MIDDLE = 7, OFFSET_BOTTOM_MIDDLE = 8, OFFSET_RIGHT_MIDDLE = 9;

    private Sprite sprite;
    private String id;
    private int layer = 0;

    private static ArrayList<UIElement> instances = new ArrayList<>();

    public UIElement(Sprite sprite, String id, boolean instance){
        this.sprite = sprite;
        this.id = id;
        init();
        if(instance)
            instances.add(this);
    }
    public UIElement(Sprite sprite, boolean instance){
        this.sprite = sprite;
        this.id = "";
        init();
        if(instance)
            instances.add(this);
    }


    public static void queueUIElements(Window window){
        for(UIElement uie : instances)
            window.queueRenderable(uie, uie.getLayer());
    }

    public void render(Graphics g){
        if(sprite != null){
            Graphics2D g2d = (Graphics2D) g;
            sprite.draw(g2d);
        }
        additionalRender(g);
    }

    public abstract void init();
    public abstract void additionalRender(Graphics g);

    public static void removeInstances(String id){
        for(int i = 0; i < instances.size(); i++)
            if(instances.get(i).getId().equals(id))
                instances.remove(i);
    }
    public static ArrayList<UIElement> getInstances(String id){
        ArrayList<UIElement> instances = new ArrayList<>();

        for(int i = 0; i < UIElement.instances.size(); i++)
            if(UIElement.instances.get(i).getId().equals(id))
                instances.add(UIElement.instances.get(i));

        return instances;
    }


    public final Sprite getSprite() {
        return sprite;
    }
    public final void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    public final int getLayer() {
        return layer;
    }
    public final void setLayer(int layer) {
        this.layer = layer;
    }
    public final String getId() {
        return id;
    }
    public static final ArrayList<UIElement> getInstances() {
        return instances;
    }
}
