package main.java.game.engine;
import main.java.game.dice.*;
public class GameBoard {
    private Player player1;
    private Player player2;
    private GameStatus gameStatus;
    private Dice[] allDice;


//============================G&S============================================
   public Player getActivePlayer(){
        if(player1.getPlayerStatus()==PlayerStatus.ACTIVE)
            return player1;
        return player2;    
   }
   public Player getPassivePlayer(){
        if(player1.getPlayerStatus()==PlayerStatus.PASSIVE)
             return player1;
        return player2;    
   }
   public Dice[] getAllDice(){
    return allDice;
   }

}
