package main.java.game.engine;
import main.java.game.dice.GreenDice;
import main.java.game.realms.*;
public class Player {
    private ScoreSheet scoreSheet;
    private GameScore gameScore;//Grimore

    private PlayerStatus playerStatus;

    private BlueRealm blueRealm;
    private RedRealm redRealm;
    private GreenRealm greenRealm;
    private YellowRealm yellowRealm;
    private MagentaRealm magentaRealm;
//============================Constructor============================================

    public Player(){
        blueRealm=new BlueRealm();
        redRealm=new RedRealm();
        greenRealm=new GreenRealm();
        yellowRealm=new YellowRealm();
        magentaRealm=new MagentaRealm();
    }




//============================G&S============================================
public PlayerStatus getPlayerStatus(){
    return playerStatus;
}
public void setPlayerStatus(PlayerStatus status){
    playerStatus=status;
}

}
