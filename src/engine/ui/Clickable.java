package engine.ui;

/**
 * Created by tomas on 6/1/2017.
 */
public interface Clickable {
    void clicked(int button, int x, int y);
    void onClick();
    void setTriggerButton(int button);
    int getTriggerButton();
}
