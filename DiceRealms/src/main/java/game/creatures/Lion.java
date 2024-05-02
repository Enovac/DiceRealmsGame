package main.java.game.creatures;

import main.java.game.realms.RealmColor;

public class Lion extends Creature{
//============================Constructor============================================
    public Lion(){
        super(RealmColor.YELLOW);
    }

//============================Methods================================================

@Override
public boolean checkPossibleAttack(int diceValue) {
    return true;//always Possible
}
//============================G&S====================================================   





}
