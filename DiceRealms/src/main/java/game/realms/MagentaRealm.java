package main.java.game.realms;

import main.java.game.creatures.Creature;
import main.java.game.dice.Dice;

public class MagentaRealm extends Realms{


//============================Constructor============================================
    public MagentaRealm(){
        super(RealmColor.MAGENTA);
    }
//============================Methods================================================
public  boolean attack(Dice dice,Creature creature){
        return false;
    }

//============================G&S====================================================    

}
