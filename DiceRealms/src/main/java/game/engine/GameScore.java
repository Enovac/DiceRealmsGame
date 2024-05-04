package main.java.game.engine;

public class GameScore {
    private int totalGameScore;
    private int redScore;
    private int blueScore;
    private int greenScore;
    private int yellowScore;
    private int magentaScore;
    private int numberOfCrests;
    


    public void setRedScore(int value){
        redScore=value;
    }
    public void setBlueScore(int value){
        blueScore=value;
    }
    public void setGreenScore(int value){
        greenScore=value;
    }
    public void setYellowScore(int value){
        yellowScore=value;
    }
    public void setMagentaScore(int value){
        magentaScore=value;
    }
    public void setNumberOfCrests(int value){
        numberOfCrests=value;
    }

    public void calculateGameScore(){
        totalGameScore=redScore+blueScore+greenScore+yellowScore+magentaScore;
        int minRealmScore=Math.min(redScore,Math.min(blueScore,Math.min(greenScore, Math.min(yellowScore, magentaScore))));
        totalGameScore+=minRealmScore*numberOfCrests;
    }
    public int getTotalGameScore(){
        calculateGameScore();
        return totalGameScore;
    }
}
