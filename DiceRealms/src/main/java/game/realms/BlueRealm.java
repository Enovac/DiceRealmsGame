package main.java.game.realms;

import main.java.game.creatures.*;
import main.java.game.dice.Dice;

public class BlueRealm extends Realms{
    private Hydra hydra;
    private int totalDefeatedHeads;
//============================Constructor============================================
    public BlueRealm(){
         super(RealmColor.BLUE);
    }
//============================Methods================================================
    public boolean attack(Dice dice,Creature creature){
        int diceValue=dice.getDiceValue();
        if(hydra.checkPossibleAttack(diceValue)){
            if(hydra.killHead()){
                if(hydra.isRespawned())
                    closeRealm();
                else
                    hydra.respawnHydra();    
            }
            totalDefeatedHeads++;
            updateTotalRealmScore();
            //Give user his rewards,Do I increment total attacks
            return true;
        }
        return false;
        }
    public void updateTotalRealmScore(){
        switch(totalDefeatedHeads){
            case 1:setTotalRealmScore(1);break;
            case 2:setTotalRealmScore(3);break;
            case 3:setTotalRealmScore(6);break;
            case 4:setTotalRealmScore(10);break;
            case 5:setTotalRealmScore(15);break;
            case 6:setTotalRealmScore(21);break;
            case 7:setTotalRealmScore(28);break;
            case 8:setTotalRealmScore(36);break;
            case 9:setTotalRealmScore(45);break;
            case 10:setTotalRealmScore(55);break;
            case 11:setTotalRealmScore(66);break;
        }
    }   
//============================G&S====================================================
    public Hydra getHydra(){
        return hydra;
    }

    

}
