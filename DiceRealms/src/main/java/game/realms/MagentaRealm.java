package main.java.game.realms;

import main.java.game.creatures.*;

public class MagentaRealm extends Realms{
    private Phoenix phoenix;

//============================Constructor============================================
    public MagentaRealm(){
        super(RealmColor.MAGENTA);
        phoenix=new Phoenix();
    }
//============================Methods================================================
    @Override
    public  boolean attack(int diceValue,Creature creature){
        if(phoenix.checkPossibleAttack(diceValue)){
            phoenix.setHealth(diceValue);
            updateTotalRealmScore(diceValue);
            incrementTotalNumberOfAttacks();
            if(isRealmDefeated())
                closeRealm();
            //Give Rewards 
            return true;
        }
        return false;
    }
    @Override
    public void updateTotalRealmScore(int value) {
        int oldScore=getTotalRealmScore();
        setTotalRealmScore(value+oldScore);
    }
    @Override
    public boolean isRealmDefeated() {
        return getTotalNumberOfAttacks()==11;
    }

//============================G&S====================================================   
    public Phoenix getPhoenix(){
        return phoenix;
    }

}
