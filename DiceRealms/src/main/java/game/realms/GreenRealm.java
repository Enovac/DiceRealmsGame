package main.java.game.realms;

import main.java.game.creatures.Creature;
import main.java.game.dice.Dice;

public class GreenRealm extends Realms{

//============================Constructor============================================
    public GreenRealm(){
         super(RealmColor.GREEN);
    }
//============================Methods================================================
public  boolean attack(Dice dice,Creature creature){
        return false;
    }

//============================G&S====================================================    

}
