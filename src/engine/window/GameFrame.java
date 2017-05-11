package engine.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

/**
 * Created by tomas on 4/23/2017.
 */
public abstract class GameFrame extends JFrame {
    private boolean antialiasing;

    public GameFrame(String title, int width, int height, boolean resizable, boolean antialiasing) {
        super(title);

        this.antialiasing = antialiasing;

        this.setSize(width, height);
        this.setResizable(resizable);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addMouseListener(new MouseHandler());
        this.setVisible(true);
        this.setIgnoreRepaint(false);
    }

    public abstract void render(Graphics g);
    public abstract void update();

    public final void cls(Graphics g){
        g.clearRect(0, 0, getWidth(), getHeight());
    }

    public final void checkMouseOver(){
        Point mousePos = getMousePosition();
        if(mousePos != null){
            Rectangle mouse = new Rectangle(mousePos.x, mousePos.y, 1, 1);
            for(Map.Entry<Integer, engine.ui.Button> me : engine.ui.Button.getInstances().entrySet()){
                if(mouse.intersects(me.getValue().getSprite().getCollisionBox()))
                    me.getValue().setMouseOver(true);
                else
                    me.getValue().setMouseOver(false);
            }
        }
    }
    private Graphics checkAntialiasing(Graphics g){
        if(this.antialiasing){
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        return g;
    }

    @Override
    public void paint(Graphics g){
        render(checkAntialiasing(g));
    }

    private class MouseHandler implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            for(Map.Entry<Integer, engine.ui.Button> me : engine.ui.Button.getInstances().entrySet())
                me.getValue().clicked(e.getButton(), e.getX(), e.getY());
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }
    }
}
