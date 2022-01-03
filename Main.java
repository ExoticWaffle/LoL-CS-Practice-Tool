/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ibcompsciia;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Main extends StateBasedGame{
    
    public static final String winName="CS Practice Tool";
    public static final int menu=0;
    public static final int play=1;
    public static final int stats=2;
    public static GameManager gm;
    
    public Main(String winName){
        super(winName);
        gm=new GameManager();
        this.addState(new Menu(menu));
        this.addState(new Play(play));
        this.addState(new Stats(stats));
    }
    
    public void initStatesList(GameContainer gc) throws SlickException{
        this.getState(menu).init(gc, this);
        this.getState(stats).init(gc, this);
        this.enterState(menu);
    }
    
    public static void main(String[] args) {
        AppGameContainer appgc;
        try{
            appgc=new AppGameContainer(new Main(winName));
            appgc.setDisplayMode(720, 960, false);
            appgc.setTargetFrameRate(60);
            appgc.setShowFPS(false);
            appgc.start();
        }catch(SlickException e){
            e.printStackTrace();
        }
    }
    
}
