package example;

import engine.gfx.Sprite;
import engine.threads.Timer;
import engine.ui.*;
import engine.ui.Button;
import engine.window.Window;

import java.awt.*;

/**
 * Created by tomas on 5/8/2017.
 */
public class Main {
    public static void main(String[] args){
        Window w = new Window("frame", 600, 400){
            int tick = 0;

            @Override
            public void update() {
                mouseUpdate();
            }
            @Override
            public void render(Graphics g){
                clearRenderableQueue();
                tick++;
                if(tick > 180){
                    UIElement.removeInstances("button2");
                    ((TextBox) UIElement.getInstances("a").get(0)).setText("qwerty");
                }
            }
        };
        w.setAntialiasing(true);
        w.display();
        w.center();

        Timer t = new Timer(60) {
            @Override
            public void action() {
                w.update();
                w.repaint();
            }
        };

        TextBox tb1 = new TextBox("but", "a", true, 2),
                tb2 = new TextBox("ton1", false, 0);
        tb1.getSprite().setColor(Color.BLUE);
        tb1.setBackgroundVisible(true);
        tb1.setHorizontalPadding(8);
        tb1.setVerticalPadding(2);
        tb1.setOffset(UIElement.OFFSET_BOTTOM_LEFT);
        tb2.setOffset(UIElement.OFFSET_BOTTOM_RIGHT);
        tb1.setTextColor(Color.YELLOW);
        tb1.setTextColor(Color.GREEN);

        new Button(new Sprite(new Rectangle(50, 50, 120, 20), Color.RED), "button1", true, tb1, tb2) {
            @Override
            public void init() {
                getSprite().setAlpha(.5f);
            }

            @Override
            public void onClick() {
                System.out.println("lmb pressed");
            }

            @Override
            public void onMouseOver(Graphics g) {
                highlight(g, Color.BLUE, 5);

            }
        };
        for(int i = 0; i < 10; i++)
            new Button(new Sprite(new Rectangle(300 + i * 10, 50 + i * 10, 120, 20), Color.BLACK), "button2", true, new TextBox("button2", "button2txt", false, 0)){
                @Override
                public void init() {
                    getSprite().setAlpha(.75f);
                }

                @Override
                public void onClick() {
                        System.out.println("rmb pressed");
                    }

                @Override
                public void onMouseOver(Graphics g) {
                    highlight(g, Color.BLUE, 3);
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
