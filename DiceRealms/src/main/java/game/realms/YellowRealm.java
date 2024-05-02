package main.java.game.realms;

import main.java.game.creatures.Creature;
import main.java.game.dice.Dice;

public class YellowRealm extends Realms{


//============================Constructor============================================
    public YellowRealm(){
        super(RealmColor.YELLOW);
    }
//============================Methods================================================
public  boolean attack(Dice dice,Creature creature){
        return false;
    }

//============================G&S====================================================    

}
