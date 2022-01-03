/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ibcompsciia;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.lwjgl.input.Mouse;
import java.awt.Font;

/**
 *
 * @author Leo
 */
public class Menu extends BasicGameState{
    
    Button startButton;
    Button quitButton;
    
    RangeSlider damageSlider;
    RangeSlider atkRangeSlider;
    RangeSlider atkSpeedSlider;
    RangeSlider mSpeedSlider;
    
    TrueTypeFont headerFont;
    TrueTypeFont labelFont;
    
    public Menu(int state){
        
        
    }
    
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
        startButton=new Button(125,800, 200, 60, "START");
        quitButton=new Button(395, 800, 200, 60, "QUIT");
        
        atkSpeedSlider=new RangeSlider(440, 312, 0, 100, 150);
        damageSlider=new RangeSlider(440, 412, 0, 100, 150);
        atkRangeSlider=new RangeSlider(440, 512, 0, 100, 150);
        mSpeedSlider=new RangeSlider(440, 612, 0, 100, 150);
        
        headerFont=new TrueTypeFont(new Font("SansSerif", Font.BOLD, 48), true);
        labelFont=new TrueTypeFont(new Font("SansSerif", Font.PLAIN, 22), true);
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
        g.setColor(Color.black);
        g.setBackground(Color.white);
        
        g.setFont(headerFont);
        g.drawString("CS Practice Tool", 360-headerFont.getWidth("CS Practice Tool")/2, 100);
        
        g.setFont(labelFont);
        g.drawString("Attack Speed", 90, 300);
        g.drawString("Attack Damage", 90, 400);
        g.drawString("Attack Range", 90, 500);
        g.drawString("Movement Speed", 90, 600);
        
        atkSpeedSlider.draw(g);
        damageSlider.draw(g);
        atkRangeSlider.draw(g);
        mSpeedSlider.draw(g);
        
        g.drawString(Integer.toString(atkSpeedSlider.getValue()), 610, 300);
        g.drawString(Integer.toString(damageSlider.getValue()), 610, 400);
        g.drawString(Integer.toString(atkRangeSlider.getValue()), 610, 500);
        g.drawString(Integer.toString(mSpeedSlider.getValue()), 610, 600);
        
        
        startButton.draw(g);
        quitButton.draw(g);
    }
    
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
        float mX=Mouse.getX();
        float mY=960-Mouse.getY();
        boolean lmb=Mouse.isButtonDown(0);
        
        atkSpeedSlider.update(mX, mY, lmb);
        damageSlider.update(mX, mY, lmb);
        atkRangeSlider.update(mX, mY, lmb);
        mSpeedSlider.update(mX, mY, lmb);
        
        if(startButton.isClicked(mX, mY, lmb)){
            Main.gm.setASpeed((int)(60*(2.1-0.013*atkSpeedSlider.getValue())));
            Main.gm.setDamage((int)(40+0.6*damageSlider.getValue()));
            Main.gm.setMSpeed((float)(2.08+0.0125*mSpeedSlider.getValue()));
            
            if(atkRangeSlider.getValue()==0){
                Main.gm.setRange(0);
            }
            else{
                Main.gm.setRange((int)(175+1.75*atkRangeSlider.getValue()));
            }
            
            sbg.getState(1).init(gc, sbg);
            
            sbg.enterState(1);
        }
        
        if(quitButton.isClicked(mX, mY, lmb)){
            System.exit(0);
        }
    }
    
    public int getID(){
        return 0;
    }
}
