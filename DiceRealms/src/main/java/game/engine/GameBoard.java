package main.java.game.engine;
import main.java.game.dice.*;
public class GameBoard {
    private Player player1;
    private Player player2;
    private GameStatus gameStatus;
    private Dice[] allDice;


     public GameBoard(){
          player1=new Player();//set active / passive??
          player2=new Player();

          gameStatus=new GameStatus();
          
          ArcanePrism arcanePrism=new ArcanePrism();
          BlueDice blueDice=new BlueDice();
          RedDice redDice=new RedDice();
          GreenDice greenDice=new GreenDice();
          YellowDice yellowDice=new YellowDice();
          MagentaDice magentaDice=new MagentaDice();
          allDice=new Dice[]{redDice,blueDice,greenDice,yellowDice,magentaDice,arcanePrism};
     }


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
