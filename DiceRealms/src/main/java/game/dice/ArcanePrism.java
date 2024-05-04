package main.java.game.dice;
import main.java.game.realms.*;
public class ArcanePrism extends Dice{
    private RealmColor chosenColor;
    public ArcanePrism(int diceValue){
        super(RealmColor.WHITE,diceValue);
    }
    public void setChosenColor(String chosen){
        switch (chosen.toUpperCase().trim().charAt(0)) {
            case 'R':chosenColor=RealmColor.RED;break;
            case 'B':chosenColor=RealmColor.BLUE;break;
            case 'G':chosenColor=RealmColor.GREEN;break;
            case 'Y':chosenColor=RealmColor.YELLOW;break;
            case 'M':chosenColor=RealmColor.MAGENTA;break;
            default:break;//throw error else<<<
        }
    }
    public RealmColor getChosenColor(){
        return chosenColor;
    }
    

}
