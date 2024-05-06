package main.java.game.dice;
import main.java.game.realms.*;

public class RedDice extends Dice{
    private int selectsDragon;
    public RedDice(int diceValue){
        super(RealmColor.RED,diceValue);
    }
    public void selectsDragon(int x){
        selectsDragon=x;
    }
    public int getselectsDragon(){
        return selectsDragon;
    }

}
