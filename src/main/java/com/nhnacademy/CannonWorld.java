package com.nhnacademy;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import com.nhnacademy.component.Ball;
import com.nhnacademy.component.Bounceable;
import com.nhnacademy.component.BounceableBall;
import com.nhnacademy.component.Bounded;
import com.nhnacademy.component.Box;
import com.nhnacademy.component.Brittle;
import com.nhnacademy.component.BrittleBox;
import com.nhnacademy.component.Cannon;
import com.nhnacademy.component.HitListener;
import com.nhnacademy.component.Movable;
import com.nhnacademy.component.PaintableBox;
import com.nhnacademy.component.PaintableCannon;
import com.nhnacademy.utility.Point;
import com.nhnacademy.utility.Vector;

public class CannonWorld extends MovableWorld implements MouseMotionListener, KeyListener{
    static final int WALL_THICKNESS = 200;
    static final int BAR_WIDTH = 50;
    static final int BAR_THICKNESS = 50;
    static final int BAR_SPEED = 10;
    static final int MIN_HEIGHT = WALL_THICKNESS * 2 + BAR_THICKNESS;
    static final int MIN_WIDTH = WALL_THICKNESS * 2 + BAR_WIDTH;
    int ballSpeed = 80;
    int blockHeight = 60;
    int blockWidth = 60;
    int cannon1pLocationX = 50;
    int cannon2pLocationX = 900;
    Vector gravity = new Vector(0,1);
    Vector windSpeed = new Vector(0,0);
    Vector angle = new Vector(23, -23);

    final Box leftWall;
    final Box rightWall;
    final Box topWall;
    final Box bottomWall;
    final PaintableCannon cannon1p;
    final PaintableCannon cannon2p;

    final List<Box> boxList = new LinkedList<>();
    final List<Ball> ballList = new LinkedList<>();
    ExecutorService ballThreadPoll1 = Executors.newFixedThreadPool(4);
    ExecutorService ballThreadPoll2 = Executors.newFixedThreadPool(4);

    final Color[] colors = {Color.YELLOW, Color.WHITE, Color.BLUE, Color.GREEN };

    public CannonWorld(int x, int y, int width, int height){
        super();
        this.setBounds(x,y,width,height);
        leftWall = new PaintableBox(0, height+WALL_THICKNESS/2,
                                        WALL_THICKNESS, height+WALL_THICKNESS, Color.RED);
        rightWall = new PaintableBox(width+WALL_THICKNESS,height+WALL_THICKNESS/2,
                                        WALL_THICKNESS, height+WALL_THICKNESS, Color.BLUE);
        topWall = new PaintableBox(width, 0, 
                                        width, WALL_THICKNESS, Color.YELLOW);
        bottomWall = new PaintableBox(width, height+WALL_THICKNESS, 
                                        width, WALL_THICKNESS, Color.GREEN);
        add(leftWall);
        add(rightWall);
        add(topWall);
        add(bottomWall);

        bottomWall.setHitListener(other ->{
            if(other instanceof Bounceable){
                Vector motion = ((Movable)other).getMotion();

                motion.multiply(0.5);
                ((Movable)other).setMotion(motion);
            }

        });
        
        cannon1p =new PaintableCannon(cannon1pLocationX,height,30,Color.BLACK);
        cannon2p =new PaintableCannon(cannon2pLocationX,height,30,Color.BLACK,-135);
        
        add(cannon1p);
        add(cannon2p);

        init();
        setFocusable(true);
        addKeyListener(this);
        addMouseMotionListener(this);    
    }
    public void init() {
        Random random = new Random();
        for(int j = 0 ; j < 6;j++){
            for(int i=0; i< 11;i++){
                if(i == random.nextInt(10))
                    break;
                Box box = new BrittleBox(230 + i*60, 500 -j*60, blockWidth, blockHeight, Color.LIGHT_GRAY);
                add(box);
            }
        }
    }
    @Override
    public void add(Bounded object){
        super.add(object);
        if(object instanceof Movable){
            if(object instanceof  BounceableBall){
                if(((BounceableBall)object).getSide()){
                    ballThreadPoll1.execute((Movable)object);
                }
                else{
                    ballThreadPoll2.execute((Movable)object);
                }
            }
        }
    }

    public void clear(boolean side){
        List<Bounded> boundedList = getBoundedList();
        for(Bounded ball : boundedList){
            if(ball instanceof  BounceableBall){
                if(((BounceableBall)ball).getSide() == side){
                    remove(ball);
                    ((Movable)ball).stop();
                }
            }
        }
    }
    public void start(){
        BounceableBall ball = new BounceableBall(cannon1p.shootingPoint(),20, Color.YELLOW,true);

        ball.addStartedActionListener(() -> {
            Vector newMotion = new Vector();
            double radianAngle = Math.toRadians(cannon1p.getCannonAngle());
            double totalPower = Math.sqrt((double)newMotion.getDX()*newMotion.getDX()+(double)newMotion.getDY()*newMotion.getDY());
            newMotion.set((int)(totalPower*Math.cos(radianAngle)),
                            (int)(totalPower*Math.sin(radianAngle)));
            ball.setMotion(newMotion);
            super.add(ball);
        });

        ball.addMovedActionListener(() -> {
            List<Bounded> removeList = new LinkedList<>();

            Vector newMotion = ball.getMotion();
            ball.setDT(ballSpeed);
            newMotion.add(gravity);
            newMotion.add(windSpeed);
            
            ball.setMotion(newMotion);

            if (ball instanceof Bounceable) {
                for (int j = 0; j < getCount(); j++) {
                    Bounded other = get(j);

                    if (ball != other && ball.isCollision(other.getBounds())) {
                        if (other instanceof HitListener) {
                            ((HitListener) other).hit(ball);
                        }
                        if(other instanceof Brittle){
                            remove(other);
                        }
                        if(other instanceof PaintableCannon){
                            if(((PaintableCannon)other).equals(cannon2p)){
                                remove(cannon2p);
                                JOptionPane.showMessageDialog(this,"1p Wins");
                                System.exit(0);
                            }
                        }
                        ((Bounceable) ball).bounce(other);
                    }
                }
            }

            for (Bounded item : removeList) {
                remove(item);
            }
            repaint();
        });

        add(ball);
    }
    public void start1(){
        BounceableBall ball1 = new BounceableBall(cannon2p.shootingPoint(),20, Color.RED,false);

        ball1.addStartedActionListener(() -> {
            Vector newMotion = new Vector();
            double radianAngle = Math.toRadians(cannon2p.getCannonAngle());
            double totalPower = Math.sqrt((double)newMotion.getDX()*newMotion.getDX()+(double)newMotion.getDY()*newMotion.getDY());
            newMotion.set((int)(totalPower*Math.cos(radianAngle)),
                            (int)(totalPower*Math.sin(radianAngle)));
            ball1.setMotion(newMotion);
            super.add(ball1);
        });

        ball1.addMovedActionListener(() -> {
            List<Bounded> removeList = new LinkedList<>();

            Vector newMotion = ball1.getMotion();
            ball1.setDT(ballSpeed);
            newMotion.add(gravity);
            newMotion.add(windSpeed);
            
            ball1.setMotion(newMotion);

            if (ball1 instanceof Bounceable) {
                for (int j = 0; j < getCount(); j++) {
                    Bounded other = get(j);

                    if (ball1 != other && ball1.isCollision(other.getBounds())) {
                        if (other instanceof HitListener) {
                            ((HitListener) other).hit(ball1);
                        }
                        if(other instanceof Brittle){
                            remove(other);
                        }
                        if(other instanceof PaintableCannon){
                            if(((PaintableCannon)other).equals(cannon1p)){
                                remove(cannon1p);
                                JOptionPane.showMessageDialog(this,"2p Wins");
                                System.exit(0);
                            }
                        }
                        ((Bounceable) ball1).bounce(other);
                    }
                }
            }

            for (Bounded item : removeList) {
                remove(item);
            }
            repaint();
        });

        add(ball1);
    }
    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        List<Bounded> boundedList = getBoundedList();

        if(keyCode == KeyEvent.VK_W){
            cannon1p.repaintCannonAngle(-6, -90, 0);
            //1p 각도 올림
        } else if(keyCode == KeyEvent.VK_S){
            cannon1p.repaintCannonAngle(6, -90, 0);
        } else if(keyCode == KeyEvent.VK_A){
            Vector motion = new Vector(-6, 0);
            Point origin = ((Cannon)cannon1p).getLocation();
            Point origin2 = ((Cannon)cannon1p).getLocation();
            origin.translate(motion);
            cannon1p.setLocation(origin);
            for(Bounded other : boundedList){
                if(other instanceof Brittle && cannon1p.getBounds().isCollision(((Box)other).getBounds())){
                    cannon1p.setLocation(origin2);
                }
            }
        } else if(keyCode == KeyEvent.VK_D){
            Vector motion = new Vector(6, 0);
            Point origin = ((Cannon)cannon1p).getLocation();
            Point origin2 = ((Cannon)cannon1p).getLocation();
            origin.translate(motion);
            cannon1p.setLocation(origin);
            for(Bounded other : boundedList){
                if(other instanceof Brittle && cannon1p.getBounds().isCollision(((Box)other).getBounds())){
                    cannon1p.setLocation(origin2);
                }
            }
        } else if(keyCode == KeyEvent.VK_R){
            start();
        }else if(keyCode == KeyEvent.VK_T){
            clear(true);
        }else if(keyCode == KeyEvent.VK_UP){
            cannon2p.repaintCannonAngle(6, -180, -90);
        } else if(keyCode == KeyEvent.VK_DOWN){
            cannon2p.repaintCannonAngle(-6, -180, -90);
        } else if(keyCode == KeyEvent.VK_LEFT){
            Vector motion = new Vector(-6, 0);
            Point origin = ((Cannon)cannon2p).getLocation();
            Point origin2 = ((Cannon)cannon2p).getLocation();
            origin.translate(motion);
            cannon2p.setLocation(origin);
            for(Bounded other : boundedList){
                if(other instanceof Brittle && cannon2p.getBounds().isCollision(((Box)other).getBounds())){
                    cannon2p.setLocation(origin2);
                }
            }
        } else if(keyCode == KeyEvent.VK_RIGHT){
            Vector motion = new Vector(6, 0);
            Point origin = ((Cannon)cannon2p).getLocation();
            Point origin2 = ((Cannon)cannon2p).getLocation();
            origin.translate(motion);
            cannon2p.setLocation(origin);
            for(Bounded other : boundedList){
                if(other instanceof Brittle && cannon2p.getBounds().isCollision(((Box)other).getBounds())){
                    cannon2p.setLocation(origin2);
                }
            }
        } else if(keyCode == KeyEvent.VK_ENTER){
            start1();
        }else if(keyCode == KeyEvent.VK_SHIFT){
            clear(false);
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent event) {
    }

    @Override
    public void keyTyped(KeyEvent event) {
    }    

    @Override
    public void mouseDragged(MouseEvent event) {
    
    }

    @Override
    public void mouseMoved(MouseEvent event) {
    }

    public void setWindSpeed(int speed) {
        windSpeed.setDX(speed);
    }
    public void setBallSpeed(int ballSpeed){
        this.ballSpeed = ballSpeed;
    }
    public void setAngle(Vector motion){
        this.angle = motion;
    }
    public void setGravity(Vector gravity){
        this.gravity = gravity;
    }
    public int getBallSpeed(){
        return this.ballSpeed;
    }
}
