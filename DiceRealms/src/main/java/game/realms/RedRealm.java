package main.java.game.realms;

import main.java.game.creatures.*;


public class RedRealm extends Realms{
    private Dragon dragon1;
    private Dragon dragon2;
    private Dragon dragon3;
    private Dragon dragon4;
//============================Constructor============================================
    public RedRealm(){
        super(RealmColor.RED);
        dragon1=new Dragon(DragonNumber.Dragon1,3,2,1,0);
        dragon2=new Dragon(DragonNumber.Dragon2,6,1,0,3);
        dragon3=new Dragon(DragonNumber.Dragon3,5,0,2,4);
        dragon4=new Dragon(DragonNumber.Dragon4,0,5,4,6);
    }
//============================Methods================================================
  
    @Override
    public boolean attack(int diceValue, Creature creature) {
        Dragon currentDragon=(Dragon)creature;
        if(currentDragon.checkPossibleAttack(diceValue)){
            currentDragon.attackPart(diceValue);
            incrementTotalNumberOfAttacks();
            updateTotalRealmScore(diceValue);//change
            if(isRealmDefeated())
                closeRealm();
            //getRewards
            return true;
        }
        return false;
    }
    @Override
    public void updateTotalRealmScore(int value) {
        int newScore=0;
        if(dragon1.isDeadDragon())
            newScore+=10;
        if(dragon2.isDeadDragon())
            newScore+=14;
        if(dragon3.isDeadDragon())
            newScore+=16;
        if(dragon4.isDeadDragon())
            newScore+=20;
        setTotalRealmScore(newScore);             
    }
    @Override
    public boolean isRealmDefeated() {
        return dragon1.isDeadDragon()&&dragon2.isDeadDragon()&&dragon3.isDeadDragon()&&dragon4.isDeadDragon();
    }  

//============================G&S====================================================
    public Dragon getDragon1(){
        return dragon1;
    }
    public Dragon getDragon2(){
        return dragon2;
    }
    public Dragon getDragon3(){
        return dragon3;
    }
    public Dragon getDragon4(){
        return dragon4;
    }
}
