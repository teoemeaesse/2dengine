package example;

import engine.threads.GameTimer;
import engine.ui.*;
import engine.ui.Button;
import engine.window.Window;

import java.awt.*;
import java.awt.Font;

/**
 * Created by tomas on 5/8/2017.
 */
public class Main {
    public static void main(String[] args){
        Window w = new Window("frame", 600, 400){
            @Override
            public void update() {
                mouseUpdate();
            }
        };
        w.setAntialiasing(true);
        w.display();
        w.center();

        GameTimer gt = new GameTimer(60) {
            int tick = 0;

            @Override
            public void action() {
                w.update();
                UIElement.queueUIElements(w);
                w.clearRenderableQueue();
                tick++;
                if(tick > 180)
                    UIElement.removeInstance("button1");
            }
        };

        TextBox tb1 = new TextBox(Color.WHITE, new Font("Helvetica", Font.PLAIN, 12), "but", "button1txt1", 8, 2),
                tb2 = new TextBox(Color.WHITE, new Font("Helvetica", Font.PLAIN, 12), "ton1", "button1txt2");
        tb1.setOffset(UIElement.OFFSET_BOTTOM_LEFT);
        tb2.setOffset(UIElement.OFFSET_BOTTOM_RIGHT);

        new Button(new Sprite(new Rectangle(50, 50, 120, 20), Color.RED), "button1", tb1, tb2) {
            @Override
            public void onClick() {
                System.out.println("lmb pressed");
            }

            @Override
            public void onMouseOver(Graphics g) {
                if(isMouseOver()){
                    getSprite().highlight(g, getSprite().getX(), getSprite().getY(), 5, Color.BLUE);
                }
            }
        };
        new Button(new Sprite(new Rectangle(300, 50, 120, 20), Color.BLACK), "button2", new TextBox(Color.WHITE, new Font("Helvetica", Font.PLAIN, 20), "button2", "button2txt")){
            @Override
            public void onClick() {
                System.out.println("rmb pressed");
            }

            @Override
            public void onMouseOver(Graphics g) {
                if(isMouseOver()){
                    getSprite().highlight(g, getSprite().getX(), getSprite().getY(), 3, Color.BLUE);
                }
            }
        };

        ((Button) UIElement.getInstance("button1")).setDragMode(UIElement.DRAG_CLICK_HOLD_RELEASE);
        ((Button) UIElement.getInstance("button1")).setDragOffset(UIElement.OFFSET_MIDDLE);
        ((Button) UIElement.getInstance("button1")).setOutline(Color.BLUE, 2);
        ((Button) UIElement.getInstance("button2")).setDragMode(UIElement.DRAG_CLICK_CLICK);
        ((Button) UIElement.getInstance("button2")).setDragOffset(UIElement.OFFSET_MOUSE);
        ((Button) UIElement.getInstance("button2")).getTextBox("button2txt").setOffset(UIElement.OFFSET_TOP_LEFT);
        ((Clickable) UIElement.getInstance("button2")).setTriggerButton(Button.RMB);
        gt.start();
    }
}
