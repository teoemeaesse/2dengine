package example;

import engine.threads.GameTimer;
import engine.ui.Button;
import engine.ui.Sprite;
import engine.window.GameFrame;

import java.awt.*;

/**
 * Created by tomas on 5/8/2017.
 */
public class Main {
    public static void main(String[] args){
        GameFrame gf = new GameFrame("frame", 600, 400, true, true){
            int x = 10;
            @Override
            public void render(Graphics g) {
                cls(g);
                Button.renderAllButtons(getGraphics());
            }

            @Override
            public void update() {
                x += 5;
                checkMouseOver();
            }
        };

        GameTimer gt = new GameTimer(60) {
            int tick = 0;
            @Override
            public void action() {
                gf.update();
                gf.repaint();
                tick++;
                if(tick > 180)
                    Button.deleteButton(0);
            }
        };
        engine.ui.Button.createButton(0, new Button(new Sprite(new Rectangle(50, 50, 120, 20), Color.RED), Button.LMB, Color.BLACK, gf.getGraphics(), new Font("Dialog", Font.PLAIN, 12), "button1") {
            @Override
            public void onClick() {
                System.out.println("lmb pressed");
            }

            @Override
            public void onMouseOver(Graphics g) {
                if(isMouseOver()){
                    sprite.highlight(g, sprite.getCollisionBox().x, sprite.getCollisionBox().y, 5, Color.BLUE);
                }
            }
        });
        engine.ui.Button.createButton(1, new Button(new Sprite(new Rectangle(300, 50, 120, 20), Color.BLACK), Button.RMB, Color.WHITE, gf.getGraphics(), new Font("Dialog", Font.PLAIN, 12), "button2") {
            @Override
            public void onClick() {
                System.out.println("rmb pressed");
            }

            @Override
            public void onMouseOver(Graphics g) {
                if(isMouseOver()){
                    sprite.highlight(g, sprite.getCollisionBox().x, sprite.getCollisionBox().y, 3, Color.BLUE);
                }
            }
        });
        gt.start();
    }
}
