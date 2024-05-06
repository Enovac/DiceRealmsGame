package main.java.game.creatures;

import main.java.game.realms.RealmColor;

public class Lion extends Creature{
//=======================================Constructor===================================
    public Lion(){
            super(RealmColor.YELLOW);
    }
//=======================================Methods=======================================
    @Override
    public boolean checkPossibleAttack(int diceValue) {//TODO: check Dice>0 <=6 and Throw Error
        return true;//always Possible
    }
//=======================================Display=======================================
    @Override public String toString(){
        return "Lion";
    }
}
