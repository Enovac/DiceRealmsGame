package main.java.game.creatures;

import main.java.game.realms.RealmColor;

public class Gaia extends Creature{
    private boolean[] gurdiansHealth;
//=======================================Constructor===================================
public Gaia(){
        super(RealmColor.GREEN);
        gurdiansHealth=new boolean[13];
        for(int i=2;i<gurdiansHealth.length;i++)
            gurdiansHealth[i]=true;
        //False for dead , True for avaibble, Index = required Value    
    }
//=======================================Methods=======================================
    @Override
    public boolean checkPossibleAttack(int diceValue) {
        return diceValue>=2&&diceValue<=12&&gurdiansHealth[diceValue];
    }
    public void killGaiaGurdian(int diceValue){
        gurdiansHealth[diceValue]=false;
    }
//=======================================Get&Set=======================================
    public boolean[] getGurdiansHealth(){
            return gurdiansHealth;
    }
//=======================================Display=======================================
    @Override 
    public String toString(){
        return "Gaia";
    }
}
