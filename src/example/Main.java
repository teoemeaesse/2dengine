package example;

import engine.threads.GameTimer;
import engine.ui.Button;
import engine.ui.Clickable;
import engine.ui.Sprite;
import engine.ui.UIElement;
import engine.window.GamePanel;

import java.awt.*;

/**
 * Created by tomas on 5/8/2017.
 */
public class Main {
    public static void main(String[] args){
        GamePanel gp = new GamePanel("frame", 600, 400){
            @Override
            public void render(Graphics g) {
                UIElement.renderUIElements(g);
                new Sprite(new Rectangle(50, 200, 150, 70), Color.RED).draw(g, 50, 200);
            }

            @Override
            public void update() {
                mouseUpdate();
            }
        };
        gp.setAntialiasing(true);
        gp.display();

        GameTimer gt = new GameTimer(60) {
            int tick = 0;

            @Override
            public void action() {
                gp.repaint();
                gp.update();
                tick++;
                if(tick > 180)
                    UIElement.removeInstance(0);
            }
        };

        new Button(new Sprite(new Rectangle(50, 50, 120, 20), Color.RED), Color.BLACK, gp.getGraphics(), new Font("Dialog", Font.PLAIN, 12), "button1", 0) {
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
        new Button(new Sprite(new Rectangle(300, 50, 120, 20), Color.BLACK), Color.WHITE, gp.getGraphics(), new Font("Dialog", Font.PLAIN, 12), "button2"){
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

        ((Button) UIElement.getInstance(1)).setDragMode(UIElement.DRAG_CLICK_CLICK);
        ((Button) UIElement.getInstance(1)).setDragOffset(UIElement.DRAG_OFFSET_MOUSE);
        ((Button) UIElement.getInstance(0)).setDragMode(UIElement.DRAG_CLICK_HOLD_RELEASE);
        ((Button) UIElement.getInstance(0)).setDragOffset(UIElement.DRAG_OFFSET_MIDDLE);
        ((Button) UIElement.getInstance(0)).setOutline(Color.BLUE, 2);
        ((Clickable) UIElement.getInstance(1)).setTriggerButton(Button.RMB);
        gt.start();
    }
}
