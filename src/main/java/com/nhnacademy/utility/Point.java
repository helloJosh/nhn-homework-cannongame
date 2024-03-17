package com.nhnacademy.utility;

public class Point {
    private int x;
    private int y;

    public Point(){
        this.x = 0;
        this.y = 0;
    }
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Point(Point other){
        this.x = other.getX();
        this.y = other.getY();
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void set(Point other){
        setX(other.getX());
        setY(other.getY());
    }
    public void set(int x, int y){
        setX(x);
        setY(y);
    }
    public void translate(int dx, int dy){
        setX(getX()+dx);
        setY(getY()+dy);
    }
    public void translate(Vector motion){
        set(getX()+motion.getDX(), getY()+motion.getDY());
    }

    @Override
    public boolean equals(Object other){
        return (other instanceof Point)
            && (getX()==((Point)other).getX())
            && (getY()==((Point)other).getY());
    }
    @Override
    public String toString(){
        return "("+getX()+","+getY()+")";
    }

}
