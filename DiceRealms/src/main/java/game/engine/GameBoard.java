package main.java.game.engine;
import main.java.game.dice.*;
public class GameBoard {
    private Player player1;
    private Player player2;
    private GameStatus gameStatus;

    private ArcanePrism arcanePrism;
    private BlueDice blueDice;
    private RedDice redDice;
    private GreenDice greenDice;
    private YellowDice yellowDice;
    private MagentaDice magentaDice;


     public GameBoard(){
          player1=new Player();//set active / passive??
          player2=new Player();

          gameStatus=new GameStatus();
          
          arcanePrism=new ArcanePrism(6);
          blueDice=new BlueDice(6);
          redDice=new RedDice(6);
          greenDice=new GreenDice(6);
          yellowDice=new YellowDice(6);
          magentaDice=new MagentaDice(6);
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
    return new Dice[]{redDice,blueDice,greenDice,yellowDice,magentaDice,arcanePrism};
   }
   public RedDice getRedDice(){
     return redDice;
   }
   public BlueDice getBlueDice(){
     return blueDice;
   } 
   public GreenDice getGreenDice(){
     return greenDice;
   }
   public YellowDice getYellowDice(){
     return yellowDice;
   }
   public MagentaDice getMagentaDice(){
     return magentaDice;
   }
   public ArcanePrism getArcanePrism(){
     return arcanePrism;
   }
   public GameStatus getGameStatus(){
      return gameStatus;
   }

}
