package engine.gfx;

import engine.ui.Renderable;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by tomas on 14/07/2017.
 */
public class Layer implements Renderable {
    private ArrayList<Renderable> queue = new ArrayList<>();

    public void render(Graphics g){
        for(Renderable r : queue)
            r.render(g);
    }

    public void addToQueue(Renderable r){
        queue.add(r);
    }
    public void clearQueue(){
        queue = new ArrayList<>();
    }
}
