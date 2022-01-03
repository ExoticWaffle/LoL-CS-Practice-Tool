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
public class Projectile {
    Circle shape;
    int damage;
    Color color;
    Direction direction;
    float x;
    float y;
    boolean destroyed=false;
    
    public Projectile(float x, float y, int radius, int damage, Color color, Direction direction){
        shape=new Circle(x,y,radius);
        this.x=x;
        this.y=y;
        this.damage=damage;
        this.color=color;
        this.direction=direction;
    }
    
    public void move(){
        x+=direction.getX()*20;
        y+=direction.getY()*20;
        shape.setCenterX(x);
        shape.setCenterY(y);
    }
    
    public void draw(Graphics g){
        g.setColor(color);
        g.fill(shape);
        g.setLineWidth(2);
        g.setColor(Color.black);
        g.draw(shape);
    }
    
    public float getX(){
        return x;
    }
    
    public float getY(){
        return y;
    }
    
    public int getDamage(){
        return damage;
    }
}
