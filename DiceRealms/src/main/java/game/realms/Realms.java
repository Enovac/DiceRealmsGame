package main.java.game.realms;
import main.java.game.creatures.*;
import main.java.game.engine.*;
public abstract class Realms {
    private final RealmColor REALM_COLOR;
    private boolean isRealmAccessible;//Realm isnt accessible when all enemies are dead!!
    private int totalRealmScore;
    private int totalNumberOfAttacks;

//============================Constructor============================================

    public Realms(RealmColor color){
        REALM_COLOR=color;
        isRealmAccessible=true;
    }
//============================Methods============================================
    public abstract boolean attack(int diceValue,Creature creature);
    public abstract void updateTotalRealmScore(int value);
    public void incrementTotalNumberOfAttacks(){
        totalNumberOfAttacks++;
    }
    public abstract boolean isRealmDefeated();
    public abstract Move[] getAllPossibleMoves();
    public abstract Move[] getPossibleMovesForADie(int diceValue,RealmColor colorOfDice);
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



}
