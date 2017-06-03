package engine.window;

import engine.ui.Clickable;
import engine.ui.Draggable;
import engine.ui.Highlightable;
import engine.ui.UIElement;
import javafx.stage.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
    }

    public abstract void render(Graphics g);
    public abstract void update();

    @Override
    public void paintComponent(Graphics g){
        cls(g);
        render(checkAntialiasing(g));
    }

    public final void cls(Graphics g){
        g.clearRect(0, 0, getWidth(), getHeight());
    }

    public final void mouseUpdate(){
        checkMouseDragging();
        checkMouseOver();
    }

    public final void display(){
        getWindow().setVisible(true);
    }

    private void checkMouseOver(){
        Point mousePos = getMousePosition();
        if(mousePos != null){
            Rectangle mouse = new Rectangle(mousePos.x, mousePos.y, 1, 1);
            for(UIElement uie : UIElement.getInstances()){
                if(uie instanceof Highlightable){
                    if (mouse.intersects(uie.getSprite().getCollisionBox().getBounds()))
                        ((Highlightable) uie).setMouseOver(true);
                    else
                        ((Highlightable) uie).setMouseOver(false);
                }
            }
        }
    }
    private void checkMouseDragging(){
        for(UIElement uie : UIElement.getInstances())
            if(uie instanceof Draggable)
                if(((Draggable) uie).getDragMode() != UIElement.DRAG_NONE)
                    if(getMousePosition() != null)
                        ((Draggable) uie).drag(getMousePosition().x, getMousePosition().y);
    }
    private Graphics checkAntialiasing(Graphics g){
        if(this.antialiasing){
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        return g;
    }

    public JFrame getWindow() {
        return window;
    }

    public final void setAntialiasing(boolean antialiasing){
        this.antialiasing = antialiasing;
    }
    public final void setResizable(boolean resizable){
        window.setResizable(resizable);
    }
    public final void center(){
        window.setLocationRelativeTo(null);
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
            for(UIElement uie : UIElement.getInstances()){
                if(uie instanceof Clickable){
                    ((Clickable) uie).clicked(e.getButton(), e.getX(), e.getY());
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            for(UIElement uie : UIElement.getInstances()){
                if(uie instanceof Draggable){
                    if(((Draggable) uie).getDragMode() == UIElement.DRAG_CLICK_HOLD_RELEASE)
                        ((Draggable) uie).setDragging(false);
                }
            }
        }
    }
}
