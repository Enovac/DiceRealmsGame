package main.java.game.engine;

import main.java.game.realms.*;

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
        return RED_REALM+"\n\n"+GREEN_REALM+"\n\n"+BLUE_REALM+"\n\n"+MAGENTA_REALM+"\n\n"+YELLOW_REALM;
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
}
