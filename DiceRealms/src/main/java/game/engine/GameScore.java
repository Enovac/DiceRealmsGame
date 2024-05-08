package main.java.game.engine;

public class GameScore {
    private int totalGameScore;
    private int redScore;
    private int blueScore;
    private int greenScore;
    private int yellowScore;
    private int magentaScore;
    private int numberOfCrests;
    
//=======================================Methods=======================================
    public void calculateGameScore(int red,int blue,int green,int yellow,int magenta,int numberCrests){
        redScore=red;blueScore=blue;greenScore=green;
        yellowScore=yellow;magentaScore=magenta;numberOfCrests=numberCrests;

        totalGameScore=redScore+blueScore+greenScore+yellowScore+magentaScore;

        int minRealmScore=Math.min(redScore,Math.min(blueScore,Math.min(greenScore, Math.min(yellowScore, magentaScore))));

        totalGameScore+=minRealmScore*numberOfCrests;
    }
//=======================================Get&Set=======================================
    public int getRedRealmScore(){
        return redScore;
    }
    public int getGreenRealmScore(){
        return greenScore;
    }
    public int getBlueRealmScore(){
        return blueScore;
    }
    public int getMagentaRealmScore(){
        return magentaScore;
    }
    public int getYellowRealmScore(){
        return yellowScore;
    }
    public int getTotalGameScore(){
        return totalGameScore;
    }
}
