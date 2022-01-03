/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ibcompsciia;

/**
 *
 * @author Leo
 */
public class Direction {
    double x;
    double y;
    public Direction(float x1, float y1, float x2, float y2){
        float xDist=x2-x1;
        float yDist=y2-y1;
        double distance=Math.sqrt(Math.pow(xDist,2)+Math.pow(yDist, 2));
        
        this.x=xDist/distance;
        this.y=yDist/distance;
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
}
