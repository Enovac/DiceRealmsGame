package main.java.game.engine;
import main.java.game.dice.*;
import main.java.game.collectibles.*;
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
        Player activePlayer=gameBoard.getActivePlayer();
        Player passivePlayer=gameBoard.getPassivePlayer();
        passivePlayer.setPlayerStatus(PlayerStatus.PASSIVE);
        activePlayer.setPlayerStatus(PlayerStatus.ACTIVE);
        return true;
        //Should it do the get dice from bla bla??
    }

    /**
     * Rolls all available dice for the current turn, assigning each a random
     * number from 1 to 6.
     * 
     * @return An array of the currently rolled {@code Dice}.
     */
    public  Dice[] rollDice(){
        Dice[] rolledDice=getAvailableDice();
        for(Dice x:rolledDice)
            x.rollDice((int)((Math.random()*6)+1));
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

    }

    /**
     * Gets possible moves for all currently rolled dice for a given player.
     *
     * @param player The player for whom to determine possible moves.
     * @return An array of all possible moves for all rolled dice.
     */
    public  Move[] getPossibleMovesForAvailableDice(Player player){

    }

    /**
     * Gets all possible moves for a given die for a given player.
     *
     * @param player The player for whom to determine possible moves.
     * @param dice   The dice to determine possible moves for.
     * @return An array of possible moves for the given dice.
     */
    public  Move[] getPossibleMovesForADie(Player player, Dice dice){

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


        
    }

    /**
     * Gets the array of ArcaneBoost powers and their status for a given player.
     *
     * @param player The player to get the current ArcaneBoost powers for.
     * @return An array of {@code ArcaneBoost} objects representing the ArcaneBoost
     *         powers for a given player.
     */
    public  ArcaneBoost[] getArcaneBoostPowers(Player player){



         
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


        return false;
    }

}
