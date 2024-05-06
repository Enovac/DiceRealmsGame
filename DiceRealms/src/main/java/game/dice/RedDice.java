package main.java.game.dice;
import main.java.game.realms.*;

public class RedDice extends Dice{
    private int selectsDragon;
//=======================================Constructor===================================
    public RedDice(int diceValue){
        super(RealmColor.RED,diceValue);
    }
//=======================================Methods=======================================
    public void selectsDragon(int x){//Doesn't follow naming convention to meet test case
        selectsDragon=x;//TODO: make sure selects 1<=x<=4
    }
//=======================================Get&Set=======================================
    public int getselectsDragon(){
        return selectsDragon;
    }
}
