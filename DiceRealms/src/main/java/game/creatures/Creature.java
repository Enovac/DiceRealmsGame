package main.java.game.creatures;
import main.java.game.realms.*;
public abstract class Creature {
    private final RealmColor CREATURE_COLOR;
//============================Constructor============================================
    public Creature(RealmColor color){
         CREATURE_COLOR=color;
    }
//============================Methods================================================
    public abstract boolean checkPossibleAttack(int diceValue);
//============================G&S====================================================
    
    public RealmColor getCreaturColor(){
        return CREATURE_COLOR;
    }

}
