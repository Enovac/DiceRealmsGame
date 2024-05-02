package main.java.game.realms;

import main.java.game.creatures.*;

public class BlueRealm extends Realms{
    private Hydra hydra;
    private int totalDefeatedHeads;
//============================Constructor============================================
    public BlueRealm(){
         super(RealmColor.BLUE);
         hydra=new Hydra();
         hydra.spawnHydra();
    }
//============================Methods================================================
    @Override
    public boolean attack(int diceValue,Creature creature){
        if(hydra.checkPossibleAttack(diceValue)){
            if(hydra.killHead()&&!hydra.isRespawned())
                hydra.respawnHydra();
            totalDefeatedHeads++;
            incrementTotalNumberOfAttacks();
            updateTotalRealmScore(totalDefeatedHeads);
            if(isRealmDefeated())
                closeRealm();
            //Give user his rewards,Do I increment total attacks
            return true;
        }
        return false;

        }
    @Override
    public boolean isRealmDefeated() {
        return getTotalNumberOfAttacks()==11;
    }    
    @Override    
    public void updateTotalRealmScore(int totalDefeatedHeads){
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
