package com.nhnacademy.component;

import java.awt.Color;
import java.awt.Graphics;

import com.nhnacademy.utility.Point;

public class PaintableBall extends Ball implements Paintable{
    public static final Color DEFAULT_COLOR = Color.BLACK;
    Color  color;

    public PaintableBall(Point location, int radius){
        this(location, radius, DEFAULT_COLOR);
    }
    public PaintableBall(Point location, int radius, Color color){
        this(location.getX(), location.getY(), radius, color);
    }
    public PaintableBall(int x, int y, int radius, Color color){
        super(x,y,radius);
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
        g.fillOval(getBounds().getMinX(), getBounds().getMinY(), getBounds().getWidth(), getBounds().getHeight());

        g.setColor(originalColor);
    }
    
}
