package engine.ui;

import java.awt.*;

/**
 * Created by tomas on 6/1/2017.
 */
public interface Highlightable {
    void onMouseOver(Graphics g);
    boolean isMouseOver();
    void setMouseOver(boolean mouseOver);
}
