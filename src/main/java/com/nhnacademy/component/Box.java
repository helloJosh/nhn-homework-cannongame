package com.nhnacademy.component;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nhnacademy.utility.Point;

public class Box implements Bounded, HitListener{
    final String id = UUID.randomUUID().toString();
    final Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    final Bounds bounds;
    HitListener hitListener;

    public Box(Point location, int width, int hieght){
        this(location.getX(), location.getY(), width, hieght);
    }
    public Box(int x, int y, int width, int height){
        if(width <= 0 || height <= 0){
            throw new IllegalArgumentException("직사각형 각변이 0보다는 커야합니다.");
        }
        if(x+(long)width/2 > Integer.MAX_VALUE
            || x-(long)width/2 < Integer.MIN_VALUE
            || y+(long)height/2 > Integer.MAX_VALUE
            || y-(long)height/2 < Integer.MIN_VALUE)
            throw new IllegalArgumentException("박스가 정수 공간을 벗어납니다");
        bounds = new Bounds(x-width, y-height,width, height);
        logger.trace("Box {} Created : {}, {}, {}, {}",id, x, y, width, height);
    }


    public int getX() {
        return bounds.getCenterX();
    }
    public int getY() {
        return bounds.getCenterY();
    }
    public int getRadius(){
        return bounds.getWidth()/2;
    }
    public Point getLocation() {
        return new Point(bounds.getCenterX(), bounds.getCenterY());
    }

    public void setLocation(Point location) {
        bounds.setLocation(location.getX() - getWidth() / 2, location.getY() - getHeight() / 2);
    }

    @Override
    public Bounds getBounds() {
        return new Bounds(bounds);
    }
    @Override
    public void setBounds(Bounds bounds) {
        this.bounds.set(bounds);
    }

    @Override
    public int getMinX() {
        return bounds.getMinX();
    }
    @Override
    public int getMaxX() {
        return bounds.getMaxX();
    }
    @Override
    public int getCenterX() {
        return bounds.getCenterX();
    }
    @Override
    public int getMinY() {
        return bounds.getMinY();
    }
    @Override
    public int getMaxY() {
        return bounds.getMaxY();
    }
    @Override
    public int getCenterY() {
        return bounds.getCenterY();
    }
    @Override
    public int getWidth() {
        return bounds.getWidth();
    }
    @Override
    public int getHeight() {
        return bounds.getHeight();
    }
    @Override
    public boolean isCollision(Bounds other) {
        return bounds.isCollision(other);
    }
    @Override
    public boolean isInclude(Bounds other) {
        return bounds.isIncluded(other);
    }
    @Override
    public void hit(Bounded other) {
        if(hitListener != null){
            hitListener.hit(other);
        }
    }
    @Override
    public void setHitListener(HitListener listener) {
        this.hitListener = listener;
    }
    @Override
    public String toString() {
        return getX()+","+ getY() +","+ getRadius();
    }
}
