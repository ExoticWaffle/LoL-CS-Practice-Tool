/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ibcompsciia;


import org.newdawn.slick.geom.*;
import org.newdawn.slick.*;
import java.awt.Font;

/**
 *
 * @author Leo
 */
public class Button {
    RoundedRectangle shape;
    String text;
    
    public Button(int x, int y, int width, int height, String text){
        shape=new RoundedRectangle(x, y, width, height, 10);
        this.text=text;
    }
    
    public void draw(Graphics g){
        g.setLineWidth(3);
        g.draw(shape);
        TrueTypeFont font=new TrueTypeFont(new Font("SansSerif", Font.BOLD, 16), true);
        g.setFont(font);
        g.drawString(text, shape.getCenterX()-font.getWidth(text)/2, shape.getCenterY()-font.getHeight()/2);
    }
    
    public boolean isClicked(float mX, float mY, boolean mbDown){
        if(shape.contains(mX,mY) && mbDown){
            return true;
        }
        return false;
    }
}
