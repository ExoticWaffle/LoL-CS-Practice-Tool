/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ibcompsciia;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.geom.*;

import java.awt.Font;


/**
 *
 * @author Leo
 */
public class Play extends BasicGameState{
    
    Player player;
    ArrayList<Minion> minions;
    Turret turret;
    GameManager playGM;
    boolean paused;
    boolean escHeld;
    long timeElapsed;
    int secondsElapsed;
    
    Rectangle pauseWindow;
    Button resumeButton;
    Button menuButton;
    Button quitButton;
    
    TrueTypeFont headerFont;
    
    int playTime;
    
    public Play(int state){
        
    }
    
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
        player=new Player(Main.gm.getASpeed(), Main.gm.getDamage(), Main.gm.getRange(), Main.gm.getMSpeed());
        turret=new Turret();
        minions=new ArrayList<>();
        paused=false;
        escHeld=false;
        playTime=120;
        
        pauseWindow=new Rectangle(180, 240, 360, 480);
        resumeButton=new Button(270, 380, 180, 60, "RESUME");
        menuButton=new Button(270, 480, 180, 60, "MAIN MENU");
        quitButton=new Button(270, 580, 180, 60, "QUIT");
        
        headerFont=new TrueTypeFont(new Font("SansSerif", java.awt.Font.BOLD, 36), true);
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
        g.setColor(Color.black);
        g.setBackground(Color.white);
        turret.draw(g);
        player.draw(g);
        
        for(Minion minion:minions){
            minion.draw(g);
        }
        
        
        
        g.setColor(Color.black);
        g.setFont(headerFont);
        g.drawString("CS count: "+String.format("%02d", Main.gm.getCSCount()), 0, 0);
        g.drawString(String.format("%02d", (playTime-secondsElapsed)/60)+":"+String.format("%02d", (playTime-secondsElapsed)%60), 620, 5);
        
        if(paused){
            g.setColor(Color.white);
            g.fill(pauseWindow);
            g.setColor(Color.black);
            g.setLineWidth(3);
            g.draw(pauseWindow);
            
            g.setFont(headerFont);
            g.drawString("Pause Menu", 360-headerFont.getWidth("Pause Menu")/2, 280);
            resumeButton.draw(g);
            menuButton.draw(g);
            quitButton.draw(g);
        }
    }
    
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
        float mX=Mouse.getX();
        float mY=960-Mouse.getY();
        boolean lmb=Mouse.isButtonDown(0);
        boolean rmb=Mouse.isButtonDown(1);
        boolean aPress=Keyboard.isKeyDown(Input.KEY_A);
        
        if(Keyboard.isKeyDown(Input.KEY_ESCAPE)&&!escHeld){
            paused=!paused;
            escHeld=true;
        }
        else if(!Keyboard.isKeyDown(Input.KEY_ESCAPE)){
            escHeld=false;
        }
        
        if(!paused){
            player.update(mX, mY, rmb, aPress, minions);
            turret.update(minions);

            for(Minion minion:minions){
                minion.update(turret);
            }

            minions=Main.gm.minionList(minions);
            
            secondsElapsed=(int)(timeElapsed/60);
            if(secondsElapsed>=playTime){
                sbg.enterState(2);
            }
            timeElapsed++;
        }
        
        else{
            if(resumeButton.isClicked(mX, mY, lmb)){
                paused=false;
            }
            
            if(menuButton.isClicked(mX, mY, lmb)){
                paused=false;
                sbg.enterState(0);
            }
            
            if(quitButton.isClicked(mX, mY, lmb)){
                System.exit(0);
            }
        }
    }
    
    public int getID(){
        return 1;
    }
}
