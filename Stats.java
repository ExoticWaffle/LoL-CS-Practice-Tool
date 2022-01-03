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
public class Stats extends BasicGameState{
    
    Button quitButton;
    
    TrueTypeFont headerFont;
    TrueTypeFont labelFont;
    public Stats(int state){
        
    }
    
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
        quitButton=new Button(260, 800, 200, 60, "QUIT");
        
        headerFont=new TrueTypeFont(new Font("SansSerif", Font.BOLD, 48), true);
        labelFont=new TrueTypeFont(new Font("SansSerif", Font.PLAIN, 22), true);
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
        g.setColor(Color.black);
        g.setBackground(Color.white);
        g.setFont(headerFont);
        g.drawString("Session Stats", 360-headerFont.getWidth("Session Stats")/2, 100);
        
        g.setFont(labelFont);
        g.drawString("You finished off:", 90, 300);
        g.drawString("You missed:", 90, 450);
        g.drawString("You successfully CS'd:", 90, 600);
        
        String csCountSTR=Integer.toString(Main.gm.getCSCount())+" minions";
        String csMissedSTR=Integer.toString(Main.gm.getMissed())+" minions";
        String csPercentSTR=Double.toString(Main.gm.getPercent())+"% of the time";
        
        g.drawString(csCountSTR, 630-labelFont.getWidth(csCountSTR), 300);
        g.drawString(csMissedSTR, 630-labelFont.getWidth(csMissedSTR), 450);
        g.drawString(csPercentSTR, 630-labelFont.getWidth(csPercentSTR), 600);
        
        quitButton.draw(g);
    }
    
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
        float mX=Mouse.getX();
        float mY=960-Mouse.getY();
        boolean lmb=Mouse.isButtonDown(0);
        if(quitButton.isClicked(mX, mY, lmb)){
            System.exit(0);
        }
    }
    
    public int getID(){
        return 2;
    }
}
