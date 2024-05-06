package main.java.game.engine;

import main.java.game.realms.*;
import main.java.game.creatures.*;
import main.java.game.dice.Dice;
public class ScoreSheet {
    private final BlueRealm BLUE_REALM;
    private final RedRealm RED_REALM;
    private final GreenRealm GREEN_REALM;
    private final YellowRealm YELLOW_REALM;
    private  final MagentaRealm MAGENTA_REALM;

    public ScoreSheet(){
        BLUE_REALM=new BlueRealm();
        RED_REALM=new RedRealm();
        GREEN_REALM=new GreenRealm();
        YELLOW_REALM=new YellowRealm();
        MAGENTA_REALM=new MagentaRealm();
    }
    @Override
    public String toString(){
        return "\n\nScoreSheet\n\n"+RED_REALM+"\n"+GREEN_REALM+"\n"+BLUE_REALM+"\n"+MAGENTA_REALM+"\n"+YELLOW_REALM;
    }

    public BlueRealm getBlueRealm(){
        return BLUE_REALM;
    }
    public RedRealm getRedRealm(){
        return RED_REALM;
    }
    public GreenRealm getGreenRealm(){
        return GREEN_REALM;
    }
    public YellowRealm getYellowRealm(){
        return YELLOW_REALM;
    }
    public MagentaRealm getMagentaRealm(){
        return MAGENTA_REALM;
    } 
    public Creature getCreatureByRealm(Dice dice){
        switch (dice.getDiceColor()) {
            case RED:return RED_REALM.getCreatureByRealm(dice);
            case GREEN:return GREEN_REALM.getCreatureByRealm(dice);
            case BLUE:return BLUE_REALM.getCreatureByRealm(dice);
            case MAGENTA:return MAGENTA_REALM.getCreatureByRealm(dice);
            case YELLOW:return YELLOW_REALM.getCreatureByRealm(dice);
            default:return null;//error occured
        }
    }
}
