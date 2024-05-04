package main.java.game.engine;
import main.java.game.collectibles.ArcaneBoost;
import main.java.game.collectibles.ElementalCrest;
import main.java.game.collectibles.TimeWarp;
import main.java.game.realms.*;
import java.util.*;
public class Player {
    // private ScoreSheet scoreSheet;
    // private GameScore gameScore;//Grimore

    private PlayerStatus playerStatus;

    private final BlueRealm BLUE_REALM;
    private final RedRealm RED_REALM;
    private final GreenRealm GREEN_REALM;
    private final YellowRealm YELLOW_REALM;
    private final MagentaRealm MAGENTA_REALM;

    private ArrayList<ArcaneBoost> arcaneBoosts;
    private ArrayList<TimeWarp> timeWarps;
    private ArrayList<ElementalCrest> elementalCrests;
//============================Constructor============================================

    public Player(){
        BLUE_REALM=new BlueRealm();
        RED_REALM=new RedRealm();
        GREEN_REALM=new GreenRealm();
        YELLOW_REALM=new YellowRealm();
        MAGENTA_REALM=new MagentaRealm();
    }




//============================G&S============================================
public PlayerStatus getPlayerStatus(){
    return playerStatus;
}
public void setPlayerStatus(PlayerStatus status){
    playerStatus=status;
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
public ArrayList<ArcaneBoost> getArcaneBoosts(){
    return arcaneBoosts;
}
public ArrayList<TimeWarp> getTimeWarps(){
    return timeWarps;
}
public ArrayList<ElementalCrest> getElementalCrests(){
    return elementalCrests;
}
}
