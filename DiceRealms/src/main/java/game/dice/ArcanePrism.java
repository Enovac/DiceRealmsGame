package main.java.game.dice;
import main.java.game.realms.*;
public class ArcanePrism extends Dice{
    private RealmColor chosenColor;
//=======================================Constructor===================================
    public ArcanePrism(int diceValue){
        super(RealmColor.WHITE,diceValue);
    }
//=======================================Get&Set=======================================
    public void setChosenColor(String chosen){
        switch (chosen.toUpperCase().trim().charAt(0)) {
            case 'R':chosenColor=RealmColor.RED;break;
            case 'B':chosenColor=RealmColor.BLUE;break;
            case 'G':chosenColor=RealmColor.GREEN;break;
            case 'Y':chosenColor=RealmColor.YELLOW;break;
            case 'M':chosenColor=RealmColor.MAGENTA;break;
        }
        //TODO: make sure that if Char not correct throw error
    }
    public RealmColor getChosenColor(){
        return chosenColor;
    }
}
