package main.java.game.engine;
import main.java.game.dice.*;

import java.util.*;
import main.java.game.collectibles.*;
import main.java.game.realms.*;
import main.java.game.creatures.*;
public class CLIGameController extends GameController{
    private GameBoard gameBoard;
    


    
    public  void startGame(){
     //Initializes necessary components and starts the game loop.
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

    /**
     * Rolls all available dice for the current turn, assigning each a random
     * number from 1 to 6.
     * 
     * @return An array of the currently rolled {@code Dice}.
     */
    public  Dice[] rollDice(){
        Dice[] rolledDice=getAvailableDice();
        for(Dice currentDice:rolledDice)
            currentDice.rollDice();
        return rolledDice;
    }

    /**
     * Gets the dice available for rolling or rerolling.
     * 
     * @return An array of {@code Dice} available for the current turn.
     */
    public  Dice[] getAvailableDice(){
        Dice[] allDice=getAllDice();
        int numberOfAvailableDice=0;
        for(Dice x:allDice)
            if(x.getDiceStatus()==DiceStatus.AVAILABLE)
                numberOfAvailableDice++;

       Dice[] availableDice=new Dice[numberOfAvailableDice];

        for(int i=0,j=0;i<numberOfAvailableDice;j++)
            if(allDice[j].getDiceStatus()==DiceStatus.AVAILABLE)
                availableDice[i++]=allDice[j];

        return availableDice;        
    }
    /**
     * Gets all six dice, providing their current state and value within the
     * game regardless of their location or status. The dice could be in various
     * states, such as currently rolled and awaiting selection by the active player,
     * in the Forgotten Realm awaiting selection by the passive player, or already
     * assigned to a specific turn by the active player.
     *
     * @return An array of all six {@code Dice}, with each die's state and value.
     */
    public  Dice[] getAllDice(){
        return gameBoard.getAllDice();//or Do I do seperate Dice?
    }

    /**
     * Gets the dice currently available in the Forgotten Realm.
     *
     * @return An array of {@code Dice} that are currently in the Forgotten Realm.
     */
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

    /**
     * Gets all possible moves for a given player.
     *
     * @param player The player for whom to determine possible moves.
     * @return An array of all possible moves for all rolled dice.
     */
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

    /**
     * Gets possible moves for all currently rolled dice for a given player.
     *
     * @param player The player for whom to determine possible moves.
     * @return An array of all possible moves for all rolled dice.
     */
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

    /**
     * Gets all possible moves for a given die for a given player.
     *
     * @param player The player for whom to determine possible moves.
     * @param dice   The dice to determine possible moves for.
     * @return An array of possible moves for the given dice.
     */ 
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

    /**
     * Gets the current game board, including all players and all score sheets.
     * 
     * @return The current {@code GameBoard} object.
     */
    public  GameBoard getGameBoard(){
        return gameBoard;
    }

    /**
     * Gets the current active player's information.
     * 
     * @return The active {@code Player} object.
     */
    public  Player getActivePlayer(){
        return gameBoard.getActivePlayer();
    }

    /**
     * Gets the current passive player's information.
     * 
     * @return The passive {@code Player} object.
     */
    public  Player getPassivePlayer(){
        return gameBoard.getPassivePlayer();
    }

    /**
     * Gets the score sheet for a given player.
     * 
     * @param player The player to get the current score sheet for.
     * @return The {@code ScoreSheet} object for a given player.
     */
    public  ScoreSheet getScoreSheet(Player player){
        return player.getScoreSheet();
    }

    /**
     * Gets the current game status, including round and turn information for the
     * current active player.
     * 
     * @return The current {@code GameStatus} object.
     */
    public  GameStatus getGameStatus(){




        
    }

    /**
     * Gets the current score of the game, including scores in each realm, number of
     * elemental crests, and the total score for a given player.
     * 
     * @param player The player to determine current score for.
     * @return The current {@code GameScore} object.
     */
    public  GameScore getGameScore(Player player){

    }

    /**
     * Gets the array of TimeWarp powers and their status for a given player.
     *
     * @param player The player to get the current TimeWarp powers for.
     * @return An array of {@code TimeWarp} objects representing the TimeWarp powers
     *         for a given player.
     */
    public  TimeWarp[] getTimeWarpPowers(Player player){
        ArrayList<TimeWarp> timeWarp=player.getTimeWarps();

        if(timeWarp==null||timeWarp.isEmpty())//May change implementation based on tests
            return new TimeWarp[0];

        TimeWarp[] timeWarpOutput=new TimeWarp[timeWarp.size()];
        for(int i=0;i<timeWarpOutput.length;i++)
            timeWarpOutput[i]=timeWarp.get(i);

        return timeWarpOutput;    
    }

    /**
     * Gets the array of ArcaneBoost powers and their status for a given player.
     *
     * @param player The player to get the current ArcaneBoost powers for.
     * @return An array of {@code ArcaneBoost} objects representing the ArcaneBoost
     *         powers for a given player.
     */
    public  ArcaneBoost[] getArcaneBoostPowers(Player player){
        ArrayList<ArcaneBoost> arcaneBoosts=player.getArcaneBoosts();

        if(arcaneBoosts==null||arcaneBoosts.isEmpty())//May change implementation based on tests
            return new ArcaneBoost[0];

        ArcaneBoost[] arcaneBoostOutput=new ArcaneBoost[arcaneBoosts.size()];
        for(int i=0;i<arcaneBoostOutput.length;i++)
            arcaneBoostOutput[i]=arcaneBoosts.get(i);
            
        return arcaneBoostOutput;    
    }

    /**
     * Selects a die and adds it to the player class, then moves
     * all other dice with less value to the Forgotten Realm.
     * 
     * @param player The player who selected the die.
     * @param dice   The dice to be selected.
     * @return {@code true} if the selection was successful,
     *         {@code false} otherwise.
     */
    public  boolean selectDice(Dice dice, Player player){
        return false;
    }

    /**
     * Executes a move using the selected dice on a specified creature.
     *
     * @param player The player who wants to make the move.
     * @param move   The move to be executed, including the selected dice and
     *               target creature.
     * @return {@code true} if the move is successfully completed,
     *         {@code false} otherwise.
     */
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
