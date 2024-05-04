package main.java.game.realms;

import main.java.game.creatures.*;
import main.java.game.dice.*;
import main.java.game.engine.Move;

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
    @Override
    public Move[] getAllPossibleMoves() {
        if(!isRealmAccessible())
            return null;
            
        Move[] moves =new Move[6];
        for(int i=0;i<moves.length;i++)
             moves[i]=getPossibleMovesForADie(i+1,RealmColor.YELLOW)[0];
        return moves;     

    }
    @Override
    public Move[] getPossibleMovesForADie(int diceValue, RealmColor colorOfDice) {
        if(lion.checkPossibleAttack(diceValue)){
            YellowDice tempDice=new YellowDice(diceValue);
            return new Move[]{new Move(tempDice,lion)};
        }
        return null;
    }

//============================G&S====================================================    
    public Lion getLion(){
        return lion;
    }
   

}
