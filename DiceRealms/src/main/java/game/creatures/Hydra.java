package main.java.game.creatures;

import main.java.game.realms.RealmColor;

public class Hydra extends Creature{
    private int remainingHeads;
    private int totalHeads;
    private boolean isRespawned;
//============================Constructor============================================
    public Hydra(){
        super(RealmColor.BLUE);
    }

    
    public void spawnHydra(){
        remainingHeads=5;
        totalHeads=5;
    //Go Over hydraRewards and put in each one reards
    }
    public void respawnHydra(){
        remainingHeads=6;
        totalHeads=6;
         isRespawned=true;
        //Go Over hydraRewards and put in each one reards
    }
//============================Methods================================================
    @Override
    public boolean checkPossibleAttack(int diceValue){
        int minValue=(totalHeads-remainingHeads)+1;
        return diceValue>=minValue;
    }
    public boolean killHead(){
      remainingHeads-=1;
      return remainingHeads==0;
    }  
    
//============================G&S====================================================
    public boolean isRespawned(){
         return isRespawned;
    }
    public int getRemainingNumberOfHeads(){
        return remainingHeads;
    }
    @Override
    public int getMinimumAttackValue(){
        return (totalHeads-remainingHeads)+1;
    }


}











