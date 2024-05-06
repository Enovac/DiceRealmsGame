package main.java.game.creatures;

import main.java.game.realms.RealmColor;

public class Phoenix extends Creature{
    private int health;
//=======================================Constructor===================================
    public Phoenix(){
        super(RealmColor.MAGENTA);
    }
//=======================================Methods=======================================
    @Override
    public boolean checkPossibleAttack(int diceValue) {//TODO: check Dice>0 <=6 and Throw Error
        return diceValue>health;
    }
    public void resetHealth(){
        health=0;
    }
//=======================================Get&Set=======================================
    @Override
    public int getMinimumAttackValue(){
        return health+1;
    }
    public void setHealth(int value){
       if(value==6)
            resetHealth();
       else
            health=value;     
    }
    public int getHealth(){
        return health;
    }
//=======================================Display=======================================
    @Override 
    public String toString(){
        return "Phoenix";
    }
}
