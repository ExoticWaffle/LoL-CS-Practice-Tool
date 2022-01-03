/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ibcompsciia;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Leo
 */
public class GameManager {
    int playerASpeed, playerDamage, playerRange;
    float playerMSpeed;
    
    //Menu state
    public void setMSpeed(float mSpeed){
        playerMSpeed=mSpeed;
    }
    public void setASpeed(int aSpeed){
        playerASpeed=aSpeed;
    }
    public void setDamage(int damage){
        playerDamage=damage;
    }
    public void setRange(int range){
        playerRange=range;
    }
    
    
    //Play state
    int csCount, totalCS;
    
    public float getMSpeed(){
        return playerMSpeed;
    }
    public int getASpeed(){
        return playerASpeed;
    }
    public int getDamage(){
        return playerDamage;
    }
    public int getRange(){
        return playerRange;
    }
    
    public ArrayList<Minion> minionList(ArrayList<Minion> minions){
        ArrayList<Minion> remove=new ArrayList<>();
        for(Minion minion:minions){
            if(minion.health<=0){
                remove.add(minion);
                totalCS++;
            }
        }
        
        for(Minion minion:remove){
            minions.remove(minion);
        }
        
        if(minions.isEmpty()){
            minions=spawnMinions(minions, 3, "melee");
            minions=spawnMinions(minions, 2, "siege");
            minions=spawnMinions(minions, 4, "caster");
        }
        return minions;
    }
    
    private ArrayList<Minion> spawnMinions(ArrayList<Minion> minions, int upBound, String type){
        Random rand=new Random();
        int minionNum=rand.nextInt(upBound)+1;
        float x=360-(((float)minionNum-1)/2*100);
        for(int i=0; i<minionNum; i++){
            switch(type){
                case "melee":
                    minions.add(new MeleeMinion(x, -20));
                    break;
                case "siege":
                    minions.add(new SiegeMinion(x, -160));
                    break;
                case "caster":
                    minions.add(new CasterMinion(x, -295));
                    break;
            }
            x+=100;
        }
        return minions;
    }
    
    //stats screen
    public int getCSCount(){
        return csCount;
    }
    
    public int getMissed(){
        return totalCS-csCount;
    }
    
    public double getPercent(){
        return (double)((int)(((double)csCount/totalCS)*1000))/10;
    }
}
