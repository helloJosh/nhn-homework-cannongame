package com.nhnacademy;

public class MovableWorld extends World{
    static final int DEFAULT_DT = 5;
    int moveCount;
    int maxMoveCount = 0;
    int dt = DEFAULT_DT;

    public void setDT(int dt){
        if(dt < 0)
            throw new IllegalArgumentException();
        this.dt = dt;
    }
    public int getDT() {
        return dt;
    }

    public void reset() {
        moveCount = 0;
    }

    public void move() {
        if ((getMaxMoveCount() == 0) || (getMoveCount() < getMaxMoveCount())) {
            moveCount++;
            repaint();
        }
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            move();
            try {
                Thread.sleep(getDT());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int getMaxMoveCount() {
        return maxMoveCount;
    }

    public void setMaxMoveCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException();
        }

        maxMoveCount = count;
    }

}
