package main.java.game.dice;
import main.java.game.realms.*;
public class ArcanePrism extends Dice{
    private RealmColor chosenColor;
    private int selectsDragon;
//=======================================Constructor===================================
    public ArcanePrism(int diceValue){
        super(RealmColor.WHITE,diceValue);
    }
//=======================================Methods=======================================
    public void selectsDragon(int x){//Doesn't follow naming convention to meet test case
    selectsDragon=x;//TODO: make sure selects 1<=x<=4
    }    
//=======================================Get&Set=======================================
    public void setChosenColor(RealmColor color){
       chosenColor=color;
    }
    public RealmColor getChosenColor(){
        return chosenColor;
    }
    public int getselectsDragon(){
        return selectsDragon;
    }
   //Selects dragon to imitate red
}
