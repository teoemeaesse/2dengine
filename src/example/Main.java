package example;

import engine.threads.GameTimer;
import engine.ui.Button;
import engine.ui.Sprite;
import engine.window.GamePanel;

import java.awt.*;

/**
 * Created by tomas on 5/8/2017.
 */
public class Main {
    public static void main(String[] args){
        GamePanel gf = new GamePanel("frame", 600, 400){
            @Override
            public void render(Graphics g) {
                cls(g);
                Button.renderButtons(g);
                new Sprite(new Rectangle(50, 200, 150, 70), Color.RED).draw(g, 50, 200);
            }

            @Override
            public void update() {
                checkMouseOver();
            }
        };
        gf.setAntialiasing(true);

        GameTimer gt = new GameTimer(30) {
            int tick = 0;

            @Override
            public void action() {
                gf.repaint();
                gf.update();
                tick++;
                if(tick > 180)
                    Button.deleteButton(0);
            }
        };

        Button.createButton(new Button(new Sprite(new Rectangle(50, 50, 120, 20), Color.RED), Color.BLACK, gf.getGraphics(), new Font("Dialog", Font.PLAIN, 12)) {
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
        Button.createButton(new Button(new Sprite(new Rectangle(300, 50, 120, 20), Color.BLACK), Color.WHITE, gf.getGraphics(), new Font("Dialog", Font.PLAIN, 12), "button2") {
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
        Button.getInstance(0).setOutline(Color.BLUE, 2);
        Button.getInstance(0).setText("button0", gf.getGraphics());
        Button.getInstance(1).setTriggerButton(Button.RMB);
        gt.start();
    }
}
