package engine.window;

import javax.swing.*;
import java.awt.*;

/**
 * Created by tomas on 4/23/2017.
 */
public abstract class GamePanel extends JPanel {
    private boolean antialiasing;

    public GamePanel(boolean antialiasing) {
        this.setIgnoreRepaint(false);
        this.antialiasing = antialiasing;
    }

    public void paintComponent(Graphics g) {
        if(this.antialiasing) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        render(g);
    }

    public abstract void render(Graphics var1);

    public abstract void update();
}
