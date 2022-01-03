/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ibcompsciia;

import java.util.ArrayList;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

/**
 *
 * @author Leo
 */
public class Turret {
    final int range;
    final float x,y;
    int coolDown;
    Ellipse shape;
    Circle rangeCircle;
    Projectile projectile;
    
    Minion target;
    
    public Turret(){
        x=360;
        y=900;
        shape=new Ellipse(x, y, 90, 60);
        range=450;
        rangeCircle=new Circle(360,900,range);
        
        projectile=null;
        coolDown=0;
    }
    
    public void update(ArrayList<Minion> minions){
        if(target==null){
            target=setTarget(minions);
        }

        
        if(target!=null && coolDown>72){
            attack();
            coolDown=0;
        }
        
        if(projectile!=null){
            projectile.move();
            projectileCheck();
        }
        
        coolDown++;
    }
    
    public void draw(Graphics g){
        if(projectile!=null){
            projectile.draw(g);
        }
        g.setColor(Color.cyan);
        g.fill(shape);
        g.setColor(Color.black);
        g.setLineWidth(3);
        g.draw(shape);
        g.setLineWidth(2);
        g.draw(rangeCircle);
    }
    
    public Minion setTarget(ArrayList<Minion> minions){
        float siegeMin=1000,meleeMin=1000,casterMin=1000;
        Minion siegeTarget=null,meleeTarget=null,casterTarget=null;
        for(Minion minion: minions){
            float distance=getMinionDist(minion);
            if((rangeCircle.intersects(minion.shape) || rangeCircle.contains(minion.shape))){
                switch(minion.getType()){
                    case "siege":
                        if(distance<siegeMin){
                            siegeTarget=minion;
                            siegeMin=distance;
                        }
                        break;
                    
                    case "melee":
                        if(distance<meleeMin){
                            meleeTarget=minion;
                            meleeMin=distance;
                        }
                        break;
                    
                    case "caster":
                        if(distance<casterMin){
                        casterTarget=minion;
                        casterMin=distance;
                    }
                    break;
                }
            }
        }
        if(siegeTarget!=null){
            return siegeTarget;
        }
        else if(meleeTarget!=null){
            return meleeTarget;
        }
        else{
            return casterTarget;
        }
    }
    
    private void attack(){
        float targetX=target.shape.getCenterX();
        float targetY=target.shape.getCenterY();
        Direction direction=new Direction(x, y, targetX, targetY);
        projectile=new Projectile(x,y,10,1,Color.cyan,direction);
    }
    
    private void projectileCheck(){
        if(projectile.getX()>720 || projectile.getX()<0 || projectile.getY()>960
                || projectile.getY()<0){
            projectile=null;
        }
        
        else if(isTargetHit()){
            switch(target.getType()){
                case "caster":
                    target.health-=207;
                    break;
                
                case "melee":
                    target.health-=215;
                    break;
                
                case "siege":
                    target.health-=128;
                    break;
            }
            
            projectile=null;
            if(target.getHealth()<=0){
                target=null;
            }
        }
    }
    
    private float getMinionDist(Minion minion){
        float xDist=minion.getX()-this.x;
        float yDist=minion.getY()-this.y;
        float distance=(float)Math.sqrt((Math.pow(xDist, 2))+(Math.pow(yDist, 2)));
        return distance;
    }
    
    private boolean isTargetHit(){
        if(target!=null){
            if(target.shape.intersects(projectile.shape) || target.shape.contains(projectile.shape)){
                return true;
            }
        }
        return false;
    }
}
