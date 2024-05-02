package main.java.game.realms;
import main.java.game.creatures.*;
public abstract class Realms {
    private final RealmColor REALM_COLOR;
    private boolean isAccessible;//Realm isnt accessible when all enemies are dead!!
    private int totalRealmScore;
    private int totalNumberOfAttacks;

//============================Constructor============================================

    public Realms(RealmColor color){
        REALM_COLOR=color;
        isAccessible=true;
    }
//============================Methods============================================
    public abstract boolean attack(int diceValue,Creature creature);
    public abstract void updateTotalRealmScore(int value);
    public void incrementTotalNumberOfAttacks(){
        totalNumberOfAttacks++;
    }
    public abstract boolean isRealmDefeated();
//============================G&S============================================
    public RealmColor getRealmColor(){
        return REALM_COLOR;
    }
    public void closeRealm(){//maybe rename to fit conventioln
        isAccessible=false;
    }
    public boolean isAccessible(){
        return isAccessible;
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
