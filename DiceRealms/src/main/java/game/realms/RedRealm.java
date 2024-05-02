package main.java.game.realms;

import main.java.game.creatures.Creature;
import main.java.game.dice.Dice;

public class RedRealm extends Realms{
//============================Constructor============================================
    public RedRealm(){
        super(RealmColor.RED);
    }
//============================Methods================================================
    public  boolean attack(Dice dice,Creature creature){
        return false;
    }

//============================G&S====================================================

}
