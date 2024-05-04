package main.java.game.engine;
import main.java.game.dice.*;

import java.util.*;
import main.java.game.collectibles.*;
import main.java.game.realms.*;
import main.java.game.creatures.*;
public class CLIGameController extends GameController{
    private GameBoard gameBoard;
    private Scanner sc;
    

    public  void startGame(){
        sc=new Scanner(System.in);
        gameBoard=new GameBoard();
        while(!getGameStatus().isGameFinished()){
            Player currentPlayer=getActivePlayer();
            while(getGameStatus().getTurn()<=3){
                System.out.println(currentPlayer.getPlayerType()+"'s Turn!!!");
                createDelay();
                System.out.println(getScoreSheet(currentPlayer));
                System.out.println("Rolling dice......");
                createDelay();
                if(getAvailableDice()==null){
                    System.out.println("No more dice to roll :( ");
                    break;
                }
                rollDice();
                int chosenDie=displayAvailableDice();
                Dice[] availableDice=getAvailableDice();
                Dice selectedDie=availableDice[chosenDie];
                selectDice(selectedDie, currentPlayer);
                RealmColor theDiceColor;
                if(selectedDie.getDiceColor()==RealmColor.WHITE){
                    chooseArcaneDiceColor();
                    theDiceColor=((ArcanePrism)selectedDie).getChosenColor();
                }
                else theDiceColor=selectedDie.getDiceColor();
                choseCreatureInRealm(theDiceColor, currentPlayer);
                break;
            }   
            break;
        }
    }
    public Creature choseCreatureInRealm(RealmColor color,Player player){
        switch (color) {
            case RED:System.out.println(player.getRedRealm());
            System.out.println("Please Choose  A dragon");
            return player.getRedRealm().getDragon1();
            case BLUE:System.out.println(player.getBlueRealm());
            System.out.println("Attacking Hydra....");
            createDelay();
            return player.getBlueRealm().getHydra();
            case GREEN:System.out.println(player.getGreenRealm());
            System.out.println("Attacking Gaia Gurdian....");
            createDelay();
            return player.getGreenRealm().getGaia();
            case YELLOW:System.out.println(player.getYellowRealm());
            System.out.println("Attacking Lion....");
            createDelay();
            return player.getYellowRealm().getLion();
            case MAGENTA:System.out.println(player.getMagentaRealm());
            System.out.println("Attacking Phoenix....");
            createDelay();
            return player.getMagentaRealm().getPhoenix();
        }
        return null;//error occured
    }
    public void chooseArcaneDiceColor(){
        System.out.println("Please chose a color for the Arcane Prism:");
        System.out.println("Red Blue Green Yellow Magenta");
        System.out.print("Chosen color: ");
        getGameBoard().getArcanePrism().setChosenColor(sc.next());
    }
    public int displayAvailableDice(){
        Dice[]availableDice=getAvailableDice();
        for(int i=0;i<availableDice.length;i++)
            System.out.print(i+"-"+availableDice[i]);
        System.out.println();
        System.out.println("Choose a number between 0 and "+(availableDice.length-1)+" to pick a die");
        System.out.print("Chosen Number: ");
        String chosenDie=sc.next().trim();
        //Error here
        return Integer.parseInt(chosenDie);

    }
    public void createDelay(){
        try{
            Thread.sleep(3000);
        }
        catch(Exception ex){
            System.out.println("Error in Thread Sleep");
            ex.printStackTrace();
        }
    }

    /**
     * Switches the role of the current active player to passive and vice versa,
     * ensuring that the turn-taking mechanism functions correctly.
     *
     * @return {@code true} if the switch was successful,
     *         {@code false} otherwise.
     */
    public  boolean switchPlayer(){
        Player activePlayer=getActivePlayer();
        Player passivePlayer=getPassivePlayer();
        passivePlayer.setPlayerStatus(PlayerStatus.PASSIVE);
        activePlayer.setPlayerStatus(PlayerStatus.ACTIVE);
        return true;
        //Should it do the get dice from bla bla??<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    }

    public  Dice[] rollDice(){
        Dice[] rolledDice=getAvailableDice();
        for(Dice currentDice:rolledDice)
            currentDice.rollDice();
        return rolledDice;
    }

    public  Dice[] getAvailableDice(){
        Dice[] allDice=getAllDice();
        int numberOfAvailableDice=0;
        for(Dice x:allDice)
            if(x.getDiceStatus()==DiceStatus.AVAILABLE)
                numberOfAvailableDice++;
       if(numberOfAvailableDice==0)
            return null;
       Dice[] availableDice=new Dice[numberOfAvailableDice];

        for(int i=0,j=0;i<numberOfAvailableDice;j++)
            if(allDice[j].getDiceStatus()==DiceStatus.AVAILABLE)
                availableDice[i++]=allDice[j];

        return availableDice;        
    }
    
    public  Dice[] getAllDice(){
        return gameBoard.getAllDice();//or Do I do seperate Dice?
    }

    public  Dice[] getForgottenRealmDice(){
        Dice[] allDice=getAllDice();
        int numberOfForgottenDice=0;
        for(Dice x:allDice)
            if(x.getDiceStatus()==DiceStatus.FORGOTTEN_REALM)
                numberOfForgottenDice++;

       Dice[] forgottenDice=new Dice[numberOfForgottenDice];

        for(int i=0,j=0;i<numberOfForgottenDice;j++)
            if(allDice[j].getDiceStatus()==DiceStatus.FORGOTTEN_REALM)
                forgottenDice[i++]=allDice[j];
                
        return forgottenDice;     
    }

    public  Move[] getAllPossibleMoves(Player player){
        BlueRealm playerBlueRealm=player.getBlueRealm();
        RedRealm  playerRedRealm=player.getRedRealm();
        GreenRealm playerGreenRealm=player.getGreenRealm();
        YellowRealm playerYellowRealm=player.getYellowRealm();
        MagentaRealm playerMagentaRealm=player.getMagentaRealm();

        Move[] blueRealmMoves=playerBlueRealm.getAllPossibleMoves();
        Move[] redRealmMoves=playerRedRealm.getAllPossibleMoves();
        Move[] greenRealmMoves=playerGreenRealm.getAllPossibleMoves();
        Move[] yellowRealmMoves=playerYellowRealm.getAllPossibleMoves();
        Move[] magentaRealmMoves=playerMagentaRealm.getAllPossibleMoves();

        return mergeMoves(blueRealmMoves, redRealmMoves, greenRealmMoves, yellowRealmMoves, magentaRealmMoves);
    }

    public  Move[] getPossibleMovesForAvailableDice(Player player){
        ArcanePrism arcanePrism=gameBoard.getArcanePrism();
        BlueDice blueDice=gameBoard.getBlueDice();
        RedDice redDice=gameBoard.getRedDice();
        GreenDice greenDice=gameBoard.getGreenDice();
        YellowDice yellowDice=gameBoard.getYellowDice();
        MagentaDice magentaDice=gameBoard.getMagentaDice();

        Move[] blueMoves=getPossibleMovesForADie(player, blueDice);
        Move[] redMoves=getPossibleMovesForADie(player, redDice);
        Move[] greenMoves=getPossibleMovesForADie(player, greenDice);
        Move[] yellowMoves=getPossibleMovesForADie(player, yellowDice);
        Move[] magentaMoves=getPossibleMovesForADie(player, magentaDice);
        Move[] arcaneMoves=getPossibleMovesForADie(player, arcanePrism);

        Move[] moves1=mergeMoves(blueMoves,redMoves, greenMoves, yellowMoves, magentaMoves);
        return mergeMoves(moves1,arcaneMoves,null,null,null);
    }

    public  Move[] getPossibleMovesForADie(Player player, Dice dice){
     
        RealmColor diceColor=dice.getDiceColor();
        int diceValue=dice.getDiceValue();

        Move[] blueRealmMoves=null;
        Move[] redRealmMoves=null;
        Move[] greenRealmMoves=null;
        Move[] yellowRealmMoves=null;
        Move[] magentaRealmMoves=null;
        
        if(diceColor==RealmColor.BLUE||diceColor==RealmColor.WHITE){
            BlueRealm playerBlueRealm=player.getBlueRealm();
            blueRealmMoves=playerBlueRealm.getPossibleMovesForADie(diceValue, diceColor);
        }
        if(diceColor==RealmColor.RED||diceColor==RealmColor.WHITE){
            RedRealm  playerRedRealm=player.getRedRealm();
            redRealmMoves=playerRedRealm.getPossibleMovesForADie(diceValue, diceColor);
        }
        if(diceColor==RealmColor.GREEN||diceColor==RealmColor.WHITE){
            GreenRealm playerGreenRealm=player.getGreenRealm();
            int sumDiceValue=diceValue;
            
            if(diceColor==RealmColor.WHITE){
                GreenDice greenDice=gameBoard.getGreenDice();
                sumDiceValue+=greenDice.getDiceValue();
            }
            else{
                ArcanePrism arcanePrism=gameBoard.getArcanePrism();
                sumDiceValue+=arcanePrism.getDiceValue();
            }

            greenRealmMoves=playerGreenRealm.getPossibleMovesForADie(sumDiceValue, diceColor);
        }
        if(diceColor==RealmColor.YELLOW||diceColor==RealmColor.WHITE){
            YellowRealm playerYellowRealm=player.getYellowRealm();
            yellowRealmMoves=playerYellowRealm.getPossibleMovesForADie(diceValue, diceColor);
        }
        if(diceColor==RealmColor.MAGENTA||diceColor==RealmColor.WHITE){
            MagentaRealm playerMagentaRealm=player.getMagentaRealm();
            magentaRealmMoves=playerMagentaRealm.getPossibleMovesForADie(diceValue, diceColor);
        }

        return mergeMoves(blueRealmMoves, redRealmMoves, greenRealmMoves, yellowRealmMoves, magentaRealmMoves);
    }

    public Move[] mergeMoves(Move[] moves1,Move[] moves2,Move[] moves3,Move[] moves4,Move[] moves5){
        int moveArraySize=0;
        Move[] moves=null;

        if(moves1!=null)
            moveArraySize+=moves1.length;
        if(moves2!=null)
            moveArraySize+=moves2.length;
        if(moves3!=null)
            moveArraySize+=moves3.length;
        if(moves4!=null)
            moveArraySize+=moves4.length;
        if(moves5!=null)
            moveArraySize+=moves5.length;
        
        if(moveArraySize!=0)
            moves=new Move[moveArraySize];
        int index=0;

        if(moves1!=null)
            for(Move x:moves1)
                moves[index++]=x;
        if(moves2!=null)
            for(Move x:moves2)
                moves[index++]=x;
        if(moves3!=null)
            for(Move x:moves3)
                moves[index++]=x;
        if(moves4!=null)
            for(Move x:moves4)
                moves[index++]=x;
        if(moves5!=null)
            for(Move x:moves5)
                moves[index++]=x;

        return moves;
    }

    public  GameBoard getGameBoard(){
        return gameBoard;
    }

    public  Player getActivePlayer(){
        return gameBoard.getActivePlayer();
    }

    public  Player getPassivePlayer(){
        return gameBoard.getPassivePlayer();
    }

    public  ScoreSheet getScoreSheet(Player player){
        return player.getScoreSheet();
    }


    public  GameStatus getGameStatus(){
        return getGameBoard().getGameStatus();
    }

    public  GameScore getGameScore(Player player){
        return player.getGameScore();
    }

    public  TimeWarp[] getTimeWarpPowers(Player player){
        ArrayList<TimeWarp> timeWarp=player.getTimeWarps();

        if(timeWarp==null||timeWarp.isEmpty())//May change implementation based on tests
            return new TimeWarp[0];

        TimeWarp[] timeWarpOutput=new TimeWarp[timeWarp.size()];
        for(int i=0;i<timeWarpOutput.length;i++)
            timeWarpOutput[i]=timeWarp.get(i);

        return timeWarpOutput;    
    }

    public  ArcaneBoost[] getArcaneBoostPowers(Player player){
        ArrayList<ArcaneBoost> arcaneBoosts=player.getArcaneBoosts();

        if(arcaneBoosts==null||arcaneBoosts.isEmpty())//May change implementation based on tests
            return new ArcaneBoost[0];

        ArcaneBoost[] arcaneBoostOutput=new ArcaneBoost[arcaneBoosts.size()];
        for(int i=0;i<arcaneBoostOutput.length;i++)
            arcaneBoostOutput[i]=arcaneBoosts.get(i);
            
        return arcaneBoostOutput;    
    }

    public  boolean selectDice(Dice dice, Player player){
       Dice[] availableDice=getAvailableDice();
       boolean foundDice=false;
       for(Dice x:availableDice){
        if(x==dice){
            foundDice=true;
            x.setDiceStatus(DiceStatus.TURN_SELECTED);
        }
        else if(x.getDiceValue()<dice.getDiceValue())    
            x.setDiceStatus(DiceStatus.FORGOTTEN_REALM);
       }
        return foundDice;    
    }
    public  boolean makeMove(Player player, Move move){ 
        Dice moveDice=move.getMoveDice();
        Creature moveCreature=move.getMoveCreature();
        switch(moveDice.getDiceColor()){
            case BLUE:
            return player.getBlueRealm().attack(moveDice.getDiceValue(), moveCreature);
            case RED:
            return player.getRedRealm().attack(moveDice.getDiceValue(), moveCreature);
            case GREEN:
            ArcanePrism arcanePrism=gameBoard.getArcanePrism();
            int sumValue=moveDice.getDiceValue()+arcanePrism.getDiceValue();
            return player.getGreenRealm().attack(sumValue, moveCreature);
            case YELLOW:
            return player.getYellowRealm().attack(moveDice.getDiceValue(), moveCreature);
            case MAGENTA:
            return player.getMagentaRealm().attack(moveDice.getDiceValue(), moveCreature);
        }//I should implement rewards here right?????

        return false;
    }
//^^^^^^^Make it so it checks color of dice "color to check" if white it reverts to the temp color but still keeps its color to be done later
}
