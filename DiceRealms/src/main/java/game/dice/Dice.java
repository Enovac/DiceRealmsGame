package main.java.game.dice;
import main.java.game.realms.*;
public abstract class Dice {
    private DiceStatus diceStatus;
    private int diceValue;
    private final RealmColor DICE_COLOR;

    public Dice(RealmColor diceColor,int diceValue){
        DICE_COLOR=diceColor;
        diceStatus=DiceStatus.AVAILABLE;
        this.diceValue=diceValue;
    }
//============================G&S============================================
    public RealmColor getDiceColor(){
       return DICE_COLOR;
    }
    public RealmColor getRealm(){
        return DICE_COLOR;
     }
    public void setDiceStatus(DiceStatus status){
        diceStatus=status;
    }
    public DiceStatus getDiceStatus(){
        return diceStatus;
    }
    public int getValue(){
        return diceValue;
    }
    public void rollDice(){
        int newValue=((int)((Math.random()*6)+1));
        diceValue=newValue;
    }
    @Override
    public String toString(){
        return DICE_COLOR+": "+diceValue+"   ";
    }
    public void setValue(int value){
        diceValue=value;
    }
}

