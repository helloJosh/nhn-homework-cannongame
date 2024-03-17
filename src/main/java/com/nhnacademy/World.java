package com.nhnacademy;

import java.util.List;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JPanel;
import com.nhnacademy.component.Bounded;
import com.nhnacademy.component.Paintable;

/**
 * component 추가 삭제 -> 편의 메서드 선언
 */
public class World extends JPanel{
    final List<Bounded> boundedList = new LinkedList<>();

    public void add(Bounded object){
        if(object == null){
            throw new IllegalArgumentException("world added object is null");
        }
        boundedList.add(object);
        repaint();
    }

    public void remove(Bounded item){
        boundedList.remove(item);
    }
    @Override
    public void remove(int index){
        boundedList.remove(index);
    }
    public int getCount(){
        return boundedList.size();
    }
    public Bounded get(int index){
        return boundedList.get(index);
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);

        for(Bounded item : boundedList){
            if(item instanceof Paintable){
                ((Paintable)item).paint(g);
            }
        }
    }
    public List<Bounded> getBoundedList(){
        return new LinkedList<>(boundedList);
    }
}
