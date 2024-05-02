package main.java.game.realms;

import main.java.game.creatures.*;
import main.java.game.dice.*;

public class YellowRealm extends Realms{
    private Lion lion;

//============================Constructor============================================
    public YellowRealm(){
        super(RealmColor.YELLOW);
        lion=new Lion();
    }
//============================Methods================================================
    @Override
    public boolean attack(int diceValue, Creature creature) {
        if(lion.checkPossibleAttack(diceValue)){
            updateTotalRealmScore(diceValue);
            incrementTotalNumberOfAttacks();
            if(isRealmDefeated())
                closeRealm();
           //Give Rewards     
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
    public Lion getLion(){
        return lion;
    }

}
