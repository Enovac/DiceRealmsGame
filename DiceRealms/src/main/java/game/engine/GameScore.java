package main.java.game.engine;

public class GameScore {
    private int totalGameScore;
    
    public void calculateGameScore(int redScore,int blueScore,int greenScore,int yellowScore,int magentaScore,int numberOfCrests){
        totalGameScore=redScore+blueScore+greenScore+yellowScore+magentaScore;
        int minRealmScore=Math.min(redScore,Math.min(blueScore,Math.min(greenScore, Math.min(yellowScore, magentaScore))));
        totalGameScore+=minRealmScore*numberOfCrests;
    }
    public int getTotalGameScore(int redScore,int blueScore,int greenScore,int yellowScore,int magentaScore,int numberOfCrests){
        calculateGameScore(redScore,blueScore,greenScore,yellowScore,magentaScore,numberOfCrests);
        return totalGameScore;
    }
}
