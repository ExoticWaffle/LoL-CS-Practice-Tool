/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ibcompsciia;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

/**
 *
 * @author Leo
 */


public class RangeSlider{
    int min, max;
    double segment;
    int size;
    int x;
    int y;
    Rectangle rail;
    Circle slider;
    boolean sliding;
    
    public RangeSlider(int x, int y, int min, int max, int size){
        this.min=min;
        this.max=max;
        this.size=size;
        this.x=x;
        this.y=y;
        segment=((double)size)/(max-min);
        sliding=false;
        
        rail=new Rectangle(x, y, size, 6);
        slider=new Circle(x, y+3, 9);
        
        class RangeSizeException extends Exception{
            public RangeSizeException(String message){
                super(message);
            }
        }
    }
    
    public void update(float mX, float mY, boolean lmDown){
        isClicked(mX, mY, lmDown);
        slide(mX);
    }
    
    private void isClicked(float mouseX, float mouseY, boolean mbDown){
        if(mbDown && (slider.contains(mouseX, mouseY) || rail.contains(mouseX, mouseY))){
            sliding=true;
        }
        else if(!mbDown && sliding){
            sliding=false;
        }
    }
    
    private void slide(float mouseX){
        if(sliding){
            if(mouseX>=x && mouseX<=x+size){
                slider.setCenterX(mouseX);
            }
            else if(mouseX<x){
                slider.setCenterX(x);
            }
            else if(mouseX>x+size){
                slider.setCenterX(x+size);
            }
        }
    }
    
    public void draw(Graphics g){
        g.setLineWidth(3);
        g.setColor(Color.black);
        g.draw(rail);
        g.setColor(Color.white);
        g.fill(slider);
        g.setColor(Color.black);
        g.setLineWidth(2);
        g.draw(slider);
    }
    
    public int getValue(){
        return (int)(min+(slider.getCenterX()-x)/segment);
    }
}
