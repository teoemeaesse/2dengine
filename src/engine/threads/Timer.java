package engine.threads;

import java.util.TimerTask;

/**
 * Created by tomas on 4/23/2017.
 */
public abstract class Timer extends java.util.Timer {
    private double fps;

    public Timer(double fps) {
        this.fps = fps;
    }

    public void start() {
        schedule(new TimerTask() {
            public void run() {
                Timer.this.action();
                Timer.this.start();
            }
        }, (int) (1000 / fps));
    }

    public abstract void action();
}
