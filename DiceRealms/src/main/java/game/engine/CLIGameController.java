package main.java.game.engine;
import main.java.game.dice.*;

import java.util.*;
import main.java.game.collectibles.*;
import main.java.game.realms.*;
import main.java.game.creatures.*;
public class CLIGameController extends GameController{
    private GameBoard gameBoard;
    private Scanner sc;
    

//============================GameFlow=================================================
    public  void startGame(){
        createDelay();
        createDelay();
        sc=new Scanner(System.in);
        gameBoard=new GameBoard();
        while(!getGameStatus().isGameFinished()){
            Player currentPlayer=getActivePlayer();
            System.out.println("Round: "+getGameStatus().getGameRound());
            System.out.println(currentPlayer.getPlayerType()+"'s Turn!!!");
            createDelay();
            System.out.println("Printing your Grimore......");
            createDelay();
            System.out.println(getScoreSheet(currentPlayer));
            while(getGameStatus().getTurn()<=3){
                System.out.println("Turn: "+getGameStatus().getTurn());
                createDelay();
                
                System.out.println("Rolling dice......");
                createDelay();
                if(getAvailableDice()==null){
                    System.out.println("No more dice to roll :( ");
                    break;
                }
                rollDice();
                //Ask Use TimeWarp Power????
                int chosenDie=displayAndChooseAvailableDice();
                Dice[] availableDice=getAvailableDice();
                Dice selectedDie=availableDice[chosenDie];
                selectDice(selectedDie, currentPlayer);
                System.out.println("Sending remaining Dice to forgoten realm.....");
                createDelay();
                displayForgottenDice();
                createDelay();
                RealmColor theDiceColor=selectedDie.getDiceColor();
                if(selectedDie.getDiceColor()==RealmColor.WHITE){
                    chooseArcaneDiceColor();
                    theDiceColor=((ArcanePrism)selectedDie).getChosenColor();
                }
                Creature selectedCreature=chooseCreatureToAttack(theDiceColor, currentPlayer);
                boolean suc=makeMove(currentPlayer,new Move(selectedDie,selectedCreature));//Here say successful or failed based on...
                if(suc)
                    System.out.println("Attack Successful");
                else System.out.println("Attack Failed :(");

                checkAndGetPossibleRewards(currentPlayer, theDiceColor);
                System.out.println("Sending Selected Die to forgotten Realm....");
                selectedDie.setDiceStatus(DiceStatus.FORGOTTEN_REALM);
                createDelay();
                System.out.println(getScoreSheet(currentPlayer));
                //Ask Use ArcaneBoost Power???
               
                getGameStatus().incrementTurn();
            } 
            
            getGameStatus().incrementRound();
            switchPlayer();
            System.out.println("Passive player choose dice from forgotten realm later..");
            resetDiceStatus();
        }
        if(getGameScore(getActivePlayer()).getTotalGameScore()>getGameScore(getPassivePlayer()).getTotalGameScore())
            System.out.println(getActivePlayer().getPlayerType()+" Wins!!!");
        else
            System.out.println(getPassivePlayer().getPlayerType()+" Wins!!!");     
    }
    public void resetDiceStatus(){
       Dice[] allDice= getAllDice();
       for(Dice x:allDice)
            x.setDiceStatus(DiceStatus.AVAILABLE);
    }
    public void checkAndGetPossibleRewards(Player player,RealmColor diceColor){
        Realms checkInRealm=null;
        switch(diceColor){
            case RED:checkInRealm=player.getRedRealm();break;
            case BLUE:checkInRealm=player.getBlueRealm();break;
            case GREEN:checkInRealm=player.getGreenRealm();break;
            case YELLOW:checkInRealm=player.getYellowRealm();break;
            case MAGENTA:checkInRealm=player.getMagentaRealm();break;
            case WHITE:System.out.println("Error checking reward in white realm");//change later
        }
        if(!checkInRealm.isRewardAvailable())
            return;
        Reward storeReward=checkInRealm.getReward();//DONT FORGET TO FIX IMPLEMENTATION TO ARRAYSSSSSSSSSS
        switch (storeReward.getRewardType()) {
            case CREST:player.addElementalCrest((ElementalCrest)storeReward);break; 
            case POWER:
            if(storeReward instanceof TimeWarp){
                System.out.println("You got a TimeWarp power");
                player.addTimeWarp((TimeWarp)storeReward);
            }
            else{
                System.out.println("You got an ArcaneBoost power");  
                player.addArcaneBoost((ArcaneBoost)storeReward);
            }break;
            case BONUS:
            if(storeReward instanceof EssenceBonus){
                System.out.println("You got an Essence Bonus");  
                //Calls the Method that interrupts and uses Bonus!!!!
            }
            else{
                System.out.println("You got a insertColor  Bonus");  
                //Calls the Method That interrupts and uses Bonuss!!!
            }
            System.out.println("Bonuses are not yet implemented");
        } 
        createDelay(); 
    }
    public Creature chooseDragon(Player player){
        System.out.println();
        System.out.print("Chosen Dragon:");
        System.out.println();
        String chosen=sc.next();
        int dragonNumber=Integer.parseInt(""+chosen.charAt(chosen.length()-1));
        switch (dragonNumber) {
            case 1:return player.getRedRealm().getDragon1();
            case 2:return player.getRedRealm().getDragon2();
            case 3:return player.getRedRealm().getDragon3();
            case 4:return player.getRedRealm().getDragon4();
        }
        return null;//error occured
    }
    public Creature chooseCreatureToAttack(RealmColor color,Player player){
        switch (color) {
            case RED:System.out.println(player.getRedRealm());
            System.out.println("Please Choose a dragon");
            Dragon[] aliveDragons=player.getRedRealm().getAliveDragons();
            for(Dragon x:aliveDragons)
                System.out.print(x+" ");
            return chooseDragon(player);

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
            case WHITE:System.out.println("Error Arcane not set");
        }
        return null;//Invalid Color Entered
    }
    public void chooseArcaneDiceColor(){
        System.out.println("Please chose a color for the Arcane Prism:");
        System.out.println("Red Blue Green Yellow Magenta");
        System.out.print("Chosen color: ");
        getGameBoard().getArcanePrism().setChosenColor(sc.next());
    }
    
    public void createDelay(){
        try{
            Thread.sleep(2000);
        }
        catch(Exception ex){
            System.out.println("Error in Thread Sleep");
            ex.printStackTrace();}
    }
//============================Display====================================================
    public int displayAndChooseAvailableDice(){
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
    public void displayForgottenDice(){
        System.out.println("Forgotten Realm Dice: ");
        Dice[]forgottenDice=getForgottenRealmDice();
        for(Dice x:forgottenDice)
            System.out.println(x+"  ");
    }

//============================Methods====================================================    
    public  boolean switchPlayer(){
        Player activePlayer=getActivePlayer();
        Player passivePlayer=getPassivePlayer();
        passivePlayer.setPlayerStatus(PlayerStatus.ACTIVE);
        activePlayer.setPlayerStatus(PlayerStatus.PASSIVE);
        System.out.println("Switching");
        return true;
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

        //Do i make mara from passive mara from Active??????????
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
        RealmColor color=moveDice.getDiceColor();
        if(color==RealmColor.WHITE)
            color=((ArcanePrism)moveDice).getChosenColor();
        switch(color){
            case BLUE:
            return player.getBlueRealm().attack(moveDice.getDiceValue(), moveCreature);
            case RED:
            return player.getRedRealm().attack(moveDice.getDiceValue(), moveCreature);
            case GREEN:
            int sumValue=moveDice.getDiceValue();
            if(moveDice.getDiceColor()==RealmColor.WHITE)
                sumValue+=gameBoard.getGreenDice().getDiceValue();
            else sumValue+=gameBoard.getArcanePrism().getDiceValue();      
            return player.getGreenRealm().attack(sumValue, moveCreature);
            case YELLOW:
            return player.getYellowRealm().attack(moveDice.getDiceValue(), moveCreature);
            case MAGENTA:
            return player.getMagentaRealm().attack(moveDice.getDiceValue(), moveCreature);
            case WHITE:System.out.println("Error Attacking WHite");
        }
        //getGameScore(player).calculateGameScore();
        return false;
    }
}
