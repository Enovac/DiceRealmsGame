package main.java.game.realms;
import main.java.game.creatures.*;
import main.java.game.engine.*;
import main.java.game.collectibles.*;
public abstract class Realms {
    private final RealmColor REALM_COLOR;
    private boolean isRealmAccessible;//Realm isnt accessible when all enemies are dead!!
    private int totalRealmScore;
    private int totalNumberOfAttacks;
    private Reward[] realmRewards;

//============================Constructor============================================

    public Realms(RealmColor color,int rewardSize){
        REALM_COLOR=color;
        isRealmAccessible=true;
        realmRewards=new Reward[rewardSize];
        setRealmRewards(realmRewards);
    }
//============================Methods============================================
    public abstract boolean attack(int diceValue,Creature creature);
    public abstract void updateTotalRealmScore(int value);
    public void incrementTotalNumberOfAttacks(){
        totalNumberOfAttacks++;
    }
    public void rewardClaimed(int index){
        realmRewards[index]=null;
    }
    public abstract boolean isRealmDefeated();
    public abstract Move[] getAllPossibleMoves();
    public abstract Move[] getPossibleMovesForADie(int diceValue,RealmColor colorOfDice);
    public abstract boolean checkRewardAvailable();
//============================G&S============================================
    public RealmColor getRealmColor(){
        return REALM_COLOR;
    }
    public void closeRealm(){//maybe rename to fit conventioln
        isRealmAccessible=false;
    }
    public boolean isRealmAccessible(){
        return isRealmAccessible;
    }
    public int getTotalRealmScore(){
        return totalRealmScore;
    }
    public void setTotalRealmScore(int value){
        totalRealmScore=value;
    }
    public int getTotalNumberOfAttacks(){
        return totalNumberOfAttacks;
    }
    public Reward[] getRealmRewards(){
        return realmRewards;
    }
    public abstract Reward getReward();
    public abstract void setRealmRewards(Reward[] realmRewards);
    
}
