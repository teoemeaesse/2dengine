package engine.ui;

/**
 * Created by tomas on 6/1/2017.
 */
public interface Draggable {
    void setDragging(boolean dragging);
    void setDragMode(int dragMode);
    void setDragOffset(int dragOffset);
    void setDragButton(int dragButton);
    void drag(int mouseX, int mouseY);
    int getDragMode();
    boolean getDragging();
}
