package main.java.game.realms;

import main.java.game.creatures.*;


public class GreenRealm extends Realms{
    Gaia gaiaGurdian;
//============================Constructor============================================
    public GreenRealm(){
        super(RealmColor.GREEN);
        gaiaGurdian=new Gaia();
    }
//============================Methods================================================
    @Override
    public boolean attack(int diceValue, Creature creature) {//diceValue is green+white
        if(gaiaGurdian.checkPossibleAttack(diceValue)){
            gaiaGurdian.killGaiaGurdian(diceValue);
            incrementTotalNumberOfAttacks();
            updateTotalRealmScore(getTotalNumberOfAttacks());
            if(isRealmDefeated())
                    closeRealm();
            //get rewards
            return true;
        }
        return false;
    }
    @Override
    public void updateTotalRealmScore(int totalNumberOfAttacks) {
        switch(totalNumberOfAttacks){
            case 1:setTotalRealmScore(1);break;
            case 2:setTotalRealmScore(2);break;
            case 3:setTotalRealmScore(4);break;
            case 4:setTotalRealmScore(7);break;
            case 5:setTotalRealmScore(11);break;
            case 6:setTotalRealmScore(16);break;
            case 7:setTotalRealmScore(22);break;
            case 8:setTotalRealmScore(29);break;
            case 9:setTotalRealmScore(37);break;
            case 10:setTotalRealmScore(46);break;
            case 11:setTotalRealmScore(56);break;
        }
        
    }
    @Override
    public boolean isRealmDefeated() {
        return getTotalNumberOfAttacks()==11;
    }

//============================G&S====================================================   
    public Gaia getGaia(){
        return gaiaGurdian;
    }

}
