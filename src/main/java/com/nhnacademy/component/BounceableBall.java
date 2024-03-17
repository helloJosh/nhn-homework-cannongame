package com.nhnacademy.component;

import java.awt.Color;
import java.util.List;

import com.nhnacademy.utility.Point;
import com.nhnacademy.utility.Vector;

public class BounceableBall extends MovableBall implements Bounceable{
    private boolean side;
    public BounceableBall(int x, int y, int radius, Color color){
        super(x,y,radius,color);
        this.side = true;
    }
    public BounceableBall(Point location, int radius, Color color,boolean side){
        this(location.getX(), location.getY(), radius, color);
        this.side = side;
    }
    public void move(List<Bounded> boundedList) {
        super.move();

        for (Bounded bounded : boundedList) {
            if (bounded != this) {
                bounce(bounded);
            }
        }
    }
    public void bounce(Bounded other) {
        if (isCollision(other.getBounds())) {
            logger.info("ball {} collision with block {}",this, other);
            Bounds intersection = getBounds().intersections(other.getBounds());

            Vector newMotion = getMotion();

            if ((getBounds().getHeight() != intersection.getHeight())
                    && (other.getHeight() != intersection.getHeight())) {

                if (getMinY() < other.getMinY()) {
                    setLocation(new Point(getX(), other.getMinY() - getRadius()));
                } else {
                    setLocation(new Point(getX(), other.getMaxY() + getRadius()));
                }

                newMotion.turnDY();
            }

            if ((getBounds().getWidth() != intersection.getWidth())
                    && (other.getWidth() != intersection.getWidth())) {

                if (getMinX() < other.getMinX()) {
                    setLocation(new Point(other.getMinX() - getRadius(), getY()));
                } else {
                    setLocation(new Point(other.getMaxX() + getRadius(), getY()));
                }

                newMotion.turnDX();
            }
            if (!getMotion().equals(newMotion)) {
                setMotion(newMotion);
            }
        }
    }
    @Override
    public Color getColor(){
        return this.color;
    }
    public boolean getSide(){
        return this.side;
    }
}
