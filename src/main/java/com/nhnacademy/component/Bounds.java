package com.nhnacademy.component;

import java.awt.Rectangle;
import com.nhnacademy.utility.Point;
import com.nhnacademy.utility.Vector;

public class Bounds {
    final Rectangle rectangle;
    public Bounds(int x, int y,int width, int height){
        rectangle = new Rectangle(x, y, width, height);
    }
    public Bounds(Point location, int width, int height){
        rectangle = new Rectangle(location.getX(), location.getY(), width, height);
    }
    public Bounds(Rectangle other){
        rectangle = (Rectangle) other.clone();
    }
    public Bounds(Bounds other){
        rectangle = new Rectangle(other.getMinX(), other.getMinY(), other.getWidth(), other.getHeight());
    }
    Rectangle getRectangle() {
        return rectangle;
    }
    public void set(Bounds other){
        rectangle.setBounds(other.getRectangle());
    }
    public Point getLocation(){
        return new Point((int)rectangle.getMinX(), (int)rectangle.getMinY());
    }
    public void setLocation(Point location){
        rectangle.setLocation(location.getX(), location.getY());
    }
    public void setLocation(int x, int y){
        rectangle.setLocation(x,y);
    }
    public void translate(Vector motion){
        rectangle.translate(motion.getDX(), motion.getDY());
    }
    public int getCenterX(){
        return (int)rectangle.getCenterX();
    }
    public int getCenterY(){
        return (int)rectangle.getCenterY();
    }
    public int getMinX(){
        return (int)rectangle.getMinX();
    }
    public int getMinY(){
        return (int)rectangle.getMinY();
    }
    public int getMaxX() {
        return (int) rectangle.getMaxX();
    }
    public int getMaxY() {
        return (int) rectangle.getMaxY();
    }
    public int getWidth() {
        return (int) rectangle.getWidth();
    }
    public int getHeight() {
        return (int) rectangle.getHeight();
    }
    public boolean isCollision(Bounds other){
        return getRectangle().intersects(other.rectangle);
    }
    public boolean isIncluded(Bounds other){
        Bounds intersections = intersections(other);
        return other.equals(intersections);
    }
    public Bounds intersections(Bounds other){
        Rectangle intersection = getRectangle().intersection(other.getRectangle());
        return new Bounds((int)intersection.getMinX(), (int)intersection.getMinY(), (int)intersection.getWidth(), (int)intersection.getHeight());
    }
    @Override
    public boolean equals(Object other){
        return (other instanceof Bounds)
            && (getLocation().equals(((Bounds)other).getLocation()))
            && (getWidth() == ((Bounds)other).getWidth())
            && (getHeight() == ((Bounds)other).getHeight());
    }

    @Override
    public String toString(){
        return "[" + rectangle.getX() + "," + rectangle.getY() + "," + rectangle.getWidth() + ","
                + rectangle.getHeight() + "]";
    }
}
