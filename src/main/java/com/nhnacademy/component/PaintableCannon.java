package com.nhnacademy.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.nhnacademy.utility.Point;

public class PaintableCannon extends Cannon implements Paintable{
    public static final Color DEFAULT_COLOR = Color.BLACK;
    public static final double DEFAULT_ANGLE = -45;
    Color  color;
    double cannonAngle;

    public PaintableCannon(Point location, int radius) {
        this(location.getX(), location.getY(), radius, DEFAULT_COLOR, DEFAULT_ANGLE);
    }
    public PaintableCannon(int x, int y, int radius) {
        this(x, y, radius, DEFAULT_COLOR,DEFAULT_ANGLE);
    }
    public PaintableCannon(Point location, int radius, Color color) {
        this(location.getX(), location.getY(), radius, color, DEFAULT_ANGLE);
    }
    public PaintableCannon(int x, int y, int radius, Color color){
        this(x, y, radius, color, DEFAULT_ANGLE);
    }
    public PaintableCannon(int x, int y, int radius, Color color, double cannonAngle){
        super(x,y,radius);
        if(color == null){
            throw new IllegalArgumentException("color is null");
        }
        this.color = color;
        this.cannonAngle = cannonAngle;
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
    public double getCannonAngle(){
        return this.cannonAngle;
    }
    public void setCannonAnlge(double cannonAngle){
        this.cannonAngle = cannonAngle;
    }
    public void repaintCannonAngle(double angleArgument, int minValue, int maxValue){
        double currentAngle = getCannonAngle();
        currentAngle += angleArgument;
        if(minValue <= currentAngle && currentAngle <= maxValue){
            setCannonAnlge(currentAngle);
        } else if(minValue > currentAngle){
            setCannonAnlge(minValue);
        } else if(maxValue < currentAngle){
            setCannonAnlge(maxValue);
        }
    }
    public Point shootingPoint(){
        int newX = (int)((getRadius()+30)*Math.cos(Math.toRadians(getCannonAngle()))) + getX();
        int newY = (int)((getRadius()+30)*Math.sin(Math.toRadians(getCannonAngle()))) + getY();
        return new Point(newX, newY);
    }

    @Override
    public void paint(Graphics g){
        if(g == null){
            throw new IllegalArgumentException("Paintable Graphics is null");
        }
        Graphics2D g2d = (Graphics2D) g; 
        Color originalColor = g.getColor();

        g.setColor(getColor());
        g.fillRect(getX()-getRadius(), getY(), getRadius()*2, getRadius());
        g.fillOval(getX()-getRadius(), getY()-getRadius(), getRadius()*2, getRadius()*2);

        g2d.rotate(Math.toRadians(getCannonAngle()), getX(), getY());
        g2d.fillRect(getX(), getY()-10, getRadius()+10, 20);
        g2d.rotate(-1*Math.toRadians(getCannonAngle()), getX(), getY());

        g.setColor(originalColor);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        long temp;
        temp = Double.doubleToLongBits(cannonAngle);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PaintableCannon other = (PaintableCannon) obj;
        return getId().equals(other.getId());
    }
}
