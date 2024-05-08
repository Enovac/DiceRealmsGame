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
    public int compareTo(Move o) {//TODO: fix tbis
        Dice dice1=this.dice;
        Dice dice2=o.getDice();
        RealmColor color1=dice1.getDiceColor();
        if(color1==RealmColor.WHITE)color1=((ArcanePrism)dice1).getChosenColor();
        RealmColor color2=dice2.getDiceColor();
        if(color2==RealmColor.WHITE)color2=((ArcanePrism)dice2).getChosenColor();

        switch(color1){
            case RED:if(color2==RealmColor.RED)return -1;
                     return 1;
            case GREEN:if(color2==RealmColor.GREEN)return -1;
                       return 1;
            case BLUE:if(color2==RealmColor.BLUE)return -1;
                      return 1;
            case MAGENTA:
                      if(color2==RealmColor.MAGENTA)return -1;
                      return 1;
            case YELLOW:if(color2==RealmColor.YELLOW)return -1;
                      return 1;
        }
        System.out.println("error comparable");return 1;
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
