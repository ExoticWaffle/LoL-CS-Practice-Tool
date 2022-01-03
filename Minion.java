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
public class Minion {
    float x,y, destX,destY;
    int health, range;
    final int fullHealth;
    final float speed;
    Direction moveDirection;
    Rectangle shape;
    Rectangle healthBar;
    String type;
    
    public Minion(float x, float y, int size, int health, int range){
        this.x=x;
        this.y=y;
        this.health=health;
        this.range=range;
        this.shape=new Rectangle(x-(size/2),y-(size/2),size,size);
        this.healthBar=new Rectangle((x-(size/2))-5, (y-(size/2))-20, size+10, 5);
        this.moveDirection=getMoveDirection();
        
        this.speed=0.8f;
        this.fullHealth=health;
        this.destX=360;
        this.destY=900;
    }
    
    public void update(Turret turret){
        move(turret);
    }
    
    public void draw(Graphics g){
        g.setColor(Color.red);
        g.fill(shape);
        g.setColor(Color.black);
        g.setLineWidth(2);
        g.draw(shape);
        g.setColor(Color.green);
        g.fill(healthBar);
        g.setColor(Color.red);
        g.fill(depletedHealth());
    }
    
    private Rectangle depletedHealth(){
        float lostHealth=fullHealth-health;
        float lostPercent=lostHealth/fullHealth;
        float width=healthBar.getWidth()*lostPercent;
        float x=healthBar.getX()+(healthBar.getWidth()-width);
        
        return new Rectangle(x, healthBar.getY(), width, 5);
    }
    
    private void move(Turret turret){
        if(range!=0){
            float xDist=destX-x;
            float yDist=destY-y;
            double distance=Math.sqrt(Math.pow(xDist,2)+Math.pow(yDist, 2));
            if(distance<range){
                moveDirection=null;
            }
        }
        else{
            if(shape.intersects(turret.shape)){
                moveDirection=null;
            }
        }
        
        if(moveDirection!=null){
            x+=(speed*moveDirection.getX());
            y+=(speed*moveDirection.getY());
        }
        
        shape.setCenterX(x);
        shape.setCenterY(y);
        healthBar.setCenterX(x);
        healthBar.setCenterY(y-25);
    }
    
    private Direction getMoveDirection(){
        if(x>=270&&x<=450){
            return new Direction(0,0,0,1);
        }
        else if(x<360){
            return new Direction(x,y,270,900);
        }
        else{
            return new Direction(x,y,450,900);
        }
    }
    
    
    public float getX(){
        return x;
    }
    
    public float getY(){
        return y;
    }
    
    public int getHealth(){
        return health;
    }
    
    public String getType(){
        return type;
    }
    
}
