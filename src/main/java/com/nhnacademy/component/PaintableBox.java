package com.nhnacademy.component;

import java.awt.Color;
import java.awt.Graphics;

import com.nhnacademy.utility.Point;

public class PaintableBox extends Box implements Paintable{
    public static final Color DEFAULT_COLOR = Color.BLACK;
    Color  color;

    public PaintableBox(Point location, int width, int height) {
        this(location.getX(), location.getY(), width, height);
    }
    public PaintableBox(int x, int y, int width, int height) {
        this(x, y, width, height, DEFAULT_COLOR);
    }
    public PaintableBox(Point location, int width, int height, Color color) {
        this(location.getX(), location.getY(), width, height, color);
    }
    public PaintableBox(int x, int y, int width,int hieght, Color color){
        super(x,y,width,hieght);
        if(color == null){
            throw new IllegalArgumentException("color is null");
        }
        this.color = color;
    }

    @Override
    public Color getColor(){
        return this.color;
    }

    @Override
    public void setColor(Color color){
        if(color == null)
            throw new IllegalArgumentException("color is null");
        this.color = color;
    }

    @Override
    public void paint(Graphics g){
        if(g == null){
            throw new IllegalArgumentException("Paintable Graphics is null");
        }
        Color originalColor = g.getColor();

        g.setColor(getColor());
        g.fillRect(getBounds().getMinX(), getBounds().getMinY(), getBounds().getWidth(), getBounds().getHeight());

        g.setColor(originalColor);
    }
    
}
