package main.java.game.dice;
import main.java.game.realms.*;
public class ArcanePrism extends Dice{
    private RealmColor chosenColor;
//=======================================Constructor===================================
    public ArcanePrism(int diceValue){
        super(RealmColor.WHITE,diceValue);
    }
//=======================================Get&Set=======================================
    public void setChosenColor(RealmColor color){
       chosenColor=color;
    }
    public RealmColor getChosenColor(){
        return chosenColor;
    }
}
