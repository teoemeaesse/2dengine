package example;

import engine.threads.Timer;
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

        Timer t = new Timer(60) {
            int tick = 0;

            @Override
            public void action() {
                w.update();
                w.clearRenderableQueue();
                tick++;
                if(tick > 1800)
                    UIElement.removeInstances("button2");
            }
        };

        TextBox tb1 = new TextBox(Color.WHITE, new Font("Helvetica", Font.PLAIN, 12), "but", 8, 2),
                tb2 = new TextBox(Color.WHITE, new Font("Helvetica", Font.PLAIN, 12), "ton1");
        tb1.setOffset(UIElement.OFFSET_BOTTOM_LEFT);
        tb2.setOffset(UIElement.OFFSET_BOTTOM_RIGHT);
        tb1.setTextColor(Color.GREEN);

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
        for(int i = 0; i < 10; i++)
            new Button(new Sprite(new Rectangle(300 + i * 10, 50 + i * 10, 120, 20), Color.BLACK), "button2", new TextBox(Color.WHITE, new Font("Helvetica", Font.PLAIN, 20), "button2", "button2txt")){
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

        ((Button) UIElement.getInstances("button1").get(0)).setDragMode(UIElement.DRAG_CLICK_HOLD_RELEASE);
        ((Button) UIElement.getInstances("button1").get(0)).setDragOffset(UIElement.OFFSET_MIDDLE);
        ((Button) UIElement.getInstances("button1").get(0)).setOutline(Color.BLUE, 2);
        for(int i = 0; i < 10; i++){
            ((Button) UIElement.getInstances("button2").get(i)).setDragMode(UIElement.DRAG_CLICK_CLICK);
            ((Button) UIElement.getInstances("button2").get(i)).setDragOffset(UIElement.OFFSET_MOUSE);
            ((Button) UIElement.getInstances("button2").get(i)).getTextBox("button2txt").setOffset(UIElement.OFFSET_TOP_LEFT);
            ((Clickable) UIElement.getInstances("button2").get(i)).setTriggerButton(Button.RMB);
        }
        t.start();
    }
}
