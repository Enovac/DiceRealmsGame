package main.java.game.creatures;

import main.java.game.realms.RealmColor;

public class Phoenix extends Creature{
    private int health;
//============================Constructor============================================
    public Phoenix(){
        super(RealmColor.MAGENTA);
    }
//============================Methods================================================
    public void resetHealth(){
        health=0;
    }
    @Override
    public boolean checkPossibleAttack(int diceValue) {
        return diceValue>health;
    }
//============================G&S====================================================
    public void setHealth(int value){
       if(value==6)
            resetHealth();
       else
            health=value;     
    }
    public int getHealth(){
        return health;
    }
    @Override
    public int getMinimumAttackValue(){
        return health+1;
    }


}
