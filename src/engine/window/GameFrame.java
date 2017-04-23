package engine.window;

import engine.gfx.GamePanel;
import engine.threads.GameTimer;
import engine.timers.GameTimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;

/**
 * Created by tomas on 4/23/2017.
 */
public class GameFrame extends JFrame {
    private GamePanel gamePanel;
    private ArrayList<GameTimer> gameTimers;

    public GameFrame(String title, Dimension size, boolean resizable, GamePanel gamePanel, GameTimer[] gameTimers, EventListener... eventListeners) {
        super(title);
        this.gameTimers = new ArrayList<>(Arrays.asList(gameTimers));
        this.gamePanel = gamePanel;
        if(eventListeners != null) {
            for(EventListener e : eventListeners) {
                if(e.getClass() == KeyListener.class) addKeyListener((KeyListener) e);
                else if(e.getClass().equals(MouseListener.class)) addMouseListener((MouseListener) e);

            }
        }

        this.setSize(size.width, size.height);
        this.setResizable(resizable);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(this.getGamePanel());
        this.setVisible(true);
    }

    public GamePanel getGamePanel() {
        return this.gamePanel;
    }

    public ArrayList<GameTimer> getGameTimers() {
        return this.gameTimers;
    }
}
