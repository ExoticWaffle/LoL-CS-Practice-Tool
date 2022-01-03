/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ibcompsciia;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

import java.util.ArrayList;
/**
 *
 * @author Leo
 */
public class Player {
    boolean attackReady, isAttacking;
    final int aSpeed, damage, range;
    final float mSpeed;
    int coolDown;
    float x,y,destX,destY;
    Rectangle shape;
    Projectile projectile;
    Direction moveDirection;
    Minion target;
    
    public Player(int aSpeed, int damage, int range, float mSpeed){
        this.aSpeed=aSpeed;
        this.damage=damage;
        this.range=range;
        this.mSpeed=mSpeed;
        this.coolDown=aSpeed;
        
        x=360;
        y=800;
        
        shape=new Rectangle(x-30, y-30, 60, 60);
        projectile=null;
        target=null;
        
        attackReady=false;
        isAttacking=false;
    }
    
    
    public void update(float mX, float mY, boolean rmDown, boolean aPress, ArrayList<Minion> minions){
        if(rmDown){
            attackReady=false;
            isAttacking=false;
            this.setMoveDirection(mX, mY);
        }
        
        this.move();
        
        if(attackReady && coolDown>=aSpeed && isAttacking){
            this.attack();
            coolDown=0;
        }
        
        if(projectile!=null){
            projectile.move();
            projectileCheck();
        }
        
        
        target=setTarget(minions, mX, mY, aPress);
        
        coolDown++;
    }
    
    public void draw(Graphics g){
        if(projectile!=null){
            projectile.draw(g);
        }
        g.setLineWidth(3);
        g.setColor(Color.green);
        g.fill(shape);
        g.setColor(Color.black);
        g.draw(shape);
    }
    
    
    private void setMoveDirection(float mX, float mY){
        moveDirection=new Direction(x, y, mX, mY);
        destX=mX;
        destY=mY;
    }
    
    private void move(){
        if((x>=destX-3&&x<=destX+3) && (y>=destY-3&&y<=destY+3)){
            x=destX;
            y=destY;
            if(!isAttacking){
                attackReady=false;
            }
            moveDirection=null;
        }
        
        else if(moveDirection!=null && !isAttacking){
            x+=(mSpeed*moveDirection.getX());
            y+=(mSpeed*moveDirection.getY());
        }
        
        shape.setCenterX(x);
        shape.setCenterY(y);
    }

    private Minion setTarget(ArrayList<Minion> minions, float mX, float mY, boolean aPress){
        if(aPress){
            boolean minionInRange=false;
            attackReady=true;
            for(Minion minion:minions){
                if(minion.shape.contains(mX, mY)){
                    if(range==0){
                        if(shape.intersects(minion.shape)){
                            minionInRange=true;
                        }
                    }
                    else{
                        if(getMinionDist(minion)<=range){
                            minionInRange=true;
                        }
                    }
                    
                    if(minionInRange){
                        isAttacking=true;
                        return minion;
                    }
                    
                    setMoveDirection(mX, mY);
                    return minion;
                }                
           }
           setMoveDirection(mX, mY);
        }
        
        if(attackReady && target==null){
            Minion minion;
            if(range==0){
                minion=getClosestMinion(minions, 100);
            }
            else{
                minion=getClosestMinion(minions, range);
            }

            if(minion!=null){
                isAttacking=true;
            }
            return minion;
        }
        
        else if(target!=null && target.getHealth()<=0){
            isAttacking=false;
        }
        
        else if(attackReady){
            boolean minionInRange=false;
            if(range==0){
                if(shape.intersects(target.shape)){
                    minionInRange=true;
                }
            }
            else{
                if(getMinionDist(target)<=range){
                    minionInRange=true;

                }
            }
            
            if(minionInRange){
                moveDirection=null;
                isAttacking=true;
            }
            else{
                
                if((range!=0 && getMinionDist(target)>range+50) || (range==0 && getMinionDist(target)>range+100)){
                    isAttacking=false;
                    setMoveDirection(target.getX(), target.getY());
                }
            }

            return target;
        }
        return null;
    }
    
    private void attack(){
        float targetX=target.shape.getCenterX();
        float targetY=target.shape.getCenterY();
        Direction direction=new Direction(x, y, targetX, targetY);
        projectile=new Projectile(x,y,10,damage,Color.green,direction);
    }
    
    private void projectileCheck(){
        if(projectile.getX()>720 || projectile.getX()<0 || projectile.getY()>960
                || projectile.getY()<0){
            projectile=null;
        }
        
        else if(isTargetHit()){
            target.health-=projectile.getDamage();
            projectile=null;
            if(target.health<=0){
                Main.gm.csCount++;
            }
        }
    }
    
    private float getMinionDist(Minion minion){
        float xDist=minion.getX()-this.x;
        float yDist=minion.getY()-this.y;
        float distance=(float)Math.sqrt((Math.pow(xDist, 2))+(Math.pow(yDist, 2)));
        return distance;
    }
    
    private Minion getClosestMinion(ArrayList<Minion> minions, int range){
        float min=1000;
        Minion closest=null;
        for(Minion minion:minions){
            float distance=getMinionDist(minion);
            if(distance<=range && distance<min){
                min=distance;
                closest=minion;
            }
        }
        return closest;
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
