package main.java.game.engine;
import main.java.game.creatures.*;
import main.java.game.dice.*;
public class Move {
    private Dice dice;
    private Creature creature;
    public Move(Dice dice,Creature creature){
        this.dice=dice;
        this.creature=creature; 
    }
    public Dice getMoveDice(){
        return dice;
    }
    public Creature getMoveCreature(){
        return creature;
    }

}
