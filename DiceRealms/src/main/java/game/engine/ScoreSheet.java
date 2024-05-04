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





/* 
Emberfall Dominion: Pyroclast Dragon (RED REALM):
+-----------------------------------+
|  #  |D1   |D2   |D3   |D4   |R    |
+-----------------------------------+
|  F  |3    |6    |5    |X    |GB   |
|  W  |2    |1    |X    |5    |YB   |
|  T  |1    |X    |2    |4    |BB   |
|  H  |X    |3    |4    |6    |EC   |
+-----------------------------------+
|  S  |10   |14   |16   |20   |AB   |
+-----------------------------------+


Terra's Heartland: Gaia Guardians (GREEN REALM): 
+-----------------------------------+
|  #  |1    |2    |3    |4    |R    |
+-----------------------------------+
|  1  |X    |2    |3    |4    |YB   |
|  2  |5    |6    |7    |8    |RB   |
|  3  |9    |10   |11   |12   |EC   |
+-----------------------------------+
|  R  |TW   |BB   |MB   |AB   |     |
+-----------------------------------------------------------------------------+
|  S  |0    |1    |2    |4    |7    |11   |16   |22   |29   |37   |46   |56   |
+-----------------------------------------------------------------------------+









*/


























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
