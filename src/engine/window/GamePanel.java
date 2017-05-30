package engine.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

/**
 * Created by tomas on 4/23/2017.
 */
public abstract class GamePanel extends JPanel {
    private boolean antialiasing;
    private JFrame window;

    public GamePanel(String title, int width, int height) {
        window = new JFrame(title);

        antialiasing = true;

        window.add(this);

        window.setSize(width, height);
        window.setIgnoreRepaint(false);
        window.setResizable(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new MouseHandler());
        window.setVisible(true);
    }

    public abstract void render(Graphics g);
    public abstract void update();

    @Override
    public void paintComponent(Graphics g){
        cls(g);
        engine.ui.Button.renderButtons(g);
        render(checkAntialiasing(g));
    }

    public final void cls(Graphics g){
        g.clearRect(0, 0, getWidth(), getHeight());
    }

    public final void checkMouseOver(){
        Point mousePos = getMousePosition();
        if(mousePos != null){
            Rectangle mouse = new Rectangle(mousePos.x, mousePos.y, 1, 1);
            for(Map.Entry<Integer, engine.ui.Button> me : engine.ui.Button.getInstances().entrySet()){
                if(mouse.intersects(me.getValue().getSprite().getCollisionBox().getBounds()))
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
    public final void setAntialiasing(boolean antialiasing){
        this.antialiasing = antialiasing;
    }
    public final void setResizable(boolean resizable){
        window.setResizable(resizable);
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
