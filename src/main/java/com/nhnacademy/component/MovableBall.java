package com.nhnacademy.component;

import java.awt.Color;
import com.nhnacademy.utility.Point;
import com.nhnacademy.utility.Vector;

public class MovableBall extends PaintableBall implements Movable{
    public static final Vector DEFAUL_VECTOR = new Vector(0, 0);

    Vector motion = new Vector();
    boolean stopped = false;
    long dt = 0;
    StartedActionListener startedActionListener;
    MovedActionListener movedActionListener;
    Thread thread;
    
    public MovableBall(int x, int y, int radius, Color color){
        super(x, y, radius, color);
    }
    public MovableBall(Point location, int radius, Color color){
        this(location.getX(), location.getY(), radius, color);
    }
    @Override
    public Vector getMotion(){
        return new Vector(motion);
    }
    @Override
    public void setMotion(int dx, int dy){
        this.motion.set(dx, dy);
    }
    @Override
    public void setMotion(Vector motion){
        this.motion.set(motion);
    }
    public void setDT(long dt){
        this.dt = dt;
    }
    public long getDT(){
        return this.dt;
    }
    public void stop(){
        stopped = true;
        thread.interrupt();
    }
    @Override
    public void move(){
        move(this.motion);
    }
    @Override
    public void move(Vector motion){
        Point origin = getLocation();
        origin.translate(motion);
        setLocation(origin);

        if(movedActionListener != null){
            movedActionListener.action();
        }
    }
    @Override
    public void moveTo(Point location){
        setLocation(location);
        if(movedActionListener != null){
            movedActionListener.action();
        }
    }
    
    @Override
    public void run(){
        thread = Thread.currentThread();
        stopped = false;

        if(startedActionListener != null){
            startedActionListener.action();
        }

        while(!stopped){
            move();
            try{
                Thread.sleep(dt);
            } catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void addStartedActionListener(StartedActionListener listener) {
        this.startedActionListener = listener;
    }

    @Override
    public void addMovedActionListener(MovedActionListener listener) {
        this.movedActionListener = listener;
    }
}
