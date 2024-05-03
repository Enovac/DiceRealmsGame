package main.java.game.engine;

public class GameStatus {
    private int gameRound;
    private int turn;
    public GameStatus(){
        gameRound=1;
        turn=1;
    }
    public int getGameRound(){
        return gameRound;
    }
    public int getTurn(){
        return turn;
    }




    public void resetTurn(){
        turn=1;
    }
    public void incrementTurn(){
        turn++;//???
    }
    public void incrementRound(){
        gameRound++;
    }
    public boolean isGameFinished(){
        return gameRound>=7;
        //based on implemnetation
    }



}
