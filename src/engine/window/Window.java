package engine.window;

import engine.ui.*;
import javafx.stage.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

/**
 * Created by tomas on 4/23/2017.
 */
public abstract class Window extends JFrame {
    private boolean antialiasing = true;
    private Display display = new Display();
    private ArrayList<Renderable> renderQueue = new ArrayList<>();

    public Window(String title, int width, int height) {
        super(title);

        display.addMouseListener(new MouseHandler());
        add(display);
        setSize(width, height);
        setIgnoreRepaint(false);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public final void clearRenderableQueue(){
        UIElement.queueUIElements(this);
        repaint();
    }
    public abstract void update();

    public final void mouseUpdate(){
        checkMouseDragging();
        checkMouseOver();
    }

    private void checkMouseOver(){
        Point mousePosition = display.getMousePosition();
        if(mousePosition != null){
            Rectangle mouse = new Rectangle(mousePosition.x, mousePosition.y, 1, 1);
            for(UIElement uie : UIElement.getInstances())
                if(uie instanceof Highlightable)
                    if(mouse.intersects(uie.getSprite().getCollisionBox().getBounds()))
                        ((Highlightable) uie).setMouseOver(true);
                    else
                        ((Highlightable) uie).setMouseOver(false);
        }
    }
    private void checkMouseDragging(){
        for(UIElement uie : UIElement.getInstances()){
            if(uie instanceof Draggable){
                if(((Draggable) uie).getDragMode() != UIElement.DRAG_NONE){
                    Point mousePosition = display.getMousePosition();
                    if(mousePosition != null)
                        ((Draggable) uie).drag(mousePosition.x, mousePosition.y);
                }
            }
        }
    }
    private Graphics checkAntialiasing(Graphics g){
        if(this.antialiasing){
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        return g;
    }
    public final void display(){
        setVisible(true);
    }
    public final void setAntialiasing(boolean antialiasing){
        this.antialiasing = antialiasing;
    }
    public final void center(){
        setLocationRelativeTo(null);
    }
    public final void queueRenderable(Renderable renderable){
        renderQueue.add(renderable);
    }

    private class Display extends JPanel{
        @Override
        public void paintComponent(Graphics g){
            cls(g);
            g = checkAntialiasing(g);
            for(Renderable r : renderQueue)
                r.render(g);
            renderQueue = new ArrayList<>();
        }

        private void cls(Graphics g){
            g.clearRect(0, 0, getWidth(), getHeight());
        }
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
