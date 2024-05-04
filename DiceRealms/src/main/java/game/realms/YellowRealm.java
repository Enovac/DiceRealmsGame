package main.java.game.realms;

import main.java.game.collectibles.ArcaneBoost;
import main.java.game.collectibles.Bonus;
import main.java.game.collectibles.ElementalCrest;
import main.java.game.collectibles.Reward;
import main.java.game.collectibles.TimeWarp;
import main.java.game.creatures.*;
import main.java.game.dice.*;
import main.java.game.engine.Move;

public class YellowRealm extends Realms{
    private Lion lion;

//============================Constructor============================================
    public YellowRealm(){
        super(RealmColor.YELLOW,11);
        lion=new Lion();
    }
//============================Methods================================================
    @Override
    public boolean attack(int diceValue, Creature creature) {
        if(lion.checkPossibleAttack(diceValue)){
            incrementTotalNumberOfAttacks();
            int totalNumberOfAttacks=getTotalNumberOfAttacks();
            if(totalNumberOfAttacks==4||totalNumberOfAttacks==7||totalNumberOfAttacks==9)
                diceValue*=2;
            else if(totalNumberOfAttacks==11)
                diceValue*=3;    
            updateTotalRealmScore(diceValue);
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
    @Override
    public boolean isRewardAvailable() {
        Reward[] rewards=getRealmRewards();
        return rewards[getTotalNumberOfAttacks()]!=null;
    }

//============================G&S====================================================    
    public Lion getLion(){
        return lion;
    }
    @Override
    public Reward getReward() {
        Reward[] rewards=getRealmRewards();
        Reward recievedReward=rewards[getTotalNumberOfAttacks()];
        rewardClaimed(getTotalNumberOfAttacks());
        return recievedReward;
    }
    @Override
    public void setRealmRewards(Reward[] realmRewards) {
        Reward[] templateRewards=new Reward[]{null,null,new TimeWarp(),null,new Bonus(RealmColor.RED),
            new ArcaneBoost(),null,new ElementalCrest(),null,new Bonus(RealmColor.MAGENTA),null};

        for(int i=0;i<templateRewards.length;i++)
            realmRewards[i]=templateRewards[i];
    }
   

}
