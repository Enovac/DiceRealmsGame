package main.java.game.dice;
import main.java.game.realms.*;
public abstract class Dice {
    private DiceStatus diceStatus;
    private int diceValue;
    private final RealmColor DICE_COLOR;
    private int selectsDragon;
//=======================================Constructor===================================
    public Dice(RealmColor diceColor,int diceValue){
        DICE_COLOR=diceColor;
        diceStatus=DiceStatus.AVAILABLE;
        this.diceValue=diceValue;
    }
//=======================================Methods=======================================
    public void rollDice(){
        int newValue=((int)((Math.random()*6)+1));
        diceValue=newValue;
    }
//=======================================Get&Set=======================================
    public RealmColor getDiceColor(){
       return DICE_COLOR;
    }
    public RealmColor getRealm(){
        return DICE_COLOR;
    }
    public DiceStatus getDiceStatus(){
        return diceStatus;
    }
    public void setDiceStatus(DiceStatus status){
        diceStatus=status;
    }
    public int getValue(){
        return diceValue;
    }
    public void setValue(int value){
        diceValue=value;//TODO: makesure 1<=value<=6
    }
    public int getselectsDragon(){
        return selectsDragon;
    } 
    public void selectsDragon(int x){//Doesn't follow naming convention to meet test case
    selectsDragon=x;//TODO: make sure selects 1<=x<=4
    }
//=======================================Display=======================================    
    @Override
    public String toString(){
        return DICE_COLOR+": "+diceValue;
    }
}

