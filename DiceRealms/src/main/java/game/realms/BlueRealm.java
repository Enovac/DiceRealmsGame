package main.java.game.realms;

import main.java.game.collectibles.*;
import main.java.game.creatures.*;
import main.java.game.dice.BlueDice;
import main.java.game.engine.*;

public class BlueRealm extends Realms{
    private Hydra hydra;
    private int totalDefeatedHeads;
//============================Constructor============================================
    public BlueRealm(){
         super(RealmColor.BLUE,11);
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
    @Override
    public Move[] getAllPossibleMoves() {
        if(!isRealmAccessible())
            return null;
        int minimumAttackValue=hydra.getMinimumAttackValue();
        int moveArraySize=6-minimumAttackValue+1;//+1 to include the minimumAttack Value
        Move[] moves=new Move[moveArraySize];
        for(int i=0;i<moves.length;i++,minimumAttackValue++)
            moves[i]=getPossibleMovesForADie(minimumAttackValue,RealmColor.BLUE)[0];
        return  moves;
    }
    @Override
    public Move[] getPossibleMovesForADie(int diceValue,RealmColor colorOfDice) {
        if(hydra.checkPossibleAttack(diceValue)){
            BlueDice tempDice=new BlueDice(diceValue);
            return new Move[]{new Move(tempDice,hydra)};
        }
        return null;
    }
    @Override
    public boolean isRewardAvailable() {
        Reward[] rewards=getRealmRewards();
        return rewards[getTotalNumberOfAttacks()]!=null;
    }
   
    
//============================G&S====================================================
    public Hydra getHydra(){
        return hydra;
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
        Reward[] templateRewards=new Reward[]{null,null,null,new ArcaneBoost(),null,
          new Bonus(RealmColor.GREEN),new ElementalCrest(),null,new Bonus(RealmColor.MAGENTA),new TimeWarp(),null};   
        for(int i=0;i<templateRewards.length;i++)
            realmRewards[i]=templateRewards[i];
    }
    
    

    

}
