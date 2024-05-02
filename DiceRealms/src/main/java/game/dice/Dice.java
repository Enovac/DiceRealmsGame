package main.java.game.dice;

public abstract class Dice {
    private DiceStatus diceStatus;
    private int diceValue;

//============================G&S============================================
    public DiceStatus getDiceStatus(){
        return diceStatus;
    }
    public void rollDice(int newValue){
        diceValue=newValue;
    }
}

