package com.nhnacademy;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nhnacademy.utility.Vector;

public class CannonGame extends JFrame {
    static final int FRAME_WIDTH = 1300;
    static final int FRAME_HEIGHT = 700;
    static final int MIN_RADIUS = 10;
    static final int MAX_RADIUS = 50;
    static final int MIN_WIDTH = 10;
    static final int MAX_WIDTH = 50;
    static final int MIN_HEIGHT = 10;
    static final int MAX_HEIGHT = 50;
    static final int FIXED_BALL_COUNT = 0;
    static final int FIXED_BOX_COUNT = 3;
    static final int BOUNDED_BALL_COUNT = 5;
    static final int MIN_DELTA = 5;
    static final int MAX_DELTA = 7;
    static final int MAX_MOVE_COUNT = 0;
    static final int DT = 10;
    static final int BLOCK_WIDTH = 80;
    static final Color[] COLOR_TABLE = {
            Color.BLACK,
            Color.RED,
            Color.BLUE,
            Color.YELLOW
    };

    Logger logger = LogManager.getLogger();

    CannonWorld world;

    public CannonGame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        world = new CannonWorld(300, 0, FRAME_WIDTH - 300, FRAME_HEIGHT - 200);
        world.setDT(DT);
        world.setBackground(Color.WHITE);
        add(world);

        addBallSpeedSlider(10, 80, 280, 100);
        addGravitySlider(10, 150+80, 280, 100);
        addWindSpeedSlider(10, 300+80, 280, 100);
        add1pGameExplaination(450, 490, 300,400);
        add2pGameExplaination(1000, 490, 300,400);
    }

    public void start() {
        setVisible(true);
        setEnabled(true);

        world.run();
    }
    public void add1pGameExplaination(int x, int y, int width, int height){
        JLabel ballSpeedLabel1 = new JLabel("1p 각도 조절 : W S");
        JLabel ballSpeedLabel2 = new JLabel("1p 좌우이동 : A D");
        JLabel ballSpeedLabel3 = new JLabel("1p 대포 쏘기 : R");
        JLabel ballSpeedLabel4 = new JLabel("1p 공 초기화 : T");
        ballSpeedLabel1.setBounds(x, y-170, width, height);
        ballSpeedLabel2.setBounds(x, y-140, width, height);
        ballSpeedLabel3.setBounds(x, y-110, width, height);
        ballSpeedLabel4.setBounds(x, y-80, width, height);
        add(ballSpeedLabel1);
        add(ballSpeedLabel2);
        add(ballSpeedLabel3);
        add(ballSpeedLabel4);
    }
    public void add2pGameExplaination(int x, int y, int width, int height){
        JLabel ballSpeedLabel1 = new JLabel("2p 각도 조절 : 위 아래 방향키");
        JLabel ballSpeedLabel2 = new JLabel("2p 좌우이동 : 좌 우 방향키");
        JLabel ballSpeedLabel3 = new JLabel("2p 대포 쏘기 : ENTER");
        JLabel ballSpeedLabel4 = new JLabel("2p 공 초기화 : SHIFT");
        ballSpeedLabel1.setBounds(x, y-170, width, height);
        ballSpeedLabel2.setBounds(x, y-140, width, height);
        ballSpeedLabel3.setBounds(x, y-110, width, height);
        ballSpeedLabel4.setBounds(x, y-80, width, height);
        add(ballSpeedLabel1);
        add(ballSpeedLabel2);
        add(ballSpeedLabel3);
        add(ballSpeedLabel4);
    }
    public void addBallSpeedSlider(int x, int y, int width, int height){
        int ballSpeedMaxValue = 400;
        JLabel ballSpeedLabel = new JLabel("공 속도");
        ballSpeedLabel.setBounds(x, y-50, width, height);

        JSlider ballSpeed = new JSlider(0, ballSpeedMaxValue, 120);
        ballSpeed.setBounds(x, y, width, height);

        ballSpeed.setPaintTrack(true);
        ballSpeed.setPaintTicks(true);
        ballSpeed.setPaintLabels(true);
        ballSpeed.setMajorTickSpacing(50);
        ballSpeed.setMinorTickSpacing(25);

        ballSpeed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                world.setBallSpeed(ballSpeedMaxValue-ballSpeed.getValue());
            }

        });
        add(ballSpeed);
        add(ballSpeedLabel);

    }
    public void addGravitySlider(int x, int y, int width, int height){
        JLabel gravityLabel = new JLabel("중력");
        gravityLabel.setBounds(x, y-50, width, height);

        JSlider gravitySlider = new JSlider(0, 10, 1);

        gravitySlider.setBounds(x, y, width, height);

        gravitySlider.setPaintTrack(true);
        gravitySlider.setPaintTicks(true);
        gravitySlider.setPaintLabels(true);

        gravitySlider.setMajorTickSpacing(2);
        gravitySlider.setMinorTickSpacing(1);

        gravitySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Vector newMotion = new Vector(0,gravitySlider.getValue());
                world.setGravity(newMotion);
            }
        });

        add(gravitySlider);
        add(gravityLabel);

    }
    public void addWindSpeedSlider(int x, int y, int width, int height){
        JLabel windSpeedLabel = new JLabel("바람 속도");
        windSpeedLabel.setBounds(x, y-50, width, height);

        JSlider windSpeed = new JSlider(-10, 10, 0);

        windSpeed.setBounds(x, y, width, height);

        windSpeed.setPaintTrack(true);
        windSpeed.setPaintTicks(true);
        windSpeed.setPaintLabels(true);

        windSpeed.setMajorTickSpacing(5);
        windSpeed.setMinorTickSpacing(2);

        windSpeed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                world.setWindSpeed(windSpeed.getValue());
            }
        });

        add(windSpeed);
        add(windSpeedLabel);

    }
    public static void main(String[] args) {
        CannonGame frame = new CannonGame();

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        frame.start();
    }
}