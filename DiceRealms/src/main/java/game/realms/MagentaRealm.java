package main.java.game.realms;

import main.java.game.collectibles.*;
import main.java.game.creatures.*;
import main.java.game.dice.*;
import main.java.game.engine.Move;

public class MagentaRealm extends Realms{
    private Phoenix phoenix;

//============================Constructor============================================
    public MagentaRealm(){
        super(RealmColor.MAGENTA,11);
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
    @Override
    public Move[] getAllPossibleMoves() {
        if(!isRealmAccessible())
            return null;
        int minimumAttackValue=phoenix.getMinimumAttackValue();
        int moveArraySize=6-minimumAttackValue+1;//+1 to include the minimumAttack Value
        Move[] moves=new Move[moveArraySize];
        for(int i=0;i<moves.length;i++,minimumAttackValue++)
            moves[i]=getPossibleMovesForADie(minimumAttackValue,RealmColor.MAGENTA)[0];
        return  moves;
    }
    @Override
    public Move[] getPossibleMovesForADie(int diceValue, RealmColor colorOfDice) {
        if(phoenix.checkPossibleAttack(diceValue)){
            MagentaDice tempDice=new MagentaDice(diceValue);
            return new Move[]{new Move(tempDice,phoenix)};
        }
        return null;
    }
    @Override
    public boolean isRewardAvailable() {
        Reward[] rewards=getRealmRewards();
        return rewards[getTotalNumberOfAttacks()]!=null;
    }

//============================G&S====================================================   
    public Phoenix getPhoenix(){
        return phoenix;
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
        Reward[] templateRewards=new Reward[]{null,null,new TimeWarp(),new Bonus(RealmColor.GREEN),new ArcaneBoost(),
        new Bonus(RealmColor.RED),new ElementalCrest(),new TimeWarp(),new Bonus(RealmColor.BLUE),new Bonus(RealmColor.YELLOW),
        new ArcaneBoost()};

        for(int i=0;i<templateRewards.length;i++)
            realmRewards[i]=templateRewards[i];
    }
   

}
 