package engine.threads;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tomas on 4/23/2017.
 */
public abstract class GameTimer extends Timer {
    private double fps;

    public GameTimer(double fps) {
        this.fps = fps;
    }

    public void start() {
        schedule(new TimerTask() {
            public void run() {
                GameTimer.this.action();
                GameTimer.this.start();
            }
        }, (int) (1000 / fps));
    }

    public abstract void action();
}
