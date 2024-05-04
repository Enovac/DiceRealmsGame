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
    public int getMinimumAttackValue(){
        return 0;//Default override if needed;
    }
//============================G&S====================================================
    
    public RealmColor getCreatureColor(){
        return CREATURE_COLOR;
    }

}
