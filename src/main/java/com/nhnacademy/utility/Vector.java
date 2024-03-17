package com.nhnacademy.utility;

public class Vector {
    int dx;
    int dy;

    public Vector(){
        dx = 23;
        dy = -23;
    }
    public Vector(int dx, int dy){
        this.dx = dx;
        this.dy = dy;
    }
    public Vector(Vector other){
        this.dx = other.getDX();
        this.dy = other.getDY();
    }
    public int getDX() {
        return dx;
    }
    public void setDX(int dx) {
        this.dx = dx;
    }
    public int getDY() {
        return dy;
    }
    public void setDY(int dy) {
        this.dy = dy;
    }
    public void set(int dx, int dy){
        setDX(dx);
        setDY(dy);
    }
    public void set(Vector other){
        setDX(other.getDX());
        setDY(other.getDY());
    }
    public void add(Vector other){
        setDX(getDX()+other.getDX());
        setDY(getDY()+other.getDY());
    }
    public void subtract(Vector other){
        setDX(getDX()-other.getDX());
        setDY(getDY()-other.getDY());
    }
    public void multiply(double scale){
        set((int)(getDX()*scale), (int)(getDY()*scale));
    }
    @Override
    public boolean equals(Object other){
        return (other instanceof Vector)
            && (((Vector)other).getDX() == getDX())
            && (((Vector)other).getDY() == getDY());
    }
    @Override
    public String toString(){
        return "Vector : ["+getDX()+","+getDY()+"]";
    }

    public void turnDX(){
        setDX(-getDX());
    }
    public void turnDY(){
        setDY(-getDY());
    }

    

}
