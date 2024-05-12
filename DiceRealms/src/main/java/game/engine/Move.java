package main.java.game.engine;
import main.java.game.creatures.*;
import main.java.game.dice.*;
import main.java.game.realms.RealmColor;
public class Move implements Comparable<Move>{//TODO: fix comparable
    private Dice dice;
    private Creature creature;
//=======================================Constructor===================================
    public Move(Dice dice,Creature creature){
        this.dice=dice;
        this.creature=creature; 
    }
//=======================================Methods======================================= 
    @Override
    public int compareTo(Move o) {
        return this.dice.compareTo(o.getDice());
    }
//=======================================Get&Set=======================================
    public Dice getDice(){
        return dice;
    }
    public Creature getMoveCreature(){
        return creature;
    }
//=======================================Display=======================================    
    @Override 
    public String toString(){
        return dice+" "+creature;
    }
}
